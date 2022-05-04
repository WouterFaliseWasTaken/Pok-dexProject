package com.example.pokdexproject.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.network.PokemonData
import com.example.pokdexproject.R

const val TAG = "POKEMONDEBUG"

class ItemAdapter (private val context: Context,private val dataset:List<PokemonData>):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val imageView:ImageView = view.findViewById(R.id.item_image_1)
        val indexView:TextView = view.findViewById(R.id.item_index)
        val nameView:TextView = view.findViewById(R.id.item_name_1)
        val type1View:TextView = view.findViewById(R.id.item_type1)
        val type2View:TextView = view.findViewById(R.id.item_type2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset
        /*
        holder.imageView.setImageResource(R.drawable.ic_launcher_background)//replace this later
        holder.indexView.text = item[position].id.toString()
        holder.nameView.text = item[position].name
        holder.type1View.text = item[position].types[0].type.name
        holder.type2View.text = item[position].types[1].type.name
        */
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}