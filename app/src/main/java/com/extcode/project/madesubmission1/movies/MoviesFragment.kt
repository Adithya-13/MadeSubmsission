package com.extcode.project.madesubmission1.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.extcode.project.core.data.Resource
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.ui.MoviesAdapter
import com.extcode.project.core.utils.SortUtils
import com.extcode.project.madesubmission1.databinding.FragmentMoviesBinding
import com.extcode.project.madesubmission1.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter()
        setList(SortUtils.RANDOM)

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

        binding.random.setOnClickListener { setList(SortUtils.RANDOM) }
        binding.newest.setOnClickListener { setList(SortUtils.NEWEST) }
        binding.popularity.setOnClickListener { setList(SortUtils.POPULARITY) }
        binding.vote.setOnClickListener { setList(SortUtils.VOTE) }
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
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.GONE
                    moviesAdapter.setData(movies.data)
                    moviesAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMoviesBinding = null
    }

}