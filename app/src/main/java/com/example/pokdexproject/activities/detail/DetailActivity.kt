package com.example.pokdexproject.activities.detail

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.adapter.PokemonEvolutionListAdapter
import com.example.pokdexproject.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    private lateinit var localViewModel:DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("id", 0)


        val mViewModel: DetailViewModel by viewModels {
            viewModelFactory {
                DetailViewModel(
                    id,
                    application
                )
            }
        }
        localViewModel = mViewModel
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        ).apply {
            lifecycleOwner = this@DetailActivity
            viewModel = mViewModel
        }
        setupAppbar()
        val adapter = PokemonEvolutionListAdapter(PokemonEvolutionListAdapter.OnClickListener {listItem->
            val intent = Intent(
                this,
                DetailActivity::class.java
            )
            intent.putExtra("id", listItem.id)
            startActivity(intent)
        })
        with(binding.evolutionList) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.evolutionChain?.observe(this) {
            adapter.submitList(it)
        }
        binding.viewModel?.basics?.observe(this){
            setupListeners()
        }
    }

    private fun setupListeners() {
        findViewById<Button>(R.id.addToTeamButton).apply{
            text = resources.getString(
                if(localViewModel.basics.value?.isOnTeam == true)R.string.remove_from_team
                else R.string.add_to_team
            )
            setOnClickListener{
                localViewModel.toggleOnTeam()
                toggleText(this)
            }
        }
    }

    private fun toggleText(button: Button) {
        if((localViewModel.basics.value?.isOnTeam == true).xor(localViewModel.onTeamXor)){
            button.text = resources.getString(R.string.remove_from_team)
        }else{
            button.text = resources.getString(R.string.add_to_team)
        }
    }

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.detail_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.run {
            setDisplayHomeAsUpEnabled(true)
            title = "Terug"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_bookmark -> {
            localViewModel.toggleBookmark()
            //todo: update icon
            true
        }
        R.id.action_unbookmark -> {
            localViewModel.toggleBookmark()
            //todo: update icon
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)
        //todo: set options to false/true depending on status
        return super.onPrepareOptionsMenu(menu)
    }
}