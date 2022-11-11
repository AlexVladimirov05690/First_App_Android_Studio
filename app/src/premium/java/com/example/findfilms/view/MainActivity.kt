package com.example.findfilms.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.findfilms.*
import com.example.findfilms.databinding.ActivityMainBinding
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.utils.receivers.PowerCheckerBroadcast
import com.example.findfilms.view.fragments.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receiver = PowerCheckerBroadcast()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(receiver, filters)
        initButton()
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentPlace.id, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent?.extras?.get("film") is Film) {
            launchDetailsFragment(intent.extras?.get("film") as Film)
        } else super.onNewIntent(intent)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            AlertDialog.Builder(this)
                .setTitle(R.string.do_you_want_exit)
                .setIcon(R.drawable.ic_launcher_foreground)
                .setPositiveButton(R.string.yes) { _, _ ->
                    finish()
                }
                .setNegativeButton(R.string.no) { _, _ ->
                }
                .show()
        } else {
            super.onBackPressed()
        }
    }

    private fun initButton() {

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.set -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }

        binding.BottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> {
                    val tag = "main"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: HomeFragment(), tag)
                    true
                }
                R.id.fav -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                R.id.watch_later_menu -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SelectionsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentPlace.id, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentPlace.id, fragment, tag)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}