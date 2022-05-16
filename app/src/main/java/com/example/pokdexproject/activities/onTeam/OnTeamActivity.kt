package com.example.pokdexproject.activities.onTeam

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.activities.main.*
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.databinding.ActivityTeamBinding

class OnTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityTeamBinding>(
            this,
            R.layout.activity_team
        ).apply {
            lifecycleOwner = this@OnTeamActivity
            viewModel = ViewModelProvider(
                this@OnTeamActivity,
                MainViewModel.MainViewModelFactory(application)
            ).get(MainViewModel::class.java)
        }

        var filter = FilterByTag.ONTEAM
        var sortByAscending : Pair<Criterion, Boolean> = Pair(Criterion.ID, true)

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
}