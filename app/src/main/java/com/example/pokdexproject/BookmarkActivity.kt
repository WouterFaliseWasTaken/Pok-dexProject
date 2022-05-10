package com.example.pokdexproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.model.PokémonViewModel

class BookmarkActivity :AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bookmark)
            val viewModel = PokémonViewModel()

            //TODO: replace with real data source!
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_bookmark)
            recyclerView.adapter = PokemonListAdapter()
            recyclerView.setHasFixedSize(true)

        }
    }