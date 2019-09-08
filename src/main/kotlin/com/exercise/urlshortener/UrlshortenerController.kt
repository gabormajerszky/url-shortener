package com.exercise.urlshortener

import com.exercise.urlshortener.database.StubDatabaseHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

/*
 * Using localhost as an example, shortening the URL https://www.google.com/search?q=kotlin+reference
 *
 * The URL needs to be encoded and added as a query parameter:
 * http://localhost:8080/shorten?url=https%3A%2F%2Fwww.google.com%2Fsearch%3Fq%3Dkotlin%2Breference
 *
 * The resulting shortened URL will be in the form:
 * http://localhost:8080?id=1
 */

@RestController
class UrlshortenerController {

    val model = UrlshortenerModel("id", StubDatabaseHandler(), IdGenerator())

    /*
     * Entry point for generating a shorter URL
     * Uses the request header to determine the host
     * More complex URLs need to be URL-encoded
     */

    @GetMapping("/shorten")
    @Synchronized
    fun shorten(@RequestHeader(value = "host") host: String,
                @RequestParam(value = "url") longUrl: String) : Response {
        val shortUrl = model.createShortUrl(host, longUrl)
        return Response(longUrl, shortUrl)
    }

    /*
     * Entry point for the shortened URL
     * Looks up the original url and sends back a redirect response
     */

    @GetMapping("/")
    @Synchronized
    fun redirect(@RequestParam(value = "id") id: String) : Any {
        val longUrl = model.getLongUrl(id)
        return when (longUrl) {
            "" -> "URL not found"
            else -> RedirectView(longUrl)
        }
    }

}
