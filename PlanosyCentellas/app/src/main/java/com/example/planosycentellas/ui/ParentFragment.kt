package com.example.planosycentellas.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.planosycentellas.di.MyApplication
import com.example.planosycentellas.viewmodel.ViewModel
import javax.inject.Inject

/*
class FragmentViewBindingProperty<T : ViewBinding>(
    private val viewBinder: ViewBinder<T>
) : ReadOnlyProperty<Fragment, T>
 */
abstract class ParentFragment: Fragment() {

    @Inject
    lateinit var vmFactory: ViewModel.Factory

    lateinit var viewModel: ViewModel

    abstract fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?)

    abstract fun getRootView(): View?

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewModelAndSetupUI()
    }

    private fun getViewModelAndSetupUI() {
        getViewModel()
        setupUI()
    }

    private fun getViewModel() {
        viewModel = ViewModelProvider(this, vmFactory)[ViewModel::class.java]
    }

    abstract fun setupUI()
}