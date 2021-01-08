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
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@FlowPreview
class MoviesFragment : Fragment() {
    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = activity?.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as HomeActivity).findViewById(R.id.search_view)
        return binding.root
    }

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchView: MaterialSearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter()
        setList(SortUtils.RANDOM)
        observeSearchQuery()
        setSearchList()

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        binding.random.setOnClickListener {
            binding.menu.close(true)
            setList(SortUtils.RANDOM)
        }
        binding.newest.setOnClickListener {
            binding.menu.close(true)
            setList(SortUtils.NEWEST)
        }
        binding.popularity.setOnClickListener {
            binding.menu.close(true)
            setList(SortUtils.POPULARITY)
        }
        binding.vote.setOnClickListener {
            binding.menu.close(true)
            setList(SortUtils.VOTE)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
    }

    private fun setList(sort: String) {
        viewModel.getMovies(sort).observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE
                    binding.notFoundText.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.GONE
                    binding.notFoundText.visibility = View.GONE
                    moviesAdapter.setData(movies.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                    binding.notFoundText.visibility = View.VISIBLE
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
                newText?.let {
                    searchViewModel.setSearchQuery(it)
                }
                return true
            }
        })
    }

    private fun setSearchList() {
        searchViewModel.movieResult.observe(viewLifecycleOwner, { movies ->
            if (movies.isNullOrEmpty()){

            }
            moviesAdapter.setData(movies)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMoviesBinding = null
    }

}