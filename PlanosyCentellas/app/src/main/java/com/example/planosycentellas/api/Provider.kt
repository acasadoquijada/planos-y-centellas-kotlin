package com.example.planosycentellas.api

import com.example.planosycentellas.model.Episode
import com.example.planosycentellas.model.PatreonAwards
import com.example.planosycentellas.model.PatreonTier
import com.example.planosycentellas.model.PodcastInfo
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.util.*

class Provider {

    private val ivoox_url = "https://www.ivoox.com/podcast-planos-centellas_fg_f1609149_filtro_1.xml"
    private val ivoox_news_url = "https://www.ivoox.com/planos-centellas_pr_posts_609149_1.html"
    private val patreon_url = "https://www.patreon.com/planosycentellas"

    fun getPodcastInfo(): PodcastInfo {

        val podcastInfo = PodcastInfo()

        val doc = Jsoup.connect(ivoox_url).get().parser(Parser.xmlParser())

        podcastInfo.description = doc.select("description")[0].text()

        podcastInfo.name = (doc.select("title")[0].text())
        podcastInfo.image =(doc.select("itunes|image").attr("href"))
        podcastInfo.email = (doc.select("itunes|email").text())

        return podcastInfo
    }

    fun getEpisodeList(): List<Episode> {

        val episodeList: MutableList<Episode> = ArrayList()

        val doc = Jsoup.connect(ivoox_url).get().parser(Parser.xmlParser())

        val elements = doc.select("item")

        elements.forEach { element ->
            val episode = Episode()

            episode.title = getEpisodeTitle(element)

            episode.description = getEpisodeDescription(element)

            episode.url = getEpisodeUrl(element)

            episode.image = getEpisodeImage(element)

            episodeList.add(episode)

        }

        return episodeList
    }

    private fun getEpisodeTitle(element: Element): String {
        return element.select("title").text()
    }

    private fun getEpisodeDescription(element: Element): String {
        return element.select("description").text()
    }

    private fun getEpisodeUrl(element: Element): String {
        return element.select("enclosure").attr("url")
    }

    private fun getEpisodeImage(element: Element): String {
        return element.select("itunes|image").attr("href")
    }

    fun getUpcoming(): String {
        val doc = Jsoup.connect(ivoox_news_url).get()

        val elements = doc.select("div.container.container-xl")

        return elements[0].select("div.m-bottom-10").select("a").attr("href")
    }

    fun getPatreonInfo(): List<PatreonTier> {

        val patreonTierList: MutableList<PatreonTier> = ArrayList()

        val doc = Jsoup.connect(patreon_url).get()

        val elements = doc.select("div.sc-fzoLsD.cCFuMf")

        elements.forEach { element ->
            val patreonTier = PatreonTier()

            patreonTier.title = getPatreonTitle(element)

            patreonTier.price = getPatreonPrice(element)

            patreonTier.image = getPatreonImage(element)

            patreonTier.link = getPatreonUrl(element)

            patreonTier.awards = getPatreonAwards(element)

            patreonTierList.add(patreonTier)
        }

        return patreonTierList
    }


    private fun getPatreonTitle(element: Element): String {
        return element.select("div.sc-AxjAm.kGRoiw").text()
    }

    private fun getPatreonPrice(element: Element): String {
        return (element.select(
            "div.sc-AxjAm.bdDRMi"
        ).text() + " "
                + element.select("div.sc-AxjAm.ufKCT").text() + " "
                + element.select("div.sc-AxjAm.hpINne").text())
    }

    private fun getPatreonImage(element: Element): String {
        return element.select(
            "div.sc-fzoLsD.cBkBik"
        ).select("img").attr("src")
    }

    private fun getPatreonUrl(element: Element): String {
        return "https://www.patreon.com" + element.select(
            "a.sc-fzoiQi.hrhoNA.ibazdf-0.kYJzfB"
        ).attr("href")
    }

    private fun getPatreonAwards(element: Element): PatreonAwards {
        val patreonAwards = PatreonAwards()
        patreonAwards.initialMessage = getAwardInitialMessage(element)
        patreonAwards.awardsDetails = getPatreonAwardsDetails(element)
        return patreonAwards
    }

    private fun getAwardInitialMessage(element: Element): String {

        // Initial message
        var initialMessage = element.select("div.sc-1rlfkev-0.yMRiI").select("div").text()

        // Get only the initial message
        initialMessage = initialMessage.substring(0, initialMessage.indexOf("Recompensas"))
        return initialMessage
    }

    private fun getPatreonAwardsDetails(element: Element): List<String> {

        val awards: MutableList<String> = ArrayList()
        val size = element.select("div.sc-1rlfkev-0.yMRiI").select("div").select("li").size
        for (j in 0 until size) {
            awards.add(
                element.select("div.sc-1rlfkev-0.yMRiI").select("div").select("li")[j].text()
            )
        }
        return awards
    }

}