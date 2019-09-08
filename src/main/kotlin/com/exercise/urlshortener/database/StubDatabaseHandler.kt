package com.exercise.urlshortener.database

class StubDatabaseHandler : DatabaseHandler {

    private val urlMap = HashMap<String, String>()

    override fun save(id: String, url: String) {
        urlMap[id] = url
    }

    override fun retrieve(id: String): String? {
        return urlMap[id]
    }

}
