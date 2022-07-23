package com.foodapp.UI.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.UI.adapters.CartAdapter
import com.foodapp.UI.common.setBottomNavbar
import com.foodapp.UI.common.setTopNavbar
import com.foodapp.UI.viewmodels.CartViewModel
import com.foodapp.databinding.ActivityCartBinding


class CartActivity : AppCompatActivity() {
    private var _binding: ActivityCartBinding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
// onDestroyView.
    val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopNavbar(this)
        setBottomNavbar(this)
        cartViewModel.cartItems.observe(this) { cartItems ->
            binding.cartItems.apply {
                layoutManager = LinearLayoutManager(baseContext)
                (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
                adapter = CartAdapter(cartItems, cartViewModel.userOperation)
            }
        }
    }
}