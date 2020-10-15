package com.famtrees.pokemonex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pokemon.view.*

class PokemonAdapter(
    private val model: PokemonViewModel
): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(){

    var pokemonListSize: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(var pokemon: View) : RecyclerView.ViewHolder(pokemon) {
        init {
            val pokemonName = pokemon.findViewById<TextView>(R.id.pokemonName)
            val pokemonImg = pokemon.findViewById<ImageView>(R.id.pokemonImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pokemon = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon, parent, false)
        return ViewHolder(pokemon)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        model.loadPokemon(position){pokemon ->
            holder.pokemon.pokemonName.setText(pokemon.name)
            val imgView = holder.pokemon.pokemonImg
            Glide.with(imgView.context)
                .load(pokemon.sprites.front_default)
                .into(imgView)

        }

    }

    override fun getItemCount() = pokemonListSize

}