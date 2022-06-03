package com.example.pokdexproject.activities.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.R
import com.example.pokdexproject.adapter.PokemonEvolutionListAdapter
import com.example.pokdexproject.adapter.TypeListAdapter
import com.example.pokdexproject.databinding.ActivityDetailBinding
import com.example.pokdexproject.model.Type
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class DetailActivity : AppCompatActivity() {

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    private lateinit var localViewModel: DetailViewModel

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
        val adapter =
            PokemonEvolutionListAdapter(PokemonEvolutionListAdapter.OnClickListener { listItem ->
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
        binding.viewModel?.run {
            evolutionChain.observe(this@DetailActivity) {
                adapter.submitList(it)
            }
            basics.observe(this@DetailActivity) {
                setupListeners()
                setBackgroundGradient(this.basics.value?.type1)
            }
            typeDamageList.observe(this@DetailActivity) {
                setUpTypeList(binding.noDamageList, typeDamageRelations[0])//no damage
                setUpTypeList(binding.quarterDamageList, typeDamageRelations[1])//quarter damage
                setUpTypeList(binding.halfDamageList, typeDamageRelations[2])//half damage
                setUpTypeList(binding.fullDamageList, typeDamageRelations[3])//full damage
                setUpTypeList(binding.doubleDamageList, typeDamageRelations[4])//double damage
                setUpTypeList(binding.quadrupleDamageList, typeDamageRelations[5])//quadruple damage
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
            for(i:Int in types.indices){
                val chip = Chip(this).apply{
                    this.isCheckable = false
                    this.text = (types[i].name[0].uppercase() + types[i].name.drop(1).lowercase())
                    this.setTextColor(resources.getColor(R.color.white))
                    this.chipBackgroundColor = ColorStateList(
                        arrayOf(
                            intArrayOf(android.R.attr.state_checked),
                            intArrayOf(-android.R.attr.state_checked)
                        ),
                        intArrayOf(types[i].color,types[i].color)
                    )
                    //there are better ways of doing that, but that is SDK 23, and this is SDK 21.
                }
                list.addView(chip)
            }
        }
    }

    private fun getSpanCount(width: Int): Int {
        val marginWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,(3.0f+10.0f+10.0f),resources.displayMetrics).toInt()
        return width / (getTextWidth() + marginWidth)
    }

    private fun getTextWidth(): Int {
        return 150//this is a complete guess
        //todo: Calculate this properly.
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
            title = resources.getString(R.string.home)
            setDisplayShowTitleEnabled(true)
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