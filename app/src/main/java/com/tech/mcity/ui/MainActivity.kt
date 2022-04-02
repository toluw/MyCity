package com.tech.mcity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tech.mcity.R
import com.tech.mcity.adapter.CityAdapter
import com.tech.mcity.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        initRvAdapter()


    }

    private fun initRvAdapter() {

        val cityAdapter = CityAdapter()

        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(applicationContext)

            adapter = cityAdapter

        }

        lifecycleScope.launchWhenCreated {
            viewModel.myCity.collectLatest {
                cityAdapter.submitData(it)
            }
        }

    }
}