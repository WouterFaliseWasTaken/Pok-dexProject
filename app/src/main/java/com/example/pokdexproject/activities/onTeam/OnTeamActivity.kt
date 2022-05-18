package com.example.pokdexproject.activities.onTeam

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
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
                OnTeamViewModel.OnTeamViewModelFactory(application)
            ).get(OnTeamViewModel::class.java)
        }

        setupAppbar()

        val adapter = PokemonListAdapter(PokemonListAdapter.OnClickListener { data ->
            Log.d(TAG, "Clicked Pokémon number in OnTeam:" + data.id.toString())
            //todo: Add intent to go to detail activity
        })
        with(binding.recyclerViewTeam) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.pokemon?.observe(this) {
            adapter.submitList(it)
        }

    }

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.team_toolbar))
        supportActionBar?.run{
            setDisplayHomeAsUpEnabled(true)
            title="Terug"
        }
    }
}