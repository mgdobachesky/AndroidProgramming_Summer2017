package webservices.examples.neit.soapwebservice;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    Button GetWeather;
    EditText City;
    EditText Country;
    TextView result;

    private static final String SOAP_ACTION = "http://www.webserviceX.NET/GetWeather";
    private static final String METHOD_NAME = "GetWeather";
    private static final String NAMESPACE = "http://www.webserviceX.NET";
    private static final String URL = "http://www.webservicex.net/globalweather.asmx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City = (EditText) findViewById(R.id.txtCity);
        Country = (EditText) findViewById(R.id.txtCountry);
        result = (TextView) findViewById(R.id.result);
        GetWeather = (Button) findViewById(R.id.GetWeather);
        GetWeather.setOnClickListener(GetWeatherClickListener);
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
            String resultOfCall = "test";
            try {

                String requestedCity;
                String requestedCountry;

                requestedCity = params[0];
                requestedCountry=  params[1];
                // This won't work because you need to pull components from the main thread
                //resultOfCall = GetWeatherInfo(City.getText().toString(), Country.getText().toString());
                resultOfCall = GetWeatherInfo(requestedCity, requestedCountry);
            } catch (IOException e) {
                Log.e("Allied Error", "Foo didn't work: " + e.getMessage());
                Log.e("Allied Error2", "Full stack track:" + Log.getStackTraceString(e));
            } catch (XmlPullParserException e) {
                Log.e("Allied Error", "Foo didn't work: " + e.getMessage());
                Log.e("Allied Error2", "Full stack track:" + Log.getStackTraceString(e));
            }
        //    result.setText(resultOfCall);

            return resultOfCall;
        }
        protected void onPostExecute(String ResultOfCall) {
            result.setText(ResultOfCall);
        }
    }

    private View.OnClickListener GetWeatherClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            // Use AsyncTask execute Method To Prevent ANR Problem
            String requestedCity;
            String requestedCountry;
            requestedCity = City.getText().toString();
            requestedCountry = Country.getText().toString();
            new WebOperation().execute(requestedCity, requestedCountry);

        }
    };

    private String GetWeatherInfo(String City, String Country) throws IOException, XmlPullParserException{


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);




        PropertyInfo CityProp = new PropertyInfo();
        CityProp.type = PropertyInfo.STRING_CLASS;
        CityProp.name = "CityName";
        CityProp.setNamespace(NAMESPACE);
        CityProp.setValue(City);
        request.addProperty(CityProp);

        PropertyInfo CountryProp = new PropertyInfo();
        CountryProp.type = PropertyInfo.STRING_CLASS;
        CountryProp.name = "CountryName";
        CountryProp.setNamespace(NAMESPACE);
        CountryProp.setValue(Country);
        request.addProperty(CountryProp);

        // this works too
//		request.addProperty("CityName",City);
//		request.addProperty("CountryName", Country);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht;
        System.out.println(envelope.bodyOut.toString());
        ht = new HttpTransportSE(URL);

        ht.debug = true;
        ht.call(SOAP_ACTION, envelope);

        //SoapObject response = (SoapObject)envelope.getResponse();
        final  SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        String str = response.toString();

        return str;
    }
}
