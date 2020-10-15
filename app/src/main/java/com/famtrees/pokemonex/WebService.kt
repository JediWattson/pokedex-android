package com.famtrees.pokemonex

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("api/v2/pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokeRes

    @GET("api/v2/pokemon/{name}")
    suspend fun fetchPokemon(@Path("name") name: String): Details

}
