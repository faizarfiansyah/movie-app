package com.example.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.MovieAdapter
import com.example.favorite.databinding.FragmentFavoriteBinding
import com.example.movieapp.presenter.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private val favoriteViewModel : FavoriteViewModel by viewModel()
    private var adapter: MovieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.title = getString(R.string.favorite_movies)
        loadKoinModules(favoriteModule)
        binding = FragmentFavoriteBinding.bind(view)

        showRecycleView()
        adapter?.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Int) {
                val bundle = Bundle()
                bundle.putInt(HomeFragment.EXTRA_MOVIE, data)
                view.findNavController().navigate(com.example.movieapp.R.id.action_favoriteFragment_to_detailFragment, bundle)
            }

        })
    }

    private fun showRecycleView() {
        binding?.rvFavoriteList?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFavoriteList?.setHasFixedSize(true)

        adapter = MovieAdapter()

        favoriteViewModel.getFavoriteMovies().observe(viewLifecycleOwner,{
            adapter?.setMovie(it)
        })
        adapter?.notifyDataSetChanged()

        binding?.rvFavoriteList?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }

}