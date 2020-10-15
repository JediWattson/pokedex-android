package com.famtrees.pokemonex

data class PokeRes(
    private val next: String,
    private val previous: String,
    private val results: List<NameLink>
) {
    fun getNameLinks(): List<NameLink> {
        return results
    }
}

data class NameLink (
    val name: String,
    private val url: String
){

    fun getURL(): String{
        return url
    }
}

data class Pokemon (
    val id: Int,
    val name: String,
    val sprites: Sprites

){}

data class Sprites (
  val front_default: String
){}