package com.example.planosycentellas.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.planosycentellas.R
import com.example.planosycentellas.databinding.FragmentSocialNetworkBinding
import com.example.planosycentellas.model.SocialNetwork

class SocialNetworkFragment : ParentFragment() {

    private lateinit var binding: FragmentSocialNetworkBinding

    private val socialNetworkList: MutableList<SocialNetwork> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupDataBinding(inflater, container)
        setupSocialNetworkInfo()
        return getRootView()
    }


    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_social_network,
            container,
            false
        )
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun setupUI() {
        socialNetworkList.forEach {
            setupListener(it.name, it.url)
        }
    }

    private fun setupListener(name: ImageView, url: String) {
        name.setOnClickListener {
            launchActivity(url)
        }

        name.setOnLongClickListener {
            shareURL(url)
            true
        }
    }

    private fun launchActivity(url: String) {
        val packageManager = requireActivity().packageManager
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "There is an error\nGo to: $url", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun shareURL(url: String) {
        val shareIntent = Intent.createChooser(createIntent(url), null)
        startActivity(shareIntent)
    }

    private fun createIntent(url: String): Intent {

        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
    }

    private fun setupSocialNetworkInfo() {
        val ivooxUrl = "https://www.ivoox.com/podcast-planos-centellas_sq_f1609149_1.html"
        val youtubeUrl = "https://www.youtube.com/channel/UCLacP2BYwAAJISa7-fAj64g"
        val twitterUrl = "https://twitter.com/planoscentellas?lang=en"
        val instagramUrl = "https://www.instagram.com/planos_y_centellas/?hl=en"
        val itunesUrl = "https://podcasts.apple.com/us/podcast/planos-y-centellas/id1444091704"
        val facebookUrl =
            "https://www.facebook.com/pages/category/Podcast/Planos-y-Centellas-1950069131742290/"
        val spotifyUrl = "https://open.spotify.com/show/78SRCbyUZei41U33ZkVDme"
        val twitchUrl = "https://www.twitch.tv/planos_y_centellas"

        socialNetworkList.add(SocialNetwork(binding.ivoox, ivooxUrl))
        socialNetworkList.add(SocialNetwork(binding.instagram, instagramUrl))
        socialNetworkList.add(SocialNetwork(binding.youtube, youtubeUrl))
        socialNetworkList.add(SocialNetwork(binding.facebook, facebookUrl))
        socialNetworkList.add(SocialNetwork(binding.itunes, itunesUrl))
        socialNetworkList.add(SocialNetwork(binding.twitter, twitterUrl))
        socialNetworkList.add(SocialNetwork(binding.spotify, spotifyUrl))
        socialNetworkList.add(SocialNetwork(binding.twitch, twitchUrl))
    }
}