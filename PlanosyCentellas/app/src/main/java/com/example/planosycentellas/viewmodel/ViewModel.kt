package com.example.planosycentellas.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import com.example.planosycentellas.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    private lateinit var podcastInfo: MutableLiveData<PodcastInfo>
    private lateinit var episodeList: MutableLiveData<List<Episode>>
    private lateinit var upcoming: MutableLiveData<String>
    private lateinit var patreonInfo: MutableLiveData<List<PatreonTier>>

    fun getPodcastInfo(): LiveData<PodcastInfo> {

        if(!this::podcastInfo.isInitialized) {
            podcastInfo = MutableLiveData()
            setPodcastInfo()
        }
        return podcastInfo
    }

    fun getEpisodeList(): LiveData<List<Episode>> {

        if(!this::episodeList.isInitialized) {
            episodeList = MutableLiveData()
            setEpisodeList()
        }
        return episodeList
    }

    fun getUpcoming(): LiveData<String> {

        if(!this::upcoming.isInitialized) {
            upcoming = MutableLiveData()
            setUpcoming()
        }
        return upcoming
    }

    fun getPatreonInfo(): LiveData<List<PatreonTier>> {

        if(!this::patreonInfo.isInitialized) {
            patreonInfo = MutableLiveData()
            setPatreonInfo()
        }
        return patreonInfo
    }

    private fun setPodcastInfo() = viewModelScope.launch(Dispatchers.IO) {
        podcastInfo.postValue(repository.getPodcastInfo())
    }

    private fun setEpisodeList() = viewModelScope.launch(Dispatchers.IO) {
        episodeList.postValue(repository.getEpisodeList())
    }

    private fun setUpcoming() = viewModelScope.launch(Dispatchers.IO) {
        upcoming.postValue(repository.getUpcoming())
    }

    private fun setPatreonInfo() = viewModelScope.launch(Dispatchers.IO) {
        patreonInfo.postValue(repository.getPatreonInfo())
    }

    /* Factory for creating FeatureViewModel instances */
    //https://medium.com/chili-labs/android-viewmodel-injection-with-dagger-f0061d3402ff
    class Factory @Inject constructor(private var repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ViewModel(repository) as T
        }
    }

}