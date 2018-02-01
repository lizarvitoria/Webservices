package com.example.dm2.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private String TAG="Response";
    private Button btCalcular;
    private EditText edGradosCelsius;
    private String celsius;
    private SoapPrimitive resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCalcular = (Button) findViewById(R.id.btCalcular);
        edGradosCelsius = (EditText) findViewById(R.id.etGradosCelsius);
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                celsius = edGradosCelsius.getText().toString();
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        });
    }
    public class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "metodo onPreExecute");
        }
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "metodo doInBackground");
            calcular();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "metodo onPostExecute");
            Toast.makeText(MainActivity.this,
                    "Grados Farengheit "+resultado.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        private void calcular() {
            String SOAP_ACTION =
                    "http://www.w3schools.com/xml/CelsiusToFahrenheit";
            String METHOD_NAME = "CelsiusToFahrenheit";
            String NAMESPACE = "http://www.w3schools.com/xml/";
            String URL = "http://www.w3schools.com/XML/tempconvert.asmx";
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Celsius", celsius);
                SoapSerializationEnvelope soapEnvelope =
                        new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet=true;
                soapEnvelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION,soapEnvelope);
                resultado = (SoapPrimitive) soapEnvelope.getResponse();
                Log.i(TAG,"RESULTADO CELSIUS: "+ resultado.toString());
            }catch (Exception ex){
                Log.e(TAG,"ERROR: "+ex.getMessage());
            }
        }


    }
}

