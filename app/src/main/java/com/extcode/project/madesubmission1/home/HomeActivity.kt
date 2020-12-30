package com.extcode.project.madesubmission1.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.extcode.project.madesubmission1.R
import com.extcode.project.madesubmission1.databinding.ActivityHomeBinding
import com.extcode.project.madesubmission1.movies.MoviesFragment
import com.extcode.project.madesubmission1.tvshows.TvShowsFragment
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationChange(MoviesFragment())

        binding.bottomNavigationContainer.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> navigationChange(MoviesFragment())
                1 -> navigationChange(TvShowsFragment())
                2 -> installFavoriteModule()
            }
        }
    }

    private val className: String
        get() = "com.extcode.project.favorite.FavoriteFragment"

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleChat = "favorite"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            moveToFavoriteFragment()
            Toast.makeText(this, "Open module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToFavoriteFragment()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavoriteFragment() {
        val fragment = instantiateFragment(className)
        if (fragment != null) {
            navigationChange(fragment)
        }
    }

    private fun instantiateFragment(className: String): Fragment? {
        return try {
            Toast.makeText(this, "Open Module", Toast.LENGTH_SHORT).show()
            Class.forName(className).newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun navigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

}