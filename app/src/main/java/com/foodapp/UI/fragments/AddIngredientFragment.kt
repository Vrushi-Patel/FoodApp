package com.foodapp.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.AppClass
import com.foodapp.UI.adapters.IngredientSelectorAdapter
import com.foodapp.UI.viewmodels.AddIngredientViewModel
import com.foodapp.databinding.FragmentAddIngredientBinding
import kotlinx.coroutines.launch

class AddIngredientFragment : Fragment() {

    companion object {
        fun newInstance() = AddIngredientFragment()
    }

    fun onClick() {
        if (validateFields())
            viewModel.makeProduct()
    }

    private fun validateFields(): Boolean {
        val isValid = !(binding.productDetail.name.text!!.isEmpty()
                || binding.productDetail.url.text!!.isEmpty()
                || binding.productDetail.price.text!!.isEmpty()
                || binding.productDetail.calories.text!!.isEmpty())
        if (!isValid)
            Toast.makeText(context, "Please, fill all data!", Toast.LENGTH_LONG).show()
        return isValid
    }

    private lateinit var viewModel: AddIngredientViewModel

    private var _biding: FragmentAddIngredientBinding? = null
    private val binding get() = _biding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _biding = FragmentAddIngredientBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AddIngredientViewModel.app = requireActivity().application as AppClass
        viewModel = ViewModelProvider(this).get(AddIngredientViewModel::class.java)

        viewModel.foodLiveData.observeForever {
            binding.productDetail.calories.setText(it.calories.toString())
            binding.productDetail.price.setText(it.price.toString())
            binding.productDetail.name.setText(it.name)
            binding.productDetail.url.setText(it.url)
        }

        binding.productDetail.name.addTextChangedListener {
            viewModel.foodComposite.name = it.toString()
        }

        binding.productDetail.url.addTextChangedListener {
            viewModel.foodComposite.url = it.toString()
        }

        binding.ingredientsList.apply {
            layoutManager = LinearLayoutManager(context)
            (layoutManager as LinearLayoutManager).orientation = RecyclerView.VERTICAL
            adapter = IngredientSelectorAdapter()
        }

        viewModel.viewModelScope.launch {
            viewModel.ingredientList.collect {
                val selectedItems: MutableList<Int> = mutableListOf()
                (binding.ingredientsList.adapter as IngredientSelectorAdapter).setData(
                    it, selectedItems, viewModel
                )
            }
        }
    }
}