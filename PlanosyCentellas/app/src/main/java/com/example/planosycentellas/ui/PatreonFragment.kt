package com.example.planosycentellas.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.planosycentellas.R
import com.example.planosycentellas.databinding.FragmentPatreonBinding
import com.example.planosycentellas.model.PatreonTier
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.patreon_tier.view.*

class PatreonFragment : ParentFragment() {

    private var binding: FragmentPatreonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupDataBinding(inflater,container)
        return getRootView()
    }


    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patreon, container,false)
    }

    override fun getRootView(): View?{
        return binding?.root
    }

    override fun setupUI() {

        viewModel.getPatreonInfo().observe(this, { patreonInfo: List<PatreonTier> ->
            patreonInfo.forEachIndexed{
                    index, element -> setupPatreonTierInfo(element, index)
            }
        })
    }

    private fun setupPatreonTierInfo(patreonTier: PatreonTier, index: Int) {

        try {

            val view: View? = when(index) {
                0 -> binding?.patreonTier1
                1 -> binding?.patreonTier2
                2 -> binding?.patreonTier3
                else -> throw Exception("index out of bounds: $index")
            }

            setPatreonTierTitle(view?.title, patreonTier.title)
            setPatreonTierPrice(view?.price, patreonTier.price)
            setPatreonTierImage(view?.image, patreonTier.image)
            setJoinButtonOnClickListener(view?.joinButton, patreonTier.link)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun setPatreonTierTitle(view: TextView?, title: String) {
        view?.text = title
    }

    private fun setPatreonTierPrice(view: TextView?, price: String) {
        view?.text = price
    }

    private fun setPatreonTierImage(view: ImageView?, image: String) {
        Log.d("PATREON", image)
        Picasso.get().load(image).resize(150,150).into(view)
    }

    private fun setJoinButtonOnClickListener(button: Button?, link: String) {
        button?.setOnClickListener{
            launchActivity(link)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}