package com.shahriar.ichhebazaar.ui.slider

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.ui.login.LoginActivity
import com.shahriar.ichhebazaar.ui.register.RegistrationActivity

class SliderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slider)

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.sliderone, "Various Collections of the Latest Products"))
        imageList.add(SlideModel(R.drawable.slidertwo, "Complete Collections of Colors and Series"))
        imageList.add(SlideModel(R.drawable.sliderthree, "Find the Most Suitable Outfit for You"))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        imageSlider.startSliding(2000)

        val registration = findViewById<Button>(R.id.registration)
        registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            //finish()
        }

        val login = findViewById<TextView>(R.id.login)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //finish()
        }
    }
}