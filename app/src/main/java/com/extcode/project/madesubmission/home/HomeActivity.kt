package com.extcode.project.madesubmission.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.extcode.project.madesubmission.R
import com.extcode.project.madesubmission.databinding.ActivityHomeBinding
import com.extcode.project.madesubmission.movies.MoviesFragment
import com.extcode.project.madesubmission.tvshows.TvShowsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
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
                2 -> moveToFavoriteFragment()
            }
        }
    }

    private fun moveToFavoriteFragment() {
        val fragment = instantiateFragment()
        Log.d("fragmentName", fragment.toString())
        if (fragment != null) {
            navigationChange(fragment)
        }
    }

    private fun instantiateFragment(): Fragment? {
        return try {
            Class.forName("com.extcode.project.favorite.FavoriteFragment").newInstance() as Fragment
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