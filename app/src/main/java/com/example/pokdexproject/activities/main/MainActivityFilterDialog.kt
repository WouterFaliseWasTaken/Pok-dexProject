package com.example.pokdexproject.activities.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import com.example.pokdexproject.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivityFilterDialog(context: Context, val viewModel: MainViewModel) : Dialog(context) {
    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_main_filter)
        setUpListeners()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //todo: Make window move to the bottom of the screen
    }

    private fun checkAll() {
        for (pair in viewModel.queryParameters.value!!.typeIncluded) {
            if (pair.key != "") checkChip(pair.key)
        }
    }

    private fun checkChip(name: String) {
        findViewById<ChipGroup>(R.id.filter_option_group).run {
            check(
                when (name) {
                    "Normal" -> R.id.type_normal_chip
                    "Fire" -> R.id.type_fire_chip
                    "Fighting" -> R.id.type_fighting_chip
                    "Water" -> R.id.type_water_chip
                    "Flying" -> R.id.type_flying_chip
                    "Grass" -> R.id.type_grass_chip
                    "Poison" -> R.id.type_poison_chip
                    "Electric" -> R.id.type_electric_chip
                    "Ground" -> R.id.type_ground_chip
                    "Psychic" -> R.id.type_psychic_chip
                    "Rock" -> R.id.type_rock_chip
                    "Ice" -> R.id.type_ice_chip
                    "Bug" -> R.id.type_bug_chip
                    "Dragon" -> R.id.type_dragon_chip
                    "Ghost" -> R.id.type_ghost_chip
                    "Dark" -> R.id.type_dark_chip
                    "Steel" -> R.id.type_steel_chip
                    "Fairy" -> R.id.type_fairy_chip
                    else -> Log.e("PKD.MAFD", "Trying to check non-existent chip: $name")
                }
            )
        }

    }

    private fun setUpListeners() {
        findViewById<Button>(R.id.exit_button_filter).setOnClickListener {
            dismiss()
        }
        findViewById<Button>(R.id.filter_dialog_unselect_all_button).setOnClickListener {
            findViewById<ChipGroup>(R.id.filter_option_group).clearCheck()
        }
        findViewById<Button>(R.id.filter_dialog_select_all_button).setOnClickListener {
            checkAll()
        }
        findViewById<Button>(R.id.filter_dialog_apply_button).setOnClickListener {
            viewModel.unselectAllTypes()
            findViewById<ChipGroup>(R.id.filter_option_group).checkedChipIds.forEach {
                viewModel.selectType(
                    findViewById<Chip>(it).text.toString()
                )
            }
            dismiss()
        }
    }
}