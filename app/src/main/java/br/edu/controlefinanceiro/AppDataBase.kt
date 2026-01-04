package br.edu.controlefinanceiro

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lancamento::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lancamentoDao(): LancamentoDao

    companion object {
        // A anotação @Volatile garante que a instância seja sempre a mais atual
        // para todas as threads do app.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna a instância se ela já existir.
            // Se não, cria o banco de dados de forma segura (synchronized).
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "controle_financeiro_db" // Nome do arquivo do banco de dados
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
