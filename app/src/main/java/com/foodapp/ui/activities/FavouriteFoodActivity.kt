package com.foodapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.AppClass
import com.foodapp.ui.adapters.FavouriteAdapter
import com.foodapp.ui.common.setBottomNavbar
import com.foodapp.ui.common.setTopNavbar
import com.foodapp.ui.viewmodels.FavouriteViewModel
import com.foodapp.databinding.ActivityFavouriteFoodBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFoodActivity : AppCompatActivity() {

    private var _binding: ActivityFavouriteFoodBinding? = null
    private val binding: ActivityFavouriteFoodBinding get() = _binding!!

    val viewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavouriteFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)
        setBottomNavbar(this)

        binding.favouriteList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
            adapter = FavouriteAdapter(viewModel)
        }

        viewModel.viewModelScope.launch {
            viewModel.favouriteProducts.collect { it ->
                (binding.favouriteList.adapter as FavouriteAdapter).setData(it)
                binding.emptyListMsg.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}