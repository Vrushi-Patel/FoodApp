package com.foodapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.databinding.ActivityCartBinding
import com.foodapp.ui.adapters.CartAdapter
import com.foodapp.ui.common.setBottomNavbar
import com.foodapp.ui.common.setTopNavbar
import com.foodapp.ui.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private var _binding: ActivityCartBinding? = null
    private val binding get() = _binding!!

    val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)
        setBottomNavbar(this)

        cartViewModel.viewModelScope.launch {
            binding.cartItems.apply {
                layoutManager = LinearLayoutManager(baseContext)
                (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
                adapter = CartAdapter(cartViewModel)
            }
            cartViewModel.cartItems.collect {
                (binding.cartItems.adapter as CartAdapter).setData(it)
                if (it.isNotEmpty()) {
                    binding.emptyCartMsg.visibility =
                        View.GONE
                    var priceTotal = 0.0
                    it.forEach { priceTotal += (it.food.product.price * it.cartData.quantity) }
                    binding.itemsTotal.text = "₹ $priceTotal"
                } else {
                    binding.itemsTotal.text = "₹ 0.0"
                    binding.emptyCartMsg.visibility =
                        View.VISIBLE
                }
            }
        }

    }
}