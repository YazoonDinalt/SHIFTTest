package com.example.shifttest

import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ServerConnection{

    private fun connection(): HttpURLConnection {
        val httpUrlConnection = URL("https://randomuser.me/api/?format=json&results=50").openConnection() as HttpURLConnection
        httpUrlConnection.requestMethod = "GET"
        return httpUrlConnection
    }

    fun getJson(): String {
        val httpUrlConnection = connection()
        val streamReader = InputStreamReader(httpUrlConnection.inputStream)
        var json: String
        streamReader.use { json = it.readText() }
        httpUrlConnection.disconnect()
        return json
    }
}