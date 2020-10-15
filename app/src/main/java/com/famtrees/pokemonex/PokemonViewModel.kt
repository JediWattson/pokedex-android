package com.famtrees.pokemonex

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Singleton

class PokemonViewModel @ViewModelInject constructor(
    private val pokemonRepo: PokemonRepo
): ViewModel() {

    private var limit = 25
    private var offset = 0

    private val pokemonDetails: MutableLiveData<MutableMap<Int, Pokemon>> by lazy {
        MutableLiveData<MutableMap<Int, Pokemon>>().also {
            loadPokeAPI()
        }
    }

    fun loadPokeAPI(){
        viewModelScope.launch {
            val result = try {
                pokemonRepo.fetchPokemonList(limit, offset)
            } catch (e: Exception){
                throw Error(e)
            }

            val names = pokemonDetails.value ?: mutableMapOf()
            val namesLinks = result.results
            for (index in offset until (offset + limit)){
                val pokemon = Pokemon(index, namesLinks[index - offset], null)
                names.put(index, pokemon)
            }
            pokemonDetails.postValue(names)
        }
    }

    fun getPokemon(): LiveData<MutableMap<Int, Pokemon>> {
        return pokemonDetails
    }

    fun loadPokemon(position: Int){
        val pokemon = pokemonDetails.value?.get(position)
        if(pokemon != null){
            viewModelScope.launch {
                val result = try {
                    pokemonRepo.fetchPokemonDetail(pokemon.nameLink.name)
                } catch (e: Exception) {
                    throw Error(e)
                }
                pokemon.details = result
                pokemonDetails.value?.put(position, pokemon)
            }
        }
    }

}
