package com.famtrees.pokemonex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pokemon.view.*

class PokemonAdapter(
    val getPokemon: (Int) -> Pokemon?
): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(){

    class ViewHolder(var pokemon: View) : RecyclerView.ViewHolder(pokemon) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pokemon = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon, parent, false)
        return ViewHolder(pokemon)
    }

    private fun isSame(holder: ViewHolder, pokemon: Pokemon?) = holder.pokemon.poke_name.text == pokemon?.nameLink?.name

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pokemon.poke_index.text = (position + 1).toString()

        val pokemon = getPokemon(position)
        if(pokemon?.details == null){
            holder.pokemon.poke_name.text = "Capturing Pokemon!"
            val imgView = holder.pokemon.poke_img
            Glide.with(imgView.context)
                .load("https://wiki.p-insurgence.com/images/0/09/722.png")
                .into(imgView)
        } else if(!isSame(holder, pokemon)){
            holder.pokemon.poke_name.text = pokemon.nameLink.name
            val imgView = holder.pokemon.poke_img
            Glide.with(imgView.context)
                .load(pokemon.details?.sprites?.front_default)
                .into(imgView)
        }
    }

    var size: Int = 0
    override fun getItemCount() = size

}
