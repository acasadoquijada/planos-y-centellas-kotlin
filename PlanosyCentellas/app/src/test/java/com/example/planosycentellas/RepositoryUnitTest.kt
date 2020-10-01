package com.example.planosycentellas

import com.example.planosycentellas.api.Provider
import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import com.example.planosycentellas.repository.Repository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class RepositoryUnitTest {

    private lateinit var provider: Provider
    private lateinit var repository: Repository

    @Before
    fun setup() {
        provider = mock(Provider::class.java)
        repository = Repository(provider)
    }

    @Test
    fun `Get podcastInfo`() {

        val expectedPodcastInfo = PodcastInfo()

        Mockito.`when`(provider.getPodcastInfo()).thenReturn(expectedPodcastInfo)

        val actualPodcastInfo = repository.getPodcastInfo()

        Assert.assertEquals(actualPodcastInfo, expectedPodcastInfo)
    }

    @Test
    fun `Get episodeList`(){

        val expectedEpisodeList: List<Episode> = ArrayList()

        Mockito.`when`(provider.getEpisodeList()).thenReturn(expectedEpisodeList)

        val actualEpisodeList = repository.getEpisodeList()

        Assert.assertEquals(actualEpisodeList, expectedEpisodeList)

    }

    @Test
    fun `Get upcoming`(){

        Mockito.`when`(provider.getUpcoming()).thenReturn("upcoming")

        val upcoming = repository.getUpcoming()

        Assert.assertEquals(upcoming, "upcoming")

    }

    @Test
    fun `Get patreonInfo`(){

        val expectedPatreonInfo: List<PatreonTier> = ArrayList()

        Mockito.`when`(provider.getPatreonInfo()).thenReturn(expectedPatreonInfo)

        val actualPatreonInfo = repository.getPatreonInfo()

        Assert.assertEquals(actualPatreonInfo, expectedPatreonInfo)

    }
}
