package com.example.movieapp.presenter.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.core.domain.model.Movie
import com.example.core.utils.DataMapper
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.presenter.home.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*


class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null

    private val detailViewModel : DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)


        val movieId = arguments?.getInt(HomeFragment.EXTRA_MOVIE)
        if (movieId != null) {
            setData(movieId)
        }
    }

    private fun checkMovie(movie: Movie) {
        var bool: Boolean
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.getFavoriteCount(movie.id)
            withContext(Dispatchers.Main){
                bool = if(count > 0){
                    binding?.floatingButton2?.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24_red))
                    true
                }else{
                    binding?.floatingButton2?.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24_white))
                    false
                }
            }

            binding?.floatingButton2?.setOnClickListener {
                bool = !bool
                if(bool){
                    detailViewModel.addFavorite(movie)
                    binding?.floatingButton2?.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24_red))
                }else{
                    detailViewModel.removeFavorite(movie)
                    binding?.floatingButton2?.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24_white))
                }
            }
        }
    }

    private fun setData(movieId: Int) {
        detailViewModel.getMovieDetail(movieId).observe(viewLifecycleOwner, {
            (activity as AppCompatActivity?)?.supportActionBar?.title = it.title
            checkMovie(DataMapper.mapDetailToMovie(it, movieId))
            val poster = it.poster_path ?: ""
            if(poster == ""){
                Glide.with(binding!!.root)
                    .load(R.drawable.no_preview)
                    .fitCenter()
                    .into(binding!!.movieImage)
            }else{
                Glide.with(binding!!.root)
                    .load("https://image.tmdb.org/t/p/w500${it.poster_path}")
                    .centerCrop()
                    .into(binding!!.movieImage)
            }

            binding?.tvRevenue?.text = formatMoney(it.revenue)
            binding?.tvReleaseDate?.text = it.release_date
            binding?.tvBudget?.text = formatMoney(it.budget)
            binding?.tvLanguage?.text = it.original_language
            binding?.tvImdbRating?.text = it.vote_average.toString()
            binding?.tvVoteCount?.text = it.vote_count.toString()
            binding?.tvMovieTitle?.text = it.title
            binding?.tvSynopsis?.text = it.overview
            binding?.tvDuration?.text = getString(R.string.minutes, it.runtime)
        })
    }

    private fun formatMoney(value: Int): String{
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")

        return format.format(value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}