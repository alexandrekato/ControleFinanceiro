package br.edu.controlefinanceiro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.controlefinanceiro.R
import kotlinx.coroutines.launch

class HistoricoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        // --- INÍCIO DO NOVO CÓDIGO DA TOOLBAR ---
        val toolbar = findViewById<Toolbar>(R.id.toolbar_historico)
        // 2. Define a toolbar como a ActionBar oficial da tela
        setSupportActionBar(toolbar)
        // --- FIM DO NOVO CÓDIGO DA TOOLBAR ---
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Histórico"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistorico)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dao = AppDatabase.getDatabase(this).lancamentoDao()

        lifecycleScope.launch {
            dao.buscarTodos().collect { listaDeLancamentos ->
                val adapter = LancamentoAdapter(listaDeLancamentos)
                recyclerView.adapter = adapter
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Finaliza a activity atual e volta para a anterior na pilha
        finish()
        return true
    }
}
