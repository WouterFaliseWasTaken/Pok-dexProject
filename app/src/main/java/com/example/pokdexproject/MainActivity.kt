package com.example.pokdexproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.adapter.ItemAdapter
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityMainBinding
import com.example.pokdexproject.model.PokémonViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = PokémonViewModel()
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(R.layout.activity_main)


        //TODO: replace with real data source!
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val bookmarkButton = findViewById<Button>(R.id.bookmark_button)

        bookmarkButton.setOnClickListener {startActivity(Intent(this, BookmarkActivity::class.java))}

        val teamButton = findViewById<Button>(R.id.team_Button)

        teamButton.setOnClickListener { startActivity(Intent(this, OnTeamActivity::class.java)) }

        //recyclerView.adapter = ItemAdapter(this, viewModel.pokémon.value!!)
        binding.recyclerView.adapter = PokemonListAdapter()
        recyclerView.setHasFixedSize(true)

    }


}