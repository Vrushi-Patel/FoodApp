package com.foodapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.databinding.ActivityHomeBinding
import com.foodapp.ui.adapters.CategoryAdapter
import com.foodapp.ui.adapters.MoreAdapter
import com.foodapp.ui.common.setBottomNavbar
import com.foodapp.ui.common.setTopNavbar
import com.foodapp.ui.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavbar(this)
        setTopNavbar(this)

        binding.categoryList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
            adapter = CategoryAdapter()
        }

        binding.moreList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.HORIZONTAL
            adapter = MoreAdapter()
        }

        productViewModel.viewModelScope.launch {
            productViewModel.productList.collect {
                (binding.categoryList.adapter as CategoryAdapter).setData(it)
            }
        }

        productViewModel.viewModelScope.launch {
            productViewModel.ingredientList.collect {
                (binding.moreList.adapter as MoreAdapter).setData(it)
            }
        }
    }
}