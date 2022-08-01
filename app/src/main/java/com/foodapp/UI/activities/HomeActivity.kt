package com.foodapp.UI.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.AppClass
import com.foodapp.UI.adapters.CategoryAdapter
import com.foodapp.UI.adapters.MoreAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.UI.viewmodels.ProductViewModel
import com.foodapp.databinding.ActivityHomeBinding
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

        ProductViewModel.app = application as AppClass
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