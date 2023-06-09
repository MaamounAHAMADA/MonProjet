package com.example.projet_esimed_mobile



data class GetResult(
    val id: String,
    val nom: String,
    val pseudo: String,
    val email : String,
    val motDePasse : String,
    val prenom : String,
    val isAdmin : Boolean
){}

data class GetResultGroup (
    val id: String,
    val nomGroupe: String,
        ){}
