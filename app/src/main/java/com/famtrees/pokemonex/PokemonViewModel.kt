package com.famtrees.pokemonex

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.util.concurrent.ForkJoinPool
import javax.inject.Singleton

class PokemonViewModel @ViewModelInject constructor(
    private val pokemonRepo: PokemonRepo
): ViewModel() {

    private var limit = 25
    private var offset = 0

    private val pokemonDetails: MutableLiveData< MutableMap<Int, Pokemon>> by lazy {
        MutableLiveData< MutableMap<Int, Pokemon>>().also {
            loadPokeAPI()
        }
    }

    private var isLoading = false
    private fun loadPokeAPI(){
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                pokemonRepo.fetchPokemonList(limit, offset)
            } catch (e: Exception){
                throw Error(e)
            }

            val names = pokemonDetails.value ?: mutableMapOf()
            val namesLinks = result.results
            for (index in 0 until limit){
                val pokemon = Pokemon(index, namesLinks[index], null)
                names.set(index + offset, pokemon)
            }

            pokemonDetails.postValue(names)
            offset += limit
            isLoading = false
        }
    }

    private fun loadPokemon(position: Int){
        val pokemon = pokemonDetails.value?.get(position)
        if(pokemon != null){
            viewModelScope.launch {
                val result = try {
                    pokemonRepo.fetchPokemonDetail(pokemon.nameLink.name)
                } catch (e: Exception) {
                    throw Error(e)
                }
                pokemon.details = result
                val liveList = pokemonDetails.value
                liveList?.set(position, pokemon)
                pokemonDetails.postValue(liveList)
            }
        }
    }

    fun getPokemon(): LiveData<MutableMap<Int, Pokemon>> {
        return pokemonDetails
    }

    fun getPokemonDetail(position: Int): Pokemon?{
        if(!isLoading && position == offset - 1)
            loadPokeAPI()

        val pokemon = pokemonDetails.value?.get(position)
        if(pokemon?.details != null){
            return pokemon
        } else {
            loadPokemon(position)
        }

        return null
    }


}
