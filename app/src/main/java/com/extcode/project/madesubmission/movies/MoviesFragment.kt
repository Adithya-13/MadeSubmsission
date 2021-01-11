package com.extcode.project.madesubmission.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.extcode.project.core.data.Resource
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.ui.MoviesAdapter
import com.extcode.project.core.utils.SortUtils
import com.extcode.project.madesubmission.R
import com.extcode.project.madesubmission.databinding.FragmentMoviesBinding
import com.extcode.project.madesubmission.detail.DetailActivity
import com.extcode.project.madesubmission.home.HomeActivity
import com.extcode.project.madesubmission.home.SearchViewModel
import com.extcode.project.madesubmission.utils.DataState
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MoviesFragment : Fragment(), View.OnClickListener {

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = activity?.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as HomeActivity).findViewById(R.id.search_view)
        return binding?.root
    }

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchView: MaterialSearchView
    private var sort = SortUtils.RANDOM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter()
        setList(sort)
        observeSearchQuery()
        setSearchList()

        with(binding?.rvMovies) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        binding?.random?.setOnClickListener(this)
        binding?.newest?.setOnClickListener(this)
        binding?.vote?.setOnClickListener(this)
        binding?.popularity?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.random -> {
                binding?.menu?.close(true)
                sort = SortUtils.RANDOM
                setList(sort)
            }
            binding?.newest -> {
                binding?.menu?.close(true)
                sort = SortUtils.NEWEST
                setList(sort)
            }
            binding?.popularity -> {
                binding?.menu?.close(true)
                sort = SortUtils.POPULARITY
                setList(sort)
            }
            binding?.vote -> {
                binding?.menu?.close(true)
                sort = SortUtils.VOTE
                setList(sort)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(menuItem)
    }

    private fun setList(sort: String) {
        viewModel.getMovies(sort).observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> setDataState(DataState.LOADING)
                is Resource.Success -> {
                    setDataState(DataState.SUCCESS)
                    moviesAdapter.setData(movies.data)
                }
                is Resource.Error -> {
                    setDataState(DataState.ERROR)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeSearchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchViewModel.setSearchQuery(it) }
                return true
            }
        })
    }

    private fun setSearchList() {
        searchViewModel.movieResult.observe(this, { movies ->
            if (movies.isNullOrEmpty()) {
                setDataState(DataState.ERROR)
            } else {
                setDataState(DataState.SUCCESS)
            }
            moviesAdapter.setData(movies)
        })
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                setDataState(DataState.SUCCESS)
            }

            override fun onSearchViewClosed() {
                setDataState(DataState.SUCCESS)
                setList(sort)
            }
        })
    }

    private fun setDataState(state: DataState) {
        when (state) {
            DataState.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.notFound?.visibility = View.VISIBLE
                binding?.notFoundText?.visibility = View.VISIBLE
            }
            DataState.LOADING -> {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.notFound?.visibility = View.GONE
                binding?.notFoundText?.visibility = View.GONE
            }
            DataState.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.notFound?.visibility = View.GONE
                binding?.notFoundText?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
        searchView.setOnSearchViewListener(null)
        binding?.rvMovies?.adapter = null
        _fragmentMoviesBinding = null
    }
}