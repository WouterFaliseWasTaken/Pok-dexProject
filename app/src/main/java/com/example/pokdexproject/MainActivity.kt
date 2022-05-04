package com.example.pokdexproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.Adapter.ItemAdapter
import com.example.pokdexproject.Network.PokeApiService
import com.example.pokdexproject.model.PokémonViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = PokémonViewModel()
        //TODO: replace with real data source!
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val bookmarkButton = findViewById<Button>(R.id.bookmark_button)

        bookmarkButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    BookmarkActivity::class.java
                )
            )
        }

        val teamButton = findViewById<Button>(R.id.team_Button)

        teamButton.setOnClickListener { startActivity(Intent(this, OnTeamActivity::class.java)) }

        recyclerView.adapter = ItemAdapter(this, viewModel.pokémon.value!!)
        recyclerView.setHasFixedSize(true)
    }
}