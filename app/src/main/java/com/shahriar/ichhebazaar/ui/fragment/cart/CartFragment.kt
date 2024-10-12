package com.shahriar.ichhebazaar.ui.fragment.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shahriar.ichhebazaar.databinding.FragmentCartBinding
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        // Using ViewBinding to access views
        binding.rvParent.layoutManager = LinearLayoutManager(requireContext())

        categoryAdapter = CategoryAdapter(emptyList()) { item ->
            Log.d("Clicked", item.toString())
        }
        binding.rvParent.adapter = categoryAdapter
        handleLoading()

        lifecycleScope.launch {
            viewModel.categoryResponseFlow.collect { response ->
                Log.d("CartFragment", response.toString())
                val categoryList = response?.data
                if (categoryList != null) {
                    categoryAdapter.addNewCategory(categoryList)
                }
            }
        }
    }

    private fun handleLoading() {
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}
