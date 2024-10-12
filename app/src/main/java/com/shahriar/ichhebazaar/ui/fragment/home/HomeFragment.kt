package com.shahriar.ichhebazaar.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.databinding.FragmentHomeBinding
import com.shahriar.ichhebazaar.data.product.Product
import com.shahriar.ichhebazaar.ui.fragment.DetailsActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.sliderone))
        imageList.add(SlideModel(R.drawable.slidertwo))
        imageList.add(SlideModel(R.drawable.sliderthree))

        // Using ViewBinding to access ImageSlider
        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        binding.imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        binding.imageSlider.startSliding(2000)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 8, vertical = 8))
        binding.recyclerView.setPadding(0, 0, 0, 0)

        // Initialize the adapter with an empty list
        adapter = ProductAdapter(emptyList()) { item ->
            navigateToDetails(item)
        }
        binding.recyclerView.adapter = adapter

        // Handle the Loading
        handleLoading()

        lifecycleScope.launch {
            viewModel.productResponseFlow.collect { response ->
                Log.d("HomeFragment", response.toString())
                val productList = response?.data?.data

                if (productList != null) {
                    adapter.updateList(productList)
                }
            }
        }

    }

    private fun navigateToDetails(item: Product) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtra("PRODUCT", item)
        }
        startActivity(intent)
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
