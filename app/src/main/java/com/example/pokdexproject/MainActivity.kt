package com.example.pokdexproject

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityMainBinding
import com.example.pokdexproject.model.PokémonViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewModel = ViewModelProvider(this@MainActivity).get(PokémonViewModel::class.java)
        }
        /**
        val bookmarkButton = findViewById<Button>(R.id.bookmark_button)

        bookmarkButton.setOnClickListener {startActivity(Intent(this, BookmarkActivity::class.java))}

        val teamButton = findViewById<Button>(R.id.team_Button)

        teamButton.setOnClickListener { startActivity(Intent(this, OnTeamActivity::class.java)) }
        */

        setSupportActionBar(findViewById(R.id.main_toolbar))
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        val adapter = PokemonListAdapter()
        with(binding.recyclerView){
           this.adapter = adapter
           layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.pokemon?.observe(this){
            adapter.submitList(it)
        }


    }
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.action_sort ->{
            //todo: Implement sort function
            true
        }
        R.id.action_filter->{
            //todo: Implement filter function
            true
        }
        else -> super.onOptionsItemSelected(item)}
}