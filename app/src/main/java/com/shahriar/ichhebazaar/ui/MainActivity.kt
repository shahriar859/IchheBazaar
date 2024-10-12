package com.shahriar.ichhebazaar.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.databinding.ActivityMainBinding
import com.shahriar.ichhebazaar.ui.fragment.cart.CartFragment
import com.shahriar.ichhebazaar.ui.fragment.FavouriteFragment
import com.shahriar.ichhebazaar.ui.fragment.home.HomeFragment
import com.shahriar.ichhebazaar.ui.fragment.NewProductFragment
import com.shahriar.ichhebazaar.ui.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load default fragment (Home) if no previous state
        if (savedInstanceState == null) {
            setFragment(HomeFragment())
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.home.setOnClickListener { navigateTo(HomeFragment(), R.id.home) }
        binding.cart.setOnClickListener { navigateTo(CartFragment(), R.id.cart) }
        binding.favourite.setOnClickListener { navigateTo(FavouriteFragment(), R.id.favourite) }
        binding.profile.setOnClickListener { navigateTo(ProfileFragment(), R.id.profile) }
        binding.add.setOnClickListener { navigateTo(NewProductFragment(), R.id.add) }

        // Set default selected item appearance
        updateNavBar(R.id.home)
    }

    private fun navigateTo(fragment: Fragment, selectedItemId: Int) {
        if (activeFragment == null || fragment::class != activeFragment!!::class) {
            setFragment(fragment)
            updateNavBar(selectedItemId)
        } else {
            showToast("Already on the selected page")
        }
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.setReorderingAllowed(true)  // Optional, depends on your setup
        transaction.commit()
        activeFragment = fragment
    }

    // Update the bottom navigation bar appearance
    private fun updateNavBar(selectedItemId: Int) {
        resetNavBarIcons()  // Reset all icons to default state

        // Set the selected item icon and background
        when (selectedItemId) {
            R.id.home -> setNavItemSelected(binding.home, R.drawable.ic_home)
            R.id.cart -> setNavItemSelected(binding.cart, R.drawable.ic_cart)
            R.id.favourite -> setNavItemSelected(binding.favourite, R.drawable.ic_heart)
            R.id.profile -> setNavItemSelected(binding.profile, R.drawable.ic_person)
            R.id.add -> setNavItemSelected(binding.add, R.drawable.ic_add_circle)
        }
    }

    // Reset all icons to their default state
    private fun resetNavBarIcons() {
        setNavItemDefault(binding.home, R.drawable.ic_home_outline)
        setNavItemDefault(binding.cart, R.drawable.ic_cart_outline)
        setNavItemDefault(binding.favourite, R.drawable.ic_heart_outline)
        setNavItemDefault(binding.profile, R.drawable.ic_person_outline)
        setNavItemDefault(binding.add, R.drawable.ic_add_circle_outline)
    }

    // Helper function to set an icon as selected
    private fun setNavItemSelected(item: ImageView, iconRes: Int) {
        item.apply {
            setImageResource(iconRes)
            setBackgroundResource(R.drawable.circle_background)
        }
    }

    // Helper function to reset an icon to its default state
    private fun setNavItemDefault(item: ImageView, iconRes: Int) {
        item.apply {
            setImageResource(iconRes)
            background = null
        }
    }

    // Show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
