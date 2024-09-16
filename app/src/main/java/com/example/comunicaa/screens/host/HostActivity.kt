package com.example.comunicaa.screens.host

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.comunicaa.R
import com.example.comunicaa.databinding.ActivityHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private val viewModel: HostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host)
        binding = ActivityHostBinding.bind(findViewById(R.id.navHostContainer))

        initView()
        initObservers()
    }

    private fun initView() {}

    private fun initObservers() {}
}