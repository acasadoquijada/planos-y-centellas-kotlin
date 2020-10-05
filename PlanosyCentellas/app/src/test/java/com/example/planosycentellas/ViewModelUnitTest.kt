package com.example.planosycentellas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import com.example.planosycentellas.repository.Repository
import com.example.planosycentellas.viewmodel.ViewModel
import getOrAwaitValue
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.doReturn

@RunWith(JUnit4::class)
class ViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: Repository
    private lateinit var viewModel: ViewModel

    @Before
    fun setup() {
        repository = Mockito.mock(Repository::class.java)
        viewModel = ViewModel(repository)
    }

    @Test
    fun `Get podcastInfo`() {

        val expectedPodcastInfo = PodcastInfo()

        // https://stackoverflow.com/questions/11121772/when-i-run-mockito-test-occurs-wrongtypeofreturnvalue-exception
        doReturn(expectedPodcastInfo).`when`(repository).getPodcastInfo()

        assertEquals(viewModel.getPodcastInfo().getOrAwaitValue(),expectedPodcastInfo)
    }

    @Test
    fun `Get episodeList`() {

        val expectedEpisodeList = ArrayList<Episode>()

        // https://stackoverflow.com/questions/11121772/when-i-run-mockito-test-occurs-wrongtypeofreturnvalue-exception
        doReturn(expectedEpisodeList).`when`(repository).getEpisodeList()

        assertEquals(viewModel.getEpisodeList().getOrAwaitValue(),expectedEpisodeList)
    }

    @Test
    fun `Get upcoming`() {

        val expectedUpcoming = "upcoming"

        // https://stackoverflow.com/questions/11121772/when-i-run-mockito-test-occurs-wrongtypeofreturnvalue-exception
        doReturn(expectedUpcoming).`when`(repository).getUpcoming()

        assertEquals(viewModel.getUpcoming().getOrAwaitValue(),expectedUpcoming)
    }

    @Test
    fun `Get patreonInfo`() {

        val expectedPatreonInfo = ArrayList<PatreonTier>()

        // https://stackoverflow.com/questions/11121772/when-i-run-mockito-test-occurs-wrongtypeofreturnvalue-exception
        doReturn(expectedPatreonInfo).`when`(repository).getPatreonInfo()

        assertEquals(viewModel.getPatreonInfo().getOrAwaitValue(),expectedPatreonInfo)
    }

}