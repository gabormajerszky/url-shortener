package com.exercise.urlshortener

import com.exercise.urlshortener.database.StubDatabaseHandler
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

/*
 * The URL needs to be added to the POST request body
 * Using localhost as an example, the resulting shortened URL will be in the form:
 * http://localhost:8080?id=1
 */

@RestController
class UrlshortenerController {

    val model = UrlshortenerModel("id", StubDatabaseHandler(), IdGenerator())

    /*
     * Entry point for generating a shorter URL
     * Uses the request header to determine the host
     */

    @PostMapping("/shorten")
    @Synchronized
    fun shorten(@RequestHeader(value = "host") host: String,
                @RequestBody longUrl: String) : Response {

        println("POST: shorten request")
        println("Request header: $host")
        println("Request Body: $longUrl")

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

        print("GET: redirect request")

        val longUrl = model.getLongUrl(id)
        return when (longUrl) {
            "" -> "URL not found"
            else -> RedirectView(longUrl)
        }
    }

}
