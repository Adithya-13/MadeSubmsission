package com.extcode.project.favorite.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.ui.MoviesAdapter
import com.extcode.project.core.utils.SortUtils
import com.extcode.project.favorite.FavoriteViewModel
import com.extcode.project.favorite.R
import com.extcode.project.favorite.databinding.FragmentFavoriteTvShowsBinding
import com.extcode.project.madesubmission.detail.DetailActivity
import com.extcode.project.madesubmission.utils.DataState
import com.extcode.project.utils.ItemSwipeHelper
import com.extcode.project.utils.OnItemSwiped
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteTvShowsFragment : Fragment(), View.OnClickListener {

    private var _fragmentFavoriteTvShowsBinding: FragmentFavoriteTvShowsBinding? = null
    private val binding get() = _fragmentFavoriteTvShowsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFavoriteTvShowsBinding =
            FragmentFavoriteTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var tvShowsAdapter: MoviesAdapter
    private val viewModel: FavoriteViewModel by viewModel()
    private var sort = SortUtils.RANDOM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteTvShows)

        tvShowsAdapter = MoviesAdapter()

        setDataState(DataState.LOADING)
        setList(sort)

        with(binding.rvFavoriteTvShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }

        tvShowsAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.random -> {
                binding.menu.close(true)
                sort = SortUtils.RANDOM
                setList(sort)
            }
            binding.newest -> {
                binding.menu.close(true)
                sort = SortUtils.NEWEST
                setList(sort)
            }
            binding.popularity -> {
                binding.menu.close(true)
                sort = SortUtils.POPULARITY
                setList(sort)
            }
            binding.vote -> {
                binding.menu.close(true)
                sort = SortUtils.VOTE
                setList(sort)
            }
        }
    }

    private val itemTouchHelper = ItemSwipeHelper(object : OnItemSwiped {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = tvShowsAdapter.getSwipedData(swipedPosition)
                var state = tvShowEntity.favorite
                viewModel.setFavorite(tvShowEntity, !state)
                state = !state
                val snackBar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) {
                    viewModel.setFavorite(tvShowEntity, !state)
                }
                snackBar.show()
            }
        }
    })

    private fun setList(sort: String) {
        viewModel.getFavoriteTvShows(sort).observe(viewLifecycleOwner, tvShowsObserver)
    }

    private val tvShowsObserver = Observer<List<Movie>> { tvShows ->
        if (tvShows.isNullOrEmpty()) {
            setDataState(DataState.ERROR)
        } else {
            setDataState(DataState.SUCCESS)
        }
        tvShowsAdapter.setData(tvShows)
    }

    private fun setDataState(state: DataState) {
        when (state) {
            DataState.ERROR -> {
                binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
                binding.notFoundText.visibility = View.VISIBLE
            }
            DataState.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.notFound.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE
            }
            DataState.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavoriteTvShows.adapter = null
        _fragmentFavoriteTvShowsBinding = null
    }
}