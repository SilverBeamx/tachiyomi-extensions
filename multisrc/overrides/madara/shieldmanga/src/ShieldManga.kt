package eu.kanade.tachiyomi.extension.en.shieldmanga

import eu.kanade.tachiyomi.lib.ratelimit.RateLimitInterceptor
import eu.kanade.tachiyomi.multisrc.madara.Madara
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ShieldManga : Madara("Shield Manga", "https://shieldmanga.io", "en") {
    private val rateLimitInterceptor = RateLimitInterceptor(1)

    override val client: OkHttpClient = network.cloudflareClient.newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addNetworkInterceptor(rateLimitInterceptor)
        .build()

    // The website does not flag the content, so we just use the old selector.
    override fun popularMangaSelector() =
        "div.page-item-detail:not(:has(a[href*='bilibilicomics.com']))"

    override fun chapterListSelector() = "li.wp-manga-hapter, .version-chap li"
}
