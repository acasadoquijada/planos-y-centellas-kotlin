package com.example.planosycentellas.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.planosycentellas.repository.Repository
import com.example.planosycentellas.viewmodel.ViewModel
import javax.inject.Inject

open class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }
}