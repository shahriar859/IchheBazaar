package com.shahriar.ichhebazaar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.data.product.Product
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.sliderone, "Various Collections of the Latest Products"))
        imageList.add(SlideModel(R.drawable.slidertwo, "Complete Collections of Colors and Series"))
        imageList.add(SlideModel(R.drawable.sliderthree, "Find the Most Suitable Outfit for You"))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        imageSlider.startSliding(2000)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 8, vertical = 8))
        recyclerView.setPadding(0, 0, 0, 0)

        // Initialize the adapter with an empty list
        adapter = ProductAdapter(emptyList()) { item ->
            navigateToDetails(item)
        }
        recyclerView.adapter = adapter


//        Handle the Loading
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
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}