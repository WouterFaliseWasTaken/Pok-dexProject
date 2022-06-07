package com.example.pokdexproject.activities.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdexproject.R
import com.example.pokdexproject.adapter.PokemonEvolutionListAdapter
import com.example.pokdexproject.commonCode.toPx
import com.example.pokdexproject.databinding.ActivityDetailBinding
import com.example.pokdexproject.model.Type
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class DetailActivity : AppCompatActivity() {

    private lateinit var localViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("id", 0)
        localViewModel = ViewModelProvider(
            this@DetailActivity,
            DetailViewModel.factory(id, application)
        ).get(DetailViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        ).apply {
            lifecycleOwner = this@DetailActivity
            viewModel = localViewModel
        }
        setupAppbar()
        binding.setUpObservers(binding.getAdapter())
    }

    private fun ActivityDetailBinding.getAdapter(): PokemonEvolutionListAdapter {
        val adapter =
            PokemonEvolutionListAdapter(PokemonEvolutionListAdapter.OnClickListener { listItem ->
                val intent = Intent(
                    this@DetailActivity,
                    DetailActivity::class.java
                )
                intent.putExtra("id", listItem.id)
                startActivity(intent)
            })
        with(this.evolutionList) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        return adapter
    }

    private fun ActivityDetailBinding.setUpObservers(adapter: PokemonEvolutionListAdapter) {
        viewModel?.run {
            evolutionChain.observe(this@DetailActivity) {
                adapter.submitList(it)
            }
            basics.observe(this@DetailActivity) {
                setupListeners()
                setBackgroundGradient(this.basics.value?.type1)
            }
            typeDamageList.observe(this@DetailActivity) {
                setUpTypeList(noDamageList, typeDamageRelations[0])//no damage
                setUpTypeList(quarterDamageList, typeDamageRelations[1])//quarter damage
                setUpTypeList(halfDamageList, typeDamageRelations[2])//half damage
                setUpTypeList(fullDamageList, typeDamageRelations[3])//full damage
                setUpTypeList(doubleDamageList, typeDamageRelations[4])//double damage
                setUpTypeList(quadrupleDamageList, typeDamageRelations[5])//quadruple damage
            }
        }
    }

    private fun setBackgroundGradient(type: String?) {
        if (type != null) {
            val color = Type.valueOf(type.uppercase()).color
            val hSV = FloatArray(3)//Hue, Saturation, Value
            Color.RGBToHSV(
                (color and 0x00FF0000) shr 16,
                (color and 0x0000FF00) shr 8,
                color and 0x000000FF,
                hSV
            )
            val gradient = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(
                    Color.HSVToColor(floatArrayOf(hSV[0], hSV[1] * 85 / 100, hSV[2])),
                    Color.HSVToColor(floatArrayOf(hSV[0], hSV[1] * 50 / 100, hSV[2]))
                )
            )
            findViewById<ConstraintLayout>(R.id.detail_layout).background = gradient
        }
    }

    private fun setUpTypeList(list: ChipGroup, types: List<Type>) {
        if (types.isNotEmpty()) {
            list.removeAllViews()
            list.setChipSpacing(5.toPx)
            for (i: Int in types.indices) {
                val chip = Chip(this).apply {
                    this.isCheckable = false
                    this.text = (types[i].name[0].uppercase() + types[i].name.drop(1).lowercase())
                    this.setTextColor(resources.getColor(R.color.white))
                    this.chipBackgroundColor = ColorStateList(
                        arrayOf(
                            intArrayOf(android.R.attr.state_checked),
                            intArrayOf(-android.R.attr.state_checked)
                        ),
                        intArrayOf(types[i].color, types[i].color)
                    )
                    //there are better ways of doing that, but that is SDK 23,   and this is SDK 21.
                    this.setPadding(10.toPx, 0.toPx, 10.toPx, 0.toPx)
                    this.setEnsureMinTouchTargetSize(false)
                }
                list.addView(chip)
            }
        }
    }


    private fun setupListeners() {
        findViewById<Button>(R.id.addToTeamButton).apply {
            text = resources.getString(
                if (localViewModel.basics.value?.isOnTeam == true) R.string.remove_from_team
                else R.string.add_to_team
            )
            setOnClickListener {
                if (localViewModel.basics.value?.isOnTeam == true || ((localViewModel.teamCount.value != null) && (localViewModel.teamCount.value!! <= 5))) {
                    localViewModel.toggleOnTeam()
                } else {
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.team_full_text),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                toggleText(this)
            }
        }
    }

    private fun toggleText(button: Button) {
        if ((localViewModel.basics.value?.isOnTeam == true)) {
            button.text = resources.getString(R.string.remove_from_team)
        } else {
            button.text = resources.getString(R.string.add_to_team)
        }
    }

    private fun setupAppbar() {
        setSupportActionBar(findViewById(R.id.detail_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_bookmark -> {
            localViewModel.toggleBookmark()
            if (localViewModel.basics.value?.isBookmarked == true)
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
            else item.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)
        val item = menu!!.findItem(R.id.action_bookmark)
        if (localViewModel.basics.value?.isBookmarked == true)
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        else item.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        return super.onPrepareOptionsMenu(menu)
    }
}