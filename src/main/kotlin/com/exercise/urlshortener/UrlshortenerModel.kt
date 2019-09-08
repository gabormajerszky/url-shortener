package com.exercise.urlshortener

import com.exercise.urlshortener.database.DatabaseHandler

class UrlshortenerModel(private val requestParam: String,
                        private val database: DatabaseHandler,
                        private val idGenerator: IdGenerator) {

    fun createShortUrl(host: String, longUrl: String) : String {
        val id = idGenerator.getNext()
        val shortUrl = "$host?$requestParam=$id"
        database.save(id, longUrl)
        return shortUrl
    }

    fun getLongUrl(id: String) : String {
        return database.retrieve(id).orEmpty()
    }

}
