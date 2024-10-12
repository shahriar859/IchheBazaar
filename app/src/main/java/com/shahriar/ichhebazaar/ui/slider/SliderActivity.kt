package com.shahriar.ichhebazaar.ui.slider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.databinding.ActivitySliderBinding
import com.shahriar.ichhebazaar.ui.login.LoginActivity
import com.shahriar.ichhebazaar.ui.register.RegistrationActivity

class SliderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.sliderone, "Various Collections of the Latest Products"))
        imageList.add(SlideModel(R.drawable.slidertwo, "Complete Collections of Colors and Series"))
        imageList.add(SlideModel(R.drawable.sliderthree, "Find the Most Suitable Outfit for You"))

        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        binding.imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        binding.imageSlider.startSliding(2000)

        binding.registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
