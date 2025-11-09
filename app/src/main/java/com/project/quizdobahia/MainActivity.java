package com.project.quizdobahia;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.project.quizdobahia.PerguntasRespostasQuiz;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textoTotalQuestoes;
    TextView textoPergunta;
    Button respostaA, respostaB, respostaC, respostaD;
    Button botaoResponder;

    int pontuacao = 0;
    int totalQuestoes = PerguntasRespostasQuiz.perguntas.length;
    int indicePerguntaAtual = 0;
    String respostaSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoTotalQuestoes = findViewById(R.id.total_question);
        textoPergunta = findViewById(R.id.question);
        respostaA = findViewById(R.id.ans_A);
        respostaB = findViewById(R.id.ans_B);
        respostaC = findViewById(R.id.ans_C);
        respostaD = findViewById(R.id.ans_D);
        botaoResponder = findViewById(R.id.submit_btn);

        respostaA.setOnClickListener(this);
        respostaB.setOnClickListener(this);
        respostaC.setOnClickListener(this);
        respostaD.setOnClickListener(this);
        botaoResponder.setOnClickListener(this);

        textoTotalQuestoes.setText("Total de questões: " + totalQuestoes);

        carregarNovaPergunta();
    }

    @Override
    public void onClick(View view) {
        respostaA.setBackgroundColor(Color.WHITE);
        respostaB.setBackgroundColor(Color.WHITE);
        respostaC.setBackgroundColor(Color.WHITE);
        respostaD.setBackgroundColor(Color.WHITE);

        Button botaoClicado = (Button) view;

        if (botaoClicado.getId() == R.id.submit_btn) {

            if (respostaSelecionada.equals(PerguntasRespostasQuiz.respostasCorretas[indicePerguntaAtual])) {
                pontuacao++;
            }

            indicePerguntaAtual++;
            carregarNovaPergunta();

        } else {
            respostaSelecionada = botaoClicado.getText().toString();
            botaoClicado.setBackgroundColor(Color.BLUE);
        }
    }

    void carregarNovaPergunta() {
        if (indicePerguntaAtual == totalQuestoes) {
            finalizarQuiz();
            return;
        }

        textoPergunta.setText(PerguntasRespostasQuiz.perguntas[indicePerguntaAtual]);
        respostaA.setText(PerguntasRespostasQuiz.alternativas[indicePerguntaAtual][0]);
        respostaB.setText(PerguntasRespostasQuiz.alternativas[indicePerguntaAtual][1]);
        respostaC.setText(PerguntasRespostasQuiz.alternativas[indicePerguntaAtual][2]);
        respostaD.setText(PerguntasRespostasQuiz.alternativas[indicePerguntaAtual][3]);
    }

    void finalizarQuiz() {
        String statusAprovacao = "";
        if (pontuacao > totalQuestoes * 0.60) {
            statusAprovacao = "Aprovado";
        } else {
            statusAprovacao = "Reprovado";
        }

        new AlertDialog.Builder(this)
                .setTitle(statusAprovacao)
                .setMessage("Você acertou " + pontuacao + " de " + totalQuestoes + " perguntas.")
                .setPositiveButton("Reiniciar", (dialogInterface, i) -> reiniciarQuiz())
                .setCancelable(false)
                .show();
    }

    void reiniciarQuiz() {
        pontuacao = 0;
        indicePerguntaAtual = 0;
        carregarNovaPergunta();
    }

}
