package com.famtrees.pokemonex

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepo @Inject constructor(
    private val webService: WebService
){
    suspend fun fetchPokemonList(limit: Int, offset: Int) = webService.fetchPokemonList(limit, offset)
    suspend fun fetchPokemonDetail(pokeName: String) = webService.fetchPokemon(pokeName)
}