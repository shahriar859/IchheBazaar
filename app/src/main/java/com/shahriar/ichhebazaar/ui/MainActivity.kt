package com.shahriar.ichhebazaar.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.ui.fragment.CartFragment
import com.shahriar.ichhebazaar.ui.fragment.FavouriteFragment
import com.shahriar.ichhebazaar.ui.fragment.HomeFragment
import com.shahriar.ichhebazaar.ui.fragment.NewProductFragment
import com.shahriar.ichhebazaar.ui.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var home: ImageView
    private lateinit var cart: ImageView
    private lateinit var favourite: ImageView
    private lateinit var profile: ImageView
    private lateinit var add: ImageView
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        home = findViewById(R.id.home)
        cart = findViewById(R.id.cart)
        favourite = findViewById(R.id.favourite)
        profile = findViewById(R.id.profile)
        add = findViewById(R.id.add)

        // Load the default fragment (Home)
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, homeFragment)
                .commit()
            activeFragment = homeFragment
        }

        setupBottomNav()
    }

    //button Click Listener
    private fun setupBottomNav() {
        home.setOnClickListener {
            switchFragment(HomeFragment(), R.id.home)
        }
        cart.setOnClickListener {
            switchFragment(CartFragment(), R.id.cart)
        }
        favourite.setOnClickListener {
            switchFragment(FavouriteFragment(), R.id.favourite)
        }
        profile.setOnClickListener {
            switchFragment(ProfileFragment(), R.id.profile)
        }
        add.setOnClickListener {
            switchFragment(NewProductFragment(), R.id.add)
        }

        // Set default selected item
        updateNavBar(R.id.home)
    }

    // Replace fragment only if the new fragment is different
    private fun switchFragment(newFragment: Fragment, selectedItemId: Int) {
        if (activeFragment == null || newFragment::class != activeFragment!!::class) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, newFragment)
                commit()
            }
            activeFragment = newFragment
            updateNavBar(selectedItemId)
        } else {
            Toast.makeText(this, "Already on the selected page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNavBar(selectedItemId: Int) {
        // Reset all icons to default state
        resetNavBarIcons()

        // Change the icon and background for the selected item
        when (selectedItemId) {
            R.id.home -> {
                home.apply {
                    setImageResource(R.drawable.ic_home)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.cart -> {
                cart.apply {
                    setImageResource(R.drawable.ic_cart)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.favourite -> {
                favourite.apply {
                    setImageResource(R.drawable.ic_heart)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.profile -> {
                profile.apply {
                    setImageResource(R.drawable.ic_person)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.add -> {
                add.apply {
                    setImageResource(R.drawable.ic_add_circle)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
        }
    }

    private fun resetNavBarIcons() {
        home.apply {
            setImageResource(R.drawable.ic_home_outline)
            background = null
        }
        cart.apply {
            setImageResource(R.drawable.ic_cart_outline)
            background = null
        }
        favourite.apply {
            setImageResource(R.drawable.ic_heart_outline)
            background = null
        }
        profile.apply {
            setImageResource(R.drawable.ic_person_outline)
            background = null
        }
        add.apply {
            setImageResource(R.drawable.ic_add_circle_outline)
            background = null
        }
    }

}