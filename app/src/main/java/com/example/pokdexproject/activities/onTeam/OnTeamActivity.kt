package com.example.pokdexproject.activities.onTeam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.activities.detail.DetailActivity
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityTeamBinding

class OnTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityTeamBinding>(
            this@OnTeamActivity,
            R.layout.activity_team
        ).apply {
            lifecycleOwner = this@OnTeamActivity
            viewModel = ViewModelProvider(
                this@OnTeamActivity,
                OnTeamViewModel.factory(application)
            ).get(OnTeamViewModel::class.java)
        }

        setupAppbar()

        val adapter = pokemonListAdapter()
        with(binding.recyclerViewTeam) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        setUpObservers(binding, adapter)

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

    private fun setUpObservers(
        binding: ActivityTeamBinding,
        adapter: PokemonListAdapter
    ) {
        binding.viewModel?.pokemon?.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.team_toolbar))
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }
}