package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMain2Binding
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller =findNavController(R.id.fragment2ContainerView)
        binding.bottomNavigation.setupWithNavController(controller)
    }
}