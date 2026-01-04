package br.edu.controlefinanceiro

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_de_lancamentos")
data class Lancamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Chave primária que se auto-incrementa

    val tipo: String,
    val valor: Double,
    val data: String
)
