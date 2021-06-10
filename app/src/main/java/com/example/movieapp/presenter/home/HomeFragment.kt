package com.example.movieapp.presenter.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.MovieAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private val homeViewModel : HomeViewModel by viewModel()
    private lateinit var adapter: MovieAdapter

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.title = getString(R.string.top_movies)

        binding = FragmentHomeBinding.bind(view)

        showRecycleView()

        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Int) {
                val bundle = Bundle()
                bundle.putInt(EXTRA_MOVIE, data)
                view.findNavController().navigate(
                    R.id.action_homeFragment_to_detailFragment,
                    bundle
                )
            }
        })
    }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            super.onCreateOptionsMenu(menu, inflater)
            menu.clear()
            inflater.inflate(R.menu.menu, menu)
            val item = menu.findItem(R.id.search)
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

            val searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.searchMovie(query).observe(viewLifecycleOwner, {
                        adapter.setMovie(it)
                    })
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }
            })

            item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    homeViewModel.getMovies().observe(viewLifecycleOwner, {
                        adapter.setMovie(it)
                    })
                    return true
                }

            })
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(item.itemId == R.id.favorite){
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_favoriteFragment)
            }
            return super.onOptionsItemSelected(item)
        }

    private fun showRecycleView() {
        binding.rvMovieList.layoutManager = LinearLayoutManager(activity)
        binding.rvMovieList.setHasFixedSize(true)

        adapter = MovieAdapter()
        homeViewModel.getMovies().observe(viewLifecycleOwner, {
            adapter.setMovie(it)
        })
        adapter.notifyDataSetChanged()

        binding.rvMovieList.adapter = adapter
    }
}