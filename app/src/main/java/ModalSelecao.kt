package br.edu.controlefinanceiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import br.edu.controlefinanceiro.R


class ModalSelecao : BottomSheetDialogFragment() {

    // Esta variável vai guardar a função que a MainActivity vai nos enviar
    var aoSelecionar: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_modal_tipos, container, false)

        configurarClique(view, R.id.txtSalario)
        configurarClique(view, R.id.txtAlimentacao)
        configurarClique(view, R.id.txtTransporte)
        configurarClique(view, R.id.txtLazer)
        configurarClique(view, R.id.txtSaude)
        configurarClique(view, R.id.txtPet)
        configurarClique(view, R.id.txtLuz)
        configurarClique(view, R.id.txtAgua)
        configurarClique(view, R.id.txtTelefonia)
        configurarClique(view, R.id.txtMoradia)

        return view
    }

    private fun configurarClique(view: View, id: Int) {
        val textView = view.findViewById<TextView>(id)
        textView?.setOnClickListener {
            val textoSelecionado = textView.text.toString()

            // Avisa a MainActivity qual foi o texto clicado
            aoSelecionar?.invoke(textoSelecionado)

            dismiss() // Fecha o modal
        }
    }
}