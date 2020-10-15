package com.famtrees.pokemonex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pokemon.view.*

class PokemonAdapter(
    private val model: PokemonViewModel
): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(){

    var pokemonDetails: MutableMap<Int, Pokemon> = mutableMapOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(var pokemon: View) : RecyclerView.ViewHolder(pokemon) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pokemon = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon, parent, false)
        return ViewHolder(pokemon)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pokemon.poke_index.text = (position + 1).toString()

        if(position == pokemonDetails.size - 1){
            model.loadPokeAPI()
        }

        val pokemon = pokemonDetails[position]
        if(pokemon?.details != null){
            holder.pokemon.poke_name.text = pokemon.nameLink.name
            val imgView = holder.pokemon.poke_img
            Glide.with(imgView.context)
                .load(pokemon.details?.sprites?.front_default)
                .into(imgView)
        } else {
            model.loadPokemon(position)
        }

    }

    override fun getItemCount() = pokemonDetails.size

}