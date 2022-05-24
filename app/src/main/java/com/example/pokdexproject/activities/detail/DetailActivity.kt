package com.example.pokdexproject.activities.detail

import android.opengl.Visibility
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
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

    private var id: Int? = null

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
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        ).apply {
            lifecycleOwner = this@DetailActivity
            viewModel = mViewModel
        }
        setupAppbar()
        val adapter = PokemonEvolutionListAdapter(PokemonEvolutionListAdapter.OnClickListener { })
        with(binding.evolutionList) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.viewModel?.evolutionChain?.observe(this) {
            adapter.submitList(it)
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
            true
        }
        R.id.action_unbookmark -> {
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