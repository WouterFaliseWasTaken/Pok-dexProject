package com.example.pokdexproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.R
import com.example.pokdexproject.Adapter.ItemAdapter
import com.example.pokdexproject.data.TestDataSource
import com.example.pokdexproject.model.Pokémon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDataSet = TestDataSource().getListOfPokémon()
        //TODO: replace with real data source!
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val bookmarkButton = findViewById<Button>(R.id.bookmark_button)

        bookmarkButton.setOnClickListener {startActivity(Intent(this, bookmarkActivity::class.java))}

        val teamButton = findViewById<Button>(R.id.team_Button)

        teamButton.setOnClickListener {startActivity(Intent(this, onTeamActivity::class.java))}

        recyclerView.adapter = ItemAdapter(this, myDataSet)
        recyclerView.setHasFixedSize(true)
    }
}