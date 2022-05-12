package com.example.pokdexproject.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityMainBinding
import com.example.pokdexproject.model.PokemonViewModel
import com.example.pokdexproject.model.TAG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
            viewModel = ViewModelProvider(this@MainActivity).get(PokemonViewModel::class.java)
        }

        val bookmarkButton = findViewById<Button>(R.id.bookmark_button)

        bookmarkButton.setOnClickListener {startActivity(Intent(this, BookmarkActivity::class.java))}

        val teamButton = findViewById<Button>(R.id.team_button)

        teamButton.setOnClickListener { startActivity(Intent(this, OnTeamActivity::class.java)) }


        setSupportActionBar(findViewById(R.id.main_toolbar))
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)

        val adapter = PokemonListAdapter(PokemonListAdapter.OnClickListener{data ->
            Log.d(TAG,"Clicked Pokémon number in Main:"+data.id.toString())
            //todo: Add intent to go to detail activity
        })
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
        R.id.action_filter ->{
            //todo: Implement filter function
            true
        }
        else -> super.onOptionsItemSelected(item)}

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu,menu)
        return super.onPrepareOptionsMenu(menu)
    }
}