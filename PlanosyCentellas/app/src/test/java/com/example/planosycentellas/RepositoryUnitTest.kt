package com.example.planosycentellas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.planosycentellas.api.Provider
import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import com.example.planosycentellas.repository.Repository
import getOrAwaitValue
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
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

        assertEquals(repository.getPodcastInfo(), expectedPodcastInfo)

    }

    @Test
    fun `Get episodeList`(){

        val expectedEpisodeList: List<Episode> = ArrayList()

        Mockito.`when`(provider.getEpisodeList()).thenReturn(expectedEpisodeList)

        assertEquals(repository.getEpisodeList(), expectedEpisodeList)

    }

    @Test
    fun `Get upcoming`(){

        Mockito.`when`(provider.getUpcoming()).thenReturn("upcoming")

        assertEquals(repository.getUpcoming(), "upcoming")

    }

    @Test
    fun `Get patreonInfo`(){

        val expectedPatreonInfo: List<PatreonTier> = ArrayList()

        Mockito.`when`(provider.getPatreonInfo()).thenReturn(expectedPatreonInfo)

        Assert.assertEquals(repository.getPatreonInfo(), expectedPatreonInfo)

    }
}
