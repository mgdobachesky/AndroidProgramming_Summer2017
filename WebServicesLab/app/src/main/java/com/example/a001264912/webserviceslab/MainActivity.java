package com.example.a001264912.webserviceslab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String SOAP_ACTION = "http://services.aonaware.com/webservices/Define";
    private static final String METHOD_NAME = "Define";
    private static final String NAMESPACE = "http://services.aonaware.com/webservices/";
    private static final String URL = "http://services.aonaware.com/DictService/DictService.asmx";

    TextView txtWord;
    Button btnDefine;
    TextView lblResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtWord = (TextView)findViewById(R.id.txtWord);
        btnDefine = (Button)findViewById(R.id.btnDefine);
        lblResult = (TextView)findViewById(R.id.lblResults);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WebOperation  extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String resultOfCall = "";
            try {
                String Word;
                Word = params[0];

                resultOfCall = SendDefinitionCall(Word);
            } catch (IOException e) {
                Log.e("Allied Error", "Foo didn't work: " + e.getMessage());
                Log.e("Allied Error2", "Full stack track:" + Log.getStackTraceString(e));
            } catch (XmlPullParserException e) {
                Log.e("Allied Error", "Foo didn't work: " + e.getMessage());
                Log.e("Allied Error2", "Full stack track:" + Log.getStackTraceString(e));
            }

            return resultOfCall;
        }
        protected void onPostExecute(String ResultOfCall) {
            lblResult.setText(ResultOfCall);
        }
    }

    public void GetDefinition(View view){

        // Use AsyncTask execute Method To Prevent ANR Problem
        String requestedWord;

        requestedWord = txtWord.getText().toString();

        new WebOperation().execute(requestedWord);

    }

    private String SendDefinitionCall(String Word) throws IOException, XmlPullParserException {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo WordProp = new PropertyInfo();
        WordProp.type = PropertyInfo.STRING_CLASS;
        WordProp.name = "word";
        WordProp.setNamespace(NAMESPACE);
        WordProp.setValue(Word);
        request.addProperty(WordProp);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht;
        System.out.println(envelope.bodyOut.toString());
        ht = new HttpTransportSE(URL);

        ht.debug = true;
        ht.call(SOAP_ACTION, envelope);

        String returnWord;
        SoapObject definitions;
        StringBuffer str = new StringBuffer();

        //final  SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        SoapObject response = (SoapObject)envelope.getResponse();

        returnWord = response.getPrimitivePropertyAsString("Word");
        definitions = (SoapObject)response.getProperty("Definitions");
        int definitionsCount = definitions.getPropertyCount();

        str.append("Requested Word = ");
        str.append(returnWord);
        String definition;
        for(int i = 0; i < definitionsCount; i++) {
            SoapObject wordDefinition = (SoapObject)definitions.getProperty(i);
            definition = wordDefinition.getPropertyAsString("WordDefinition");
            str.append(definition);
        }

        return str.toString();
    }
}
