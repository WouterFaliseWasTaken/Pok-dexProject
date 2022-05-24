package com.example.pokdexproject.activities.bookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.activities.detail.DetailActivity
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityBookmarkBinding

class BookmarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityBookmarkBinding>(
            this,
            R.layout.activity_bookmark
        ).apply {
            lifecycleOwner = this@BookmarkActivity
            viewModel = ViewModelProvider(
                this@BookmarkActivity,
                BookmarkViewModel.BookmarkViewModelFactory(application)
            ).get(BookmarkViewModel::class.java)
        }

        setupAppbar()

        val adapter = PokemonListAdapter(PokemonListAdapter.OnClickListener { listItem ->
            val intent = Intent(
                this,
                DetailActivity::class.java
            )
            intent.putExtra("id", listItem.id)
            startActivity(intent)
        })
        with(binding.recyclerViewBookmark) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.pokemon?.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.bookmark_toolbar))
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.back)
        }
    }
}