package com.exercise.urlshortener

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.servlet.view.RedirectView

@RunWith(SpringRunner::class)
@SpringBootTest
class UrlshortenerApplicationTests {

    val controller = UrlshortenerController()

    @Test
    fun contextLoads() {
        assertThat(controller).isNotNull
    }

    @Test
    fun getsCorrectShortUrl() {
        val longUrl = "http://localhost:8080/shorten?url=https://www.google.com/search?q=kotlin+reference"
        val response = controller.shorten("example.com", longUrl)
        assertEquals("example.com?id=1", response.shortUrl)
        assertEquals(longUrl, response.longUrl)
    }

    @Test
    fun shortUrlRedirectsCorrectly() {
        val longUrl = "http://localhost:8080/shorten?url=https://www.google.com/search?q=kotlin+reference"
        controller.shorten("", longUrl)
        val view = controller.redirect("1") as RedirectView
        assertEquals(longUrl, view.url)
    }

    @Test
    fun returnsUrlNotFoundMessage() {
        val result = controller.redirect("abc") as String
        assertEquals("URL not found", result)
    }

}
