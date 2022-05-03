package com.example.pokdexproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.R
import com.example.pokdexproject.Adapter.ItemAdapter
import com.example.pokdexproject.data.TestDataSource

    class BookmarkActivity :AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bookmark)
            val myDataSet = TestDataSource().getListOfPokémon().filter{it.bookmarked}
            //TODO: replace with real data source!
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_bookmark)
            recyclerView.adapter = ItemAdapter(this, myDataSet)
            recyclerView.setHasFixedSize(true)

        }
    }