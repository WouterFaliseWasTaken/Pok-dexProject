package com.example.pokdexproject.activities.detail

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pokdexproject.R
import com.example.pokdexproject.activities.main.MainViewModel
import com.example.pokdexproject.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_detail
        ).apply {
            lifecycleOwner = this@DetailActivity
            viewModel = ViewModelProvider(
                this@DetailActivity,
                MainViewModel.MainViewModelFactory(application)
            ).get(MainViewModel::class.java)
        }


    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }
}