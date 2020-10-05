package com.example.planosycentellas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.planosycentellas.api.Provider
import com.example.planosycentellas.di.MyApplication
import com.example.planosycentellas.repository.Repository
import com.example.planosycentellas.viewmodel.ViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModel.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vm = ViewModelProvider(this, vmFactory)[ViewModel::class.java]

        vm.getPodcastInfo().observe(this, {
                Log.d("TESTING_","HELLO")
                Log.d("TESTING_",it.description)
                Log.d("TESTING_",it.email)
                Log.d("TESTING_",it.image)
                Log.d("TESTING_",it.name)
        })

        vm.getEpisodeList().observe(this, {
            it.forEach { episode -> Log.d("TESTING", episode.title) }
        })
    }
}