package com.example.pokdexproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.Adapter.ItemAdapter
import com.example.pokdexproject.model.PokémonViewModel

class OnTeamActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val viewModel = PokémonViewModel()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_team)
        //TODO: replace with real data source!
        recyclerView.adapter = ItemAdapter(this, viewModel.pokémon.value!!)
        recyclerView.setHasFixedSize(true)
        //Because of constraints, the data fits on a single screen comfortably. Do we need a recyclerview here or are there better options?
    }
}