package com.filmekseni

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.utils.loadExtractor
import org.jsoup.nodes.Element

class FilmekseniProvider : MainAPI() {
    override var mainUrl = "https://filmekseni.net"
    override var name = "Filmekseni"
    override val hasMainPage = true
    override var lang = "tr"
    override val hasDownloadSupport = true
    override val supportedTypes = setOf(
        TvType.Movie,
        TvType.TvSeries,
        TvType.Anime,
        TvType.AnimeMovie
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get(mainUrl).document
        val lists = ArrayList<HomePageList>()

        // Son Eklenen Filmler
        document.select("div.son-eklenen-filmler div.film-card").map { film ->
            val title = film.selectFirst("h2.film-title")?.text() ?: ""
            val poster = film.selectFirst("img")?.attr("src") ?: ""
            val link = film.selectFirst("a")?.attr("href") ?: ""
            val year = film.selectFirst("span.year")?.text()?.toIntOrNull()
            val rating = film.selectFirst("span.imdb")?.text()?.toDoubleOrNull()

            MovieSearchResponse(
                title,
                link,
                this.name,
                TvType.Movie,
                poster,
                year,
                null,
                rating
            )
        }.let { movieList ->
            if (movieList.isNotEmpty()) lists.add(HomePageList("Son Eklenen Filmler", movieList))
        }

        // Popüler Filmler
        document.select("div.populer-filmler div.film-card").map { film ->
            val title = film.selectFirst("h2.film-title")?.text() ?: ""
            val poster = film.selectFirst("img")?.attr("src") ?: ""
            val link = film.selectFirst("a")?.attr("href") ?: ""
            val year = film.selectFirst("span.year")?.text()?.toIntOrNull()
            val rating = film.selectFirst("span.imdb")?.text()?.toDoubleOrNull()

            MovieSearchResponse(
                title,
                link,
                this.name,
                TvType.Movie,
                poster,
                year,
                null,
                rating
            )
        }.let { movieList ->
            if (movieList.isNotEmpty()) lists.add(HomePageList("Popüler Filmler", movieList))
        }

        return HomePageResponse(lists)
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/arama/$query"
        val document = app.get(url).document

        return document.select("div.film-card").map { film ->
            val title = film.selectFirst("h2.film-title")?.text() ?: ""
            val href = film.selectFirst("a")?.attr("href") ?: ""
            val poster = film.selectFirst("img")?.attr("src") ?: ""
            val year = film.selectFirst("span.year")?.text()?.toIntOrNull()
            val rating = film.selectFirst("span.imdb")?.text()?.toDoubleOrNull()
            
            MovieSearchResponse(
                title,
                href,
                this.name,
                TvType.Movie,
                poster,
                year,
                null,
                rating
            )
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document

        val title = document.selectFirst("h1.film-title")?.text() ?: ""
        val poster = document.selectFirst("div.film-poster img")?.attr("src") ?: ""
        val plot = document.selectFirst("div.film-description")?.text() ?: ""
        val year = document.selectFirst("span.year")?.text()?.toIntOrNull()
        val rating = document.selectFirst("span.imdb")?.text()?.toDoubleOrNull()
        val duration = document.selectFirst("span.duration")?.text()?.substringBefore(" dk")?.toIntOrNull()
        val tags = document.select("div.genres a").map { it.text() }

        val type = if (document.select("div.episodes").isNotEmpty()) TvType.TvSeries else TvType.Movie

        val recommendations = document.select("div.benzer-filmler div.film-card").map { film ->
            val recTitle = film.selectFirst("h2.film-title")?.text() ?: ""
            val recHref = film.selectFirst("a")?.attr("href") ?: ""
            val recPoster = film.selectFirst("img")?.attr("src") ?: ""
            val recYear = film.selectFirst("span.year")?.text()?.toIntOrNull()
            val recRating = film.selectFirst("span.imdb")?.text()?.toDoubleOrNull()
            
            MovieSearchResponse(
                recTitle,
                recHref,
                this.name,
                TvType.Movie,
                recPoster,
                recYear,
                null,
                recRating
            )
        }

        return if (type == TvType.TvSeries) {
            val episodes = document.select("div.episode").map { ep ->
                val epTitle = ep.selectFirst("span.episode-title")?.text() ?: ""
                val epHref = ep.selectFirst("a")?.attr("href") ?: ""
                val epNumber = ep.selectFirst("span.episode-number")?.text()?.toIntOrNull()
                val epSeason = ep.selectFirst("span.season-number")?.text()?.toIntOrNull() ?: 1
                Episode(
                    epHref,
                    epTitle,
                    epSeason,
                    epNumber
                )
            }
            
            TvSeriesLoadResponse(
                title,
                url,
                this.name,
                type,
                episodes,
                poster,
                year,
                plot,
                null,
                rating,
                tags,
                duration,
                recommendations
            )
        } else {
            MovieLoadResponse(
                title,
                url,
                this.name,
                type,
                url,
                poster,
                year,
                plot,
                rating,
                tags,
                duration,
                recommendations
            )
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        val videoLinks = document.select("div.video-player iframe")

        videoLinks.forEach { iframe ->
            val src = iframe.attr("src")
            loadExtractor(src, data, subtitleCallback, callback)
        }

        // Altyazı dosyalarını kontrol et ve ekle
        document.select("track[kind=captions]").forEach { track ->
            val subSrc = track.attr("src")
            val subLabel = track.attr("label")
            val subLang = track.attr("srclang")
            
            subtitleCallback.invoke(
                SubtitleFile(
                    subLang,
                    subSrc,
                    subLabel
                )
            )
        }

        return true
    }
}
