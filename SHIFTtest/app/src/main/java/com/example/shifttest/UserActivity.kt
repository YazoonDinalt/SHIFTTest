package com.example.shifttest

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shifttest.databinding.ActivityUserBinding
import com.squareup.picasso.Picasso

class UserActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = users[intent.extras?.getInt("user")!!]

        binding.name.text = user.name
        binding.email.text = user.email
        binding.locate.text = user.locate
        binding.number.text = user.number
        Picasso.get().load(user.picture.replace("/med", "")).into(binding.picture)
    }

}