package com.tech.mcity.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tech.mcity.R
import com.tech.mcity.adapter.CityAdapter
import com.tech.mcity.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

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

        cityAdapter.setOnItemClickListener {

            Log.d("city", it.name!!)

            val intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("city", it)
            startActivity(intent)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager

        val searchMenuItem = menu?.findItem(R.id.action_search)

        val  searchView = searchMenuItem!!.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)




        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        query?.let {

            if(it.isNotEmpty()){

                viewModel.filterCity(it)

            }

        }

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == R.id.refresh){

            viewModel.filterCity(null)

        }

        return super.onOptionsItemSelected(item)
    }
}