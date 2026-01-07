package br.edu.controlefinanceiro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class LancamentoAdapter(private val lancamentos: List<Lancamento>) :
    RecyclerView.Adapter<LancamentoAdapter.LancamentoViewHolder>() {

    // Esta classe interna representa a view de cada item da lista
    class LancamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTipo: TextView = view.findViewById(R.id.txtTipo)
        val txtValor: TextView = view.findViewById(R.id.txtValor)
        val txtData: TextView = view.findViewById(R.id.txtData)
    }

    // Cria uma nova view (invocado pelo layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LancamentoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lancamento, parent, false)
        return LancamentoViewHolder(view)
    }

    // Retorna o tamanho da sua lista de dados
    override fun getItemCount() = lancamentos.size

    // Substitui o conteúdo de uma view (invocado pelo layout manager)
    override fun onBindViewHolder(holder: LancamentoViewHolder, position: Int) {
        val lancamento = lancamentos[position]

        holder.txtTipo.text = lancamento.tipo
        holder.txtData.text = lancamento.data

        // Formata o valor como moeda (ex: R$ 150,75)
        val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        holder.txtValor.text = formatoMoeda.format(lancamento.valor)
    }
}
