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
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.launch

class AddIngredientFragment : Fragment() {

    companion object {
        fun newInstance() = AddIngredientFragment()
    }

    fun onCreate() {
        if (validateFields()) {
            viewModel.selectedItems = mutableListOf()
            viewModel.makeProduct()
        }
    }

    fun onUpdate(food: FoodIngredientRelation) {
        if (validateFields()) {
            viewModel.selectedItems = mutableListOf()
            viewModel.updateProduct(food)
        }
    }

    var food: FoodIngredientRelation? = null

    fun setFoodData(food: FoodIngredientRelation) {
        this.food = food
    }

    private fun validateFields(): Boolean {
        val isValid = !(binding.productDetail.name.text!!.isEmpty()
                || binding.productDetail.url.text!!.isEmpty()
                || binding.productDetail.price.text!!.isEmpty()
                || binding.productDetail.calories.text!!.isEmpty()
                || viewModel.foodComposite.price == 0.0 || viewModel.foodComposite.calories == 0.0)
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

        if (food != null && viewModel.ingredientList == null) {
            viewModel.viewModelScope.launch {
                viewModel.setFoodData(food!!)
            }
        } else {
            viewModel.ingredientList = AddIngredientViewModel.app.repositoryFood.getAllIngredients(
                AddIngredientViewModel.app.db
            )
        }

        viewModel.foodLiveData.observe(this.viewLifecycleOwner) {
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
            viewModel.ingredientList?.let {
                it.collect {
                    (binding.ingredientsList.adapter as IngredientSelectorAdapter).setData(
                        it, viewModel
                    )
                }
            }
        }

        viewModel.selectedItemLiveData.observe(this.viewLifecycleOwner) {
            (binding.ingredientsList.adapter as IngredientSelectorAdapter).notifyDataSetChanged()
        }
    }
}