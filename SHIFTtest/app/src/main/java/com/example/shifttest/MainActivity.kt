package com.example.shifttest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shifttest.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject

var users: ArrayList<Users> = ArrayList()

class MainActivity : AppCompatActivity() {


    private lateinit var prefs: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs =
            getSharedPreferences("app", Context.MODE_PRIVATE)
        val stringUsers = prefs.getString("users", "")
        users = try {
            prefs.getString("users", "")?.let { badStringToUser(it) }!!
        } catch(e: NumberFormatException) {
            runBlocking{async{ load()}.await()}
        }

        binding.list.layoutManager = GridLayoutManager(this@MainActivity, 1, LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = UsersAdapter(users) { item ->
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("user", item)

            startActivity(intent)
        }

        prefs.edit().putString("users", users.toString()).apply()

    }

    private suspend fun load(): ArrayList<Users>{
        val product = ArrayList<Users>()
        withContext(Dispatchers.Default) {
            val server = ServerConnection()
            val jsonObject = JSONObject(server.getJson())
            val itemsArray = jsonObject.getJSONArray("results")
            for (i in 0 until itemsArray.length()) {
                val item = itemsArray.getJSONObject(i)
                val userLocateJson = item.getJSONObject("location")
                val locate = "${userLocateJson.getString("country")} ${userLocateJson.getString("state")} ${userLocateJson.getString("city")} ${userLocateJson.getJSONObject("street").getString("name")} ${userLocateJson.getJSONObject("street").getString("number")}"
                val itemExm = Users(
                    i,
                    "${item.getJSONObject("name").getString("title")} ${item.getJSONObject("name").getString("first")} ${item.getJSONObject("name").getString("last")}",
                    item.getJSONObject("picture").getString("medium"),
                    item.getString("email"),
                    item.getString("phone"),
                    locate,
                    userLocateJson.getJSONObject("coordinates").getDouble("latitude"),
                    userLocateJson.getJSONObject("coordinates").getDouble("longitude")
                )
                product += itemExm
            }
        }

        return product
    }

    private fun badStringToUser(stringUser: String): ArrayList<Users>{
        var ourString = stringUser

        for (i in 0 until 50 ){
        val first = ourString.substringAfter('=')
        val string = first.substringBefore(',')

        val first1 = first.substringAfter('=')
        val string1 = first1.substringBefore(',')

        val first2 = first1.substringAfter('=')
        val string2 = first2.substringBefore(',')

        val first3 = first2.substringAfter('=')
        val string3 = first3.substringBefore(',')

        val first4 = first3.substringAfter('=')
        val string4 = first4.substringBefore(',')

        val first5 = first4.substringAfter('=')
        val string5 = first5.substringBefore(',')

        val first6 = first5.substringAfter('=')
        val string6 = first6.substringBefore(',')

        val first7 = first6.substringAfter('=')
        val string7 = first7.substringBefore(')')

        users += Users(string.toInt(), string1, string2, string3, string4, string5, string6.toDouble(), string7.toDouble())

        ourString = first7.substringAfter("), ")
        }
        return users
    }
}