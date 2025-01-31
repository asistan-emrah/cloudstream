package com.filmekseni

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class FilmekseniPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(FilmekseniProvider())
    }
}
