package com.famtrees.pokemonex

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

class PokemonViewModel @ViewModelInject constructor(
    private val webService: WebService
): ViewModel() {

    private var offset = 0
    private val limit = 25

    private val pokeList: MutableLiveData<List<NameLink>> by lazy {
        MutableLiveData<List<NameLink>>().also {
            loadPokeAPI()
        }
    }

    private val pokemonList: MutableMap<Int, Pokemon> = mutableMapOf()

    private fun loadPokeAPI(){
        webService.fetchPokemonList(limit, offset).enqueue(
            object : Callback<PokeRes> {
                override fun onResponse(call: Call<PokeRes>, response: Response<PokeRes>){
                    val body = response.body()
                    pokeList.postValue(body?.getNameLinks())
                    offset += limit
                }

                override fun onFailure(call: Call<PokeRes>, t: Throwable) {}
            }
        )
    }

    fun getPokemon(): LiveData<List<NameLink>> {
        return pokeList
    }

    fun loadPokemon(position: Int, cb: (Pokemon)-> Unit){
        val pokemon = pokemonList.get(position)
        if(pokemon == null){
            val pokeNmae = pokeList.value?.get(position)?.name
            if (pokeNmae != null) {
                webService.fetchPokemon(pokeNmae).enqueue(object: Callback<Pokemon> {
                    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                        val body = response.body()
                        if (body != null) {
                            pokemonList.put(position, body)
                            cb(body)
                        }
                    }
                    override fun onFailure(call: Call<Pokemon>, t: Throwable) {}

                })
            }
        } else {
            cb(pokemon)
        }
    }
}
