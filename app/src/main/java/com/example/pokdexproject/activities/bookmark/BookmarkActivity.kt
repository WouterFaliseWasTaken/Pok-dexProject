package com.example.pokdexproject.activities.bookmark

import android.content.Intent
import android.os.Bundle
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
                BookmarkViewModel.factory(application)
            ).get(BookmarkViewModel::class.java)
        }

        setupAppbar()

        val adapter = pokemonListAdapter()
        with(binding.recyclerViewBookmark) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        setUpObservers(binding, adapter)
    }

    private fun setUpObservers(
        binding: ActivityBookmarkBinding,
        adapter: PokemonListAdapter
    ) {
        binding.viewModel?.pokemon?.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun pokemonListAdapter() =
        PokemonListAdapter(PokemonListAdapter.OnClickListener { listItem ->
            val intent = Intent(
                this,
                DetailActivity::class.java
            )
            intent.putExtra("id", listItem.id)
            startActivity(intent)
        })

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.bookmark_toolbar))
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }
}