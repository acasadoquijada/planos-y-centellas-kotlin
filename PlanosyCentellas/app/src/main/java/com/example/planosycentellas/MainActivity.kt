package com.example.planosycentellas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.planosycentellas.api.Provider
import com.example.planosycentellas.databinding.ActivityMainBinding
import com.example.planosycentellas.di.MyApplication
import com.example.planosycentellas.repository.Repository
import com.example.planosycentellas.viewmodel.ViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

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