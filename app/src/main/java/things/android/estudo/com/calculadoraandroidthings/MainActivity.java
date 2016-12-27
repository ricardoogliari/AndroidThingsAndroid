package things.android.estudo.com.calculadoraandroidthings;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private TextView nrUm;
    private TextView nrDois;
    private TextView time;
    private TextView nrOp;
    private EditText edtResposta;

    private int numero1, numero2, resposta;

    private char[] operacoes = new char[]{'+', '-', '*'};
    private int indiceOp = -1;

    private Handler mHandler = new Handler();
    private int tempo = 5;

    private boolean sortear = true;

    private int media = 8;
    private boolean respondeu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nrUm = (TextView) findViewById(R.id.nrUm);
        nrDois = (TextView) findViewById(R.id.nrDois);
        nrOp = (TextView) findViewById(R.id.nrOp);
        time = (TextView) findViewById(R.id.time);
        edtResposta = (EditText) findViewById(R.id.edtResposta);

        mHandler.post(mBlinkRunnable);
    }

    private Runnable mBlinkRunnable = new Runnable() {
        @Override
        public void run() {
            sorteio();
            mHandler.postDelayed(mBlinkRunnable, 1000);
        }
    };

    public void sorteio(){
        if (respondeu){
            int correto = 0;

            switch (operacoes[indiceOp]){
                case '+':
                    correto = numero1 + numero2;
                    break;
                case '-':
                    correto = numero1 - numero2;
                    break;
                case '*':
                    correto = numero1 * numero2;
            }

            String strCorreto = "" + correto;

            if (strCorreto.equals(edtResposta.getText().toString())){
                media++;
            } else {
                media--;
            }

            Core.myRef.child("media").setValue(media);
            sortear = true;
            respondeu = false;

            edtResposta.setText("");

            sorteio();

        } else {
            if (sortear) {
                numero1 = (int) Math.floor((Math.random() * 10) + 1);
                numero2 = (int) Math.floor((Math.random() * 10) + 1);
                resposta = numero1 + numero2;

                indiceOp++;
                if (indiceOp > 2) {
                    indiceOp = 0;
                }

                if (operacoes[indiceOp] == '-' && numero2 > numero1){
                    int aux = numero1;
                    numero1 = numero2;
                    numero2 = aux;
                }

                nrOp.setText("" + operacoes[indiceOp]);

                nrUm.setText("" + numero1);
                nrDois.setText("" + numero2);

                time.setText("05");
                tempo = 5;
                sortear = false;
            } else {
                tempo--;
                time.setText("0" + tempo);

                if (tempo == 0) {
                    media--;
                    Log.e("MEDIA", "Media: " + media);
                    Core.myRef.child("media").setValue(media);
                    sortear = true;
                    sorteio();
                }
            }
        }
    }

    public void responder(View view){
        respondeu = true;
    }
}
