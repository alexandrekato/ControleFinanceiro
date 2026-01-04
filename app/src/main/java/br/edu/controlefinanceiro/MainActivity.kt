package br.edu.controlefinanceiro

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Encontre o seu EditText pelo ID
        val editTipoTransacao = findViewById<EditText>(R.id.editTextTipoEntrada)

        // 2. Importante: impede que o teclado abra ao clicar no EditText
        editTipoTransacao.isFocusable = false
        editTipoTransacao.isClickable = true

        // 3. Configura o clique para abrir o Modal
        editTipoTransacao.setOnClickListener {
            val modal = ModalSelecao()

            // Definimos o que acontece quando o usuário escolhe uma opção no modal
            modal.aoSelecionar = { tipoSelecionado ->
                editTipoTransacao.setText(tipoSelecionado) // Coloca o texto no EditText
            }

            // Mostra o modal
            modal.show(supportFragmentManager, "ModalSelecao")
        }
        //Encontra o EditText da data
        val editTextDate = findViewById<EditText>(R.id.editTextDate)

        // Configura o clique para abrir o seletor de data
        editTextDate.setOnClickListener {
            // Pega a instância atual do calendário
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            // Cria o DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, anoSelecionado, mesSelecionado, diaSelecionado ->
                    // O mês retorna de 0-11, então somamos 1
                    val mesCorrigido = mesSelecionado + 1
                    // Formata a data como "DD/MM/AAAA"
                    val dataFormatada = String.format("%02d/%02d/%d", diaSelecionado, mesCorrigido, anoSelecionado)
                    // Coloca a data formatada no EditText
                    editTextDate.setText(dataFormatada)
                },
                ano,
                mes,
                dia
            )

            // Mostra o calendário para o usuário
            datePickerDialog.show()
        }
        val btnLancar = findViewById<Button>(R.id.GravarLancamento)
        val editValor = findViewById<EditText>(R.id.editTextValor)

        // Obtenha uma instância do seu DAO (a porta de entrada para o banco)
        val dao = AppDatabase.getDatabase(this).lancamentoDao()

        // Configura a ação do clique no botão Lançar
        btnLancar.setOnClickListener {
            val tipo = editTipoTransacao.text.toString()
            val valorStr = editValor.text.toString()
            val data = editTextDate.text.toString()

            // Validação simples para garantir que os campos não estão vazios
            if (tipo.isNotEmpty() && valorStr.isNotEmpty() && data.isNotEmpty()) {
                // Converte o valor do texto para um número (Double) de forma segura
                val valor = valorStr.toDoubleOrNull()

                // Se a conversão falhar (ex: usuário digitou "abc"), mostra um erro
                if (valor == null) {
                    Toast.makeText(this, "Por favor, insira um valor numérico válido.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Interrompe a execução aqui
                }

                // Cria o objeto Lancamento com os dados da tela
                val novoLancamento = Lancamento(
                    tipo = tipo,
                    valor = valor,
                    data = data
                )

                // Inicia uma Coroutine para salvar os dados em segundo plano
                lifecycleScope.launch {
                    dao.inserir(novoLancamento)

                    // Após salvar, mostra uma mensagem de sucesso na tela principal
                    Toast.makeText(this@MainActivity, "Lançamento salvo!", Toast.LENGTH_SHORT).show()

                    // Limpa os campos para o próximo lançamento
                    editTipoTransacao.text.clear()
                    editValor.text.clear()
                    editTextDate.text.clear()
                }

            } else {
                // Mostra uma mensagem de erro se algum campo estiver vazio
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
        // --- FIM DO NOVO CÓDIGO ---

    } // Fim da função onCreate
} // Fim da classe MainActivity
