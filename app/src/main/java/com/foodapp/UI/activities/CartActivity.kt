package com.foodapp.UI.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.AppClass
import com.foodapp.UI.adapters.CartAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.UI.viewmodels.CartViewModel
import com.foodapp.databinding.ActivityCartBinding
import kotlinx.coroutines.launch


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

        CartViewModel.app = application as AppClass
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