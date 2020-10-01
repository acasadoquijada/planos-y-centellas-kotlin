package com.example.planosycentellas

import com.example.planosycentellas.api.Provider
import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonAwards
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProviderUnitTest {

    private val provider = Provider()

    @Test
    fun getPodcastInfo() {
        assertPodcastInfo(provider.getPodcastInfo())
    }

    private fun assertPodcastInfo(podcastInfo: PodcastInfo) {
        assert(podcastInfo.description.isNotEmpty())
        assert(podcastInfo.email.isNotEmpty())
        assert(podcastInfo.image.isNotEmpty())
        assert(podcastInfo.name.isNotEmpty())
    }

    @Test
    fun getEpisodeList() {
        provider.getEpisodeList().forEach { assertEpisode(it) }
    }


    private fun assertEpisode(episode: Episode) {
        assert(episode.image.isNotEmpty())
        assert(episode.description.isNotEmpty())
        assert(episode.title.isNotEmpty())
        assert(episode.url.isNotEmpty())
    }

    @Test
    fun getUpcoming() {
        assert(provider.getUpcoming().isNotEmpty())
    }

    @Test
    fun getPatreonInfo() {
        provider.getPatreonInfo().forEach { assertTierInfo(it) }
    }

    private fun assertTierInfo(tier: PatreonTier) {
        assert(tier.image.isNotEmpty())
        assert(tier.link.isNotEmpty())
        assert(tier.price.isNotEmpty())
        assert(tier.title.isNotEmpty())
        assertPatreonAwards(tier.awards)
    }

    private fun assertPatreonAwards(patreonAwards: PatreonAwards) {
        assert(patreonAwards.initialMessage.isNotEmpty())
        assert(patreonAwards.awardsDetails.isNotEmpty())
    }
}