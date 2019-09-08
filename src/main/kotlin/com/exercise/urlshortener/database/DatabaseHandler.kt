package com.exercise.urlshortener.database

interface DatabaseHandler {

    fun save(id: String, url: String)

    fun retrieve(id: String) : String?

}
