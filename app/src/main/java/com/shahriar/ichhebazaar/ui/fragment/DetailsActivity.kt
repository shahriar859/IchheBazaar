package com.shahriar.ichhebazaar.ui.fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.data.product.Product
import com.shahriar.ichhebazaar.databinding.ActivityDetailsBinding
import java.util.Locale

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<Product>("PRODUCT")

        binding.productTitle.text = product?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        binding.oldPrice.text = "৳${product?.price} TK"
        binding.productQuantity.text = "Quantity: ${product?.quantity.toString()}"
        binding.textDiscount.text = "Save: ৳${product?.discount}"
        binding.prodDescription.text = product?.description
        binding.categoryName.text = product?.category?.name
        binding.brandName.text = product?.brand?.name
        binding.shippingCost.text = "৳${product?.shipping_cost}"

        binding.imageProduct.load(product?.main_image){
            crossfade(true)
            placeholder(R.drawable.placeholder)  // Default image while loading
            error(R.drawable.placeholder)
        }

    }
}