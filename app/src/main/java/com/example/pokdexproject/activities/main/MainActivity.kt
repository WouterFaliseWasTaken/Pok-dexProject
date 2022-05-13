package com.example.pokdexproject.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
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
        initializeListeners()
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

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.main_toolbar))
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
    }

    private fun initializeListeners() {
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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_sort -> {
            //todo: Implement sort function
            true
        }
        R.id.action_filter -> {
            //todo: Implement filter function
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }
}



