package br.edu.controlefinanceiro

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LancamentoDao {

    @Insert
    suspend fun inserir(lancamento: Lancamento)

    @Query("SELECT * FROM tabela_de_lancamentos ORDER BY id DESC")
    fun buscarTodos(): Flow<List<Lancamento>>

    @Query("SELECT SUM(valor) FROM tabela_de_lancamentos WHERE tipo = 'Salário'")
    fun calcularEntradas(): Flow<Double?>

    @Query("SELECT SUM(valor) FROM tabela_de_lancamentos WHERE tipo != 'Salário'")
    fun calcularSaidas(): Flow<Double?>
}
