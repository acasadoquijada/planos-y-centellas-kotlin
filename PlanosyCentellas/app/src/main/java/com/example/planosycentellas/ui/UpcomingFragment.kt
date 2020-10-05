package com.example.planosycentellas.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.planosycentellas.R
import com.example.planosycentellas.databinding.FragmentUpcomingBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class UpcomingFragment : ParentFragment() {

    private lateinit var binding: FragmentUpcomingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater, container)
        return getRootView()
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun setupUI() {
        viewModel.getUpcoming().observe(requireActivity(), {
            Picasso.get().load(it).into(binding.upcomingImage)
        })
    }

    // This part needs to be reviewed and cleaned
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.share_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> shareUpcomingImage()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun shareUpcomingImage() {

        val bitmap = getBitmapFromView(binding.upcomingImage)

        bitmap?.let {
            val uri = saveImage(bitmap)
            if (uri != null) {
                shareImageUri(uri)
            }
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // https://stackoverflow.com/questions/52642055/view-getdrawingcache-is-deprecated-in-android-api-28
    private fun saveImage(image: Bitmap): Uri? {
        //TODO - Should be processed in another thread
        val imagesFolder = File(requireActivity().cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(requireContext(), "com.mydomain.fileprovider", file)
        } catch (e: IOException) {
            Log.d("TAG", "IOException while trying to write file for sharing: " + e.message)
        }
        return uri
    }


    // https://stackoverflow.com/questions/33222918/sharing-bitmap-via-android-intent
    private fun shareImageUri(uri: Uri) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"

        val shareIntent = Intent.createChooser(intent, "Planos y Centellas")
        startActivity(shareIntent)
    }


}