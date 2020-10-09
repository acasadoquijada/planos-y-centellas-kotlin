package com.example.planosycentellas.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planosycentellas.R
import com.example.planosycentellas.adapter.EpisodeListAdapter
import com.example.planosycentellas.databinding.FragmentEpisodeListBinding
import com.example.planosycentellas.model.Episode

class EpisodeListFragment : ParentFragment(),
    EpisodeListAdapter.EpisodeClickListener, EpisodeListAdapter.EpisodeLongClickListener {

    private var binding: FragmentEpisodeListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater, container)
        return getRootView()
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_episode_list,
            container,
            false
        )
    }

    override fun getRootView(): View ? {
        return binding?.root
    }

    override fun setupUI() {
        setupRecyclerView()
        observeEpisodeList()
    }

    private fun setupRecyclerView() {
        setupAdapter()
        setupLayoutManager()
    }

    private fun setupAdapter() {
        binding?.episodeListRecyclerView?.adapter = EpisodeListAdapter(this, this)
    }

    private fun setupLayoutManager() {
        binding?.episodeListRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    private fun observeEpisodeList() {
        viewModel.getEpisodeList().observe(this, {
            updateEpisodeListAdapter(it)
        })
    }

    private fun updateEpisodeListAdapter(episodeList: List<Episode>) {
        (binding?.episodeListRecyclerView?.adapter as EpisodeListAdapter).setEpisodeList(episodeList)
    }

    override fun onEpisodeClicked(url: String) {
        launchActivity(url)
    }

    override fun onEpisodeLongClicked(url: String) {
        val string = "Esucha este episodio de Planos y Centellas: $url"
        shareURL(string)
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

    /**
     * Setup search functionality
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.action_search)

        val searchView: SearchView? = searchItem?.actionView as SearchView

        setOnActionExpandListener(searchItem, searchView)

        setOnQueryTextListener(searchView)
    }

    private fun setOnActionExpandListener(searchItem: MenuItem?, searchView: SearchView?) {

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            /**
             * onMenuItemActionExpand sets iconified to false and requests the focusFromTouch for the
             * searchView element. This is done for a better user experience, the searchView is expanded and
             * keyboard launched when the user clicks on the search icon
             */
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                searchView?.isIconified = false
                searchView?.requestFocusFromTouch()
                return true
            }

            /** onMenuItemActionCollapse clear the query (this is for usability purposes) and call the
             * cleanQuery method of ViewModel. See the method for more information about it.
             */
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                searchView?.setQuery("", false)
                return true
            }
        })
    }

    private fun setOnQueryTextListener(searchView: SearchView?) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return handleQueryChange(s)
            }
        })
    }

    private fun handleQueryChange(query: String): Boolean {
        updateAdapterElements(query)
        return false
    }

    private fun updateAdapterElements(query: String) {
        viewModel.searchEpisode(query).observe(this, {
            updateEpisodeListAdapter(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}