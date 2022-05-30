package com.example.pokdexproject.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.R
import com.example.pokdexproject.activities.bookmark.BookmarkActivity
import com.example.pokdexproject.activities.detail.DetailActivity
import com.example.pokdexproject.activities.onTeam.OnTeamActivity
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    private lateinit var binding: ActivityMainBinding

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
            val intent = Intent(
                this,
                DetailActivity::class.java
            )
            intent.putExtra("id", listItem.id)
            intent.putExtra("teamCount",binding.viewModel?.onTeamCount?.value)
            startActivity(intent)
        })
        with(binding.recyclerView) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.pokemon?.observe(this) {
            adapter.submitList(it)
            binding.viewModel?.refreshDetails()
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
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_sort -> {
            MainActivitySortDialog(this, binding.viewModel!!).show()

            true
        }
        R.id.action_filter -> {

            MainActivityFilterDialog(this, binding.viewModel!!).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onPrepareOptionsMenu(menu)
    }
}

