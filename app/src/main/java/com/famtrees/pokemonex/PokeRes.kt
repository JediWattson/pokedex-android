package com.famtrees.pokemonex

data class PokeRes(
    val results: List<NameLink>
) {}

data class Pokemon (
    val id: Int,
    val nameLink: NameLink,
    var details: Details?
){}

data class NameLink (
    val name: String,
    val url: String
){}

data class Details(
    val sprites: Sprites?
){}

data class Sprites (
  val front_default: String
){}