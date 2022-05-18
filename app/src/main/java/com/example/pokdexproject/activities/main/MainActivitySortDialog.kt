package com.example.pokdexproject.activities.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.example.pokdexproject.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivitySortDialog(context: Context, val viewModel: MainViewModel) : Dialog(context) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_main_sort)
        setUpListeners()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //todo: Make window move to the bottom of the screen
    }

    private fun setUpListeners() {
        findViewById<Chip>(R.id.sort_alphabetical_ascending_chip).setOnClickListener {
            unCheckmarkAll()
            findViewById<ImageView>(R.id.sort_by_name_ascending_check).visibility = View.VISIBLE
        }
        findViewById<Chip>(R.id.sort_alphabetical_descending_chip).setOnClickListener {
            unCheckmarkAll()
            findViewById<ImageView>(R.id.sort_by_name_desending_check).visibility = View.VISIBLE
        }
        findViewById<Chip>(R.id.sort_numerical_ascending_chip).setOnClickListener {
            unCheckmarkAll()
            findViewById<ImageView>(R.id.sort_by_id_ascending_check).visibility = View.VISIBLE
        }
        findViewById<Chip>(R.id.sort_numerical_descending_chip).setOnClickListener {
            unCheckmarkAll()
            findViewById<ImageView>(R.id.sort_by_id_descending_check).visibility = View.VISIBLE
        }
        findViewById<Button>(R.id.sort_dialog_apply_button).setOnClickListener {
            when (findViewById<ChipGroup>(R.id.sort_option_group).checkedChipId){

            R.id.sort_numerical_ascending_chip -> viewModel.setSortBy((Pair(Criterion.ID,true)))
            R.id.sort_numerical_descending_chip -> viewModel.setSortBy((Pair(Criterion.ID,false)))
            R.id.sort_alphabetical_ascending_chip -> viewModel.setSortBy((Pair(Criterion.NAME,true)))
            R.id.sort_alphabetical_descending_chip -> viewModel.setSortBy((Pair(Criterion.NAME,false)))
            }

            dismiss()
        }
        findViewById<Button>(R.id.exit_button_sort).setOnClickListener {
            dismiss()
        }
    }

    private fun unCheckmarkAll() {
        findViewById<ImageView>(R.id.sort_by_id_descending_check).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.sort_by_id_ascending_check).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.sort_by_name_desending_check).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.sort_by_name_ascending_check).visibility = View.INVISIBLE
    }

}