package com.example.planosycentellas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.planosycentellas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setUpBottomNavigation()
    }


    private fun setUpBottomNavigation() {
        NavigationUI.setupWithNavController(
            mBinding.bottomNavigation,
            Navigation.findNavController(this, R.id.nav_host_fragment)
        )
    }

    private fun setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}