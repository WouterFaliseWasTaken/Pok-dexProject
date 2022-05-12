package com.example.pokdexproject.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pokdexproject.R
import com.example.pokdexproject.databinding.ActivityMainBinding
import com.example.pokdexproject.model.PokemonViewModel

class DetailActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_detail
        ).apply {
            lifecycleOwner = this@DetailActivity
            viewModel = ViewModelProvider(this@DetailActivity).get(PokemonViewModel::class.java)
        }


    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu,menu)
        return super.onPrepareOptionsMenu(menu)
    }
}