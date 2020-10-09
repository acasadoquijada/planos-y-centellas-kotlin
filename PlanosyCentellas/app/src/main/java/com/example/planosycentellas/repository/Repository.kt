package com.example.planosycentellas.repository

import com.example.planosycentellas.api.Provider
import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import javax.inject.Inject

class Repository @Inject constructor(private val provider: Provider) {

    fun getPodcastInfo(): PodcastInfo {
        return provider.getPodcastInfo()
    }

    fun getEpisodeList(): List<Episode> {
        return provider.getEpisodeList()
    }

    fun getUpcoming(): String {
        return provider.getUpcoming()
    }

    fun getPatreonInfo(): List<PatreonTier> {
        return provider.getPatreonInfo()
    }
}