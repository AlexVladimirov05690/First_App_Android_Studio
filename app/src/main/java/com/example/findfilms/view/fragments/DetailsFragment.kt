package com.example.findfilms.view.fragments


import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.findfilms.App
import com.example.findfilms.R
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.databinding.FragmentDetailsBinding
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.domain.Interactor
import com.example.findfilms.view.notification.NotificationHelper
import com.example.findfilms.viewmodel.DetailsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import javax.inject.Inject

class DetailsFragment : Fragment() {
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
    }

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var film: Film
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }
    private val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film = arguments?.get("film") as Film
        binding.detailsToolbar.title = film.title
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        binding.detailsDescription.setText(film.description)
        initButtons()
        binding.detailsInWish.setImageResource(
            if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    private fun initButtons() {
        binding.watchLater.setOnClickListener {
            NotificationHelper.notificationSet(requireContext(), film)
            Snackbar.make(binding.root, R.string.watch_later_menu, Snackbar.LENGTH_SHORT).show()
        }
        binding.detailsInWish.setOnClickListener {
            if (!film.isInFavorites) {
                binding.detailsInWish.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
                interactor.addFavoriteFilm(film)
                Snackbar.make(binding.root, R.string.film_add_to_favorite, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                binding.detailsInWish.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
                interactor.deleteFavoriteFilm(film)
                Snackbar.make(binding.root,
                    R.string.film_remove_from_favorite,
                    Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.detailsFab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
        binding.detailsFabDownloadWp.setOnClickListener{
            performAsyncLoadOfPoster()
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun saveToGallery(bitmap: Bitmap) {
        //Проверяем версию системы
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Создаем объект для передачи данных
            val contentValues = ContentValues().apply {
                //Составляем информацию для файла (имя, тип, дата создания, куда сохранять и т.д.)
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/FilmsSearchApp")
            }
            //Получаем ссылку на объект Content resolver, который помогает передавать информацию из приложения вовне
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            //Открываем канал для записи на диск
            val outputStream = contentResolver.openOutputStream(uri!!)
            //Передаем нашу картинку, может сделать компрессию
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            //Закрываем поток
            outputStream?.close()
        } else {
            //То же, но для более старых версий ОС
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    private fun performAsyncLoadOfPoster() {
        if (!checkPermission()) {
            requestPermission()
            return
        }
        MainScope().launch {
            binding.progressBar.isVisible = true
            val job = scope.async {
                viewModel.loadWallpaper(ApiConstants.IMAGES_URL + "original" + film.poster)
            }
            saveToGallery(job.await())
            Snackbar.make(
                binding.root,
                R.string.downloaded_to_gallery,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.open) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .show()
            binding.progressBar.isVisible = false
        }
    }


}