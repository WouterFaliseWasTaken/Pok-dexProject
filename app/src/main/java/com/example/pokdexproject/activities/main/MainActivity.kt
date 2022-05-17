package com.example.pokdexproject.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.activities.bookmark.BookmarkActivity
import com.example.pokdexproject.activities.onTeam.OnTeamActivity
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
            viewModel = ViewModelProvider(
                this@MainActivity,
                MainViewModel.MainViewModelFactory(application)
            ).get(MainViewModel::class.java)
        }
        setupAppbar()
        initializeListeners(binding)
        val adapter = PokemonListAdapter(PokemonListAdapter.OnClickListener { listItem ->
            //todo: Add intent to go to detail activity
        })
        with(binding.recyclerView) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.pokemon?.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun initializeListeners(binding: ActivityMainBinding) {
        findViewById<SearchView>(R.id.pokemon_search).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrBlank()) binding.viewModel?.setSearch("")
                else binding.viewModel?.setSearch(query)
                return true
            }
        })
        findViewById<Button>(R.id.bookmark_button).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    BookmarkActivity::class.java
                )
            )
        }
        findViewById<Button>(R.id.team_button).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    OnTeamActivity::class.java
                )
            )
        }
    }

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.main_toolbar))
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_sort -> {
            MainActivitySortDialog(this, binding.viewModel!!).show()

            true
        }
        R.id.action_filter -> {

            //todo
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onPrepareOptionsMenu(menu)
    }
}

