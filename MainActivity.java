package com.example.woffs;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextToSpeech tts;
	Intent intent;
	private int check = 1111;
	Button botao;
	private String resultNome;
	private int checkVerificaNome = 2222;
	private String resultVerificaNome;
	private int checkVerificaMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		botao = (Button) findViewById(R.id.button1);

		botao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				tts.speak("Diga seu nome",
						TextToSpeech.QUEUE_FLUSH, null);

				intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
						"Diga seu nome: ");

				startActivityForResult(intent, check);

			}
		});
		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.US);
					tts.speak("Toque em qualquer lugar da tela para iniciar",
							TextToSpeech.QUEUE_ADD, null);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == check && resultCode == RESULT_OK) {
			resultNome = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS).get(0);

			verificaSeEhONome();
		} 
		
		if (requestCode == checkVerificaNome && resultCode == RESULT_OK) {

				resultVerificaNome = data.getStringArrayListExtra(
						RecognizerIntent.EXTRA_RESULTS).get(0);
				Toast.makeText(getBaseContext(), resultVerificaNome,
						Toast.LENGTH_LONG).show();
				int i = 9;
				
				if (resultVerificaNome.equals("sim")) {
					Toast.makeText(getBaseContext(), "cheguei", Toast.LENGTH_LONG).show();

					iniciaMenu();
				}else{
					verificaSeEhONome();
				}


		}

	}

	private void iniciaMenu() {
		tts.speak("ESTAREMOS LHE ENVIANDO AO MENU MULECOTE",
				TextToSpeech.QUEUE_ADD, null);
	
		
	}

	private void verificaSeEhONome() {
		tts.speak("Seu nome é " + resultNome + "? Sim ou não?",
				TextToSpeech.QUEUE_ADD, null);
		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		startActivityForResult(intent, checkVerificaNome);
		// intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		// RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		// intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga seu nome: ");
	}

}
