package com.example.planosycentellas.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planosycentellas.R
import com.example.planosycentellas.databinding.FragmentHomeBinding
import com.example.planosycentellas.databinding.FragmentUpcomingBinding
import com.example.planosycentellas.di.MyApplication
import com.example.planosycentellas.viewmodel.ViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.patreon_tier.*
import javax.inject.Inject


class HomeFragment : ParentFragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater, container)

        return getRootView()
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
    }

    override fun getRootView(): View? {
        return binding?.root
    }

    override fun setupUI() {
        observePodcastInfo()
    }

    private fun observePodcastInfo() {
        viewModel.getPodcastInfo().observe(this, {
            setTitle(it.name)
            setContactInfo(it.email)
            setDescription(it.description)
            setPodcastImage(it.image)
        })
    }

    private fun setTitle(title: String) {
        binding?.title?.text = title
    }

    private fun setContactInfo(contactInfo: String) {
        binding?.contactInfo?.text = contactInfo
    }

    private fun setDescription(description: String) {
        binding?.description?.text = description
    }

    private fun setPodcastImage(imagePath: String) {
        Picasso.get().load(imagePath).into(binding?.podcastImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}