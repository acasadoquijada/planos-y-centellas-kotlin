package com.example.planosycentellas.di

import android.content.Context
import com.example.planosycentellas.MainActivity
import com.example.planosycentellas.ui.ParentFragment
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {
    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ParentFragment)
}