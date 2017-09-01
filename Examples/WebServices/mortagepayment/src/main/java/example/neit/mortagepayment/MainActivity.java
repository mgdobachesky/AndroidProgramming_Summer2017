package example.neit.mortagepayment;

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
import android.widget.TextView;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String SOAP_ACTION = "http://www.webserviceX.NET/GetMortgagePayment";
    private static final String METHOD_NAME = "GetMortgagePayment";
    private static final String NAMESPACE = "http://www.webserviceX.NET/";
    private static final String URL = "http://www.webservicex.net/mortgage.asmx";

    TextView txtYears;
    TextView txtInterest;
    TextView txtLoanAmount;
    TextView txtAnnualTax;
    TextView txtAnnualInsurance;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtYears = (TextView)findViewById(R.id.txtYears);
        txtInterest = (TextView)findViewById(R.id.txtInterest);
        txtLoanAmount = (TextView)findViewById(R.id.txtLoanAmount);
        txtAnnualTax = (TextView)findViewById(R.id.txtAnnualTax);
        txtAnnualInsurance = (TextView)findViewById(R.id.txtAnnualInsurance);
        result = (TextView)findViewById(R.id.response);

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
            String resultOfCall = "test";
            try {

                String Years;
                String Interest;
                String LoanAmount;
                String AnnualTax;
                String AnnualInsurance;

                Years = params[0];
                Interest=  params[1];
                LoanAmount = params[2];
                AnnualTax = params[3];
                AnnualInsurance = params[4];

                // This won't work because you need to pull components from the main thread
                //resultOfCall = GetWeatherInfo(City.getText().toString(), Country.getText().toString());
                resultOfCall = GetMortgagePayment(Years, Interest, LoanAmount, AnnualTax, AnnualInsurance);
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
    public void GetPayment(View view){
        // Use AsyncTask execute Method To Prevent ANR Problem
        String requestedYears;
        String requestedInterest;
        String requestedLoanAmount;
        String requestedAnnualTax;
        String requestedAnnualInsurance;


        requestedYears = txtYears.getText().toString();
        requestedInterest = txtInterest.getText().toString();
        requestedLoanAmount = txtLoanAmount.getText().toString();
        requestedAnnualTax = txtAnnualTax.getText().toString();
        requestedAnnualInsurance = txtAnnualInsurance.getText().toString();

        new WebOperation().execute(requestedYears, requestedInterest, requestedLoanAmount, requestedAnnualTax, requestedAnnualInsurance);

    }

    private String GetMortgagePayment(String Years, String Interest, String LoanAmount,
                                     String AnnualTax, String AnnualInsurance) throws IOException, XmlPullParserException{


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo YearsProp = new PropertyInfo();
        YearsProp.type = PropertyInfo.STRING_CLASS;
        YearsProp.name = "Years";
        YearsProp.setNamespace(NAMESPACE);
        YearsProp.setValue(Years);
        request.addProperty(YearsProp);


        PropertyInfo InterestProp = new PropertyInfo();
        InterestProp.type = PropertyInfo.STRING_CLASS;
        InterestProp.name = "Interest";
        InterestProp.setNamespace(NAMESPACE);
        InterestProp.setValue(Interest);
        request.addProperty(InterestProp);


        PropertyInfo LoanAmountProp = new PropertyInfo();
        LoanAmountProp.type = PropertyInfo.STRING_CLASS;
        LoanAmountProp.name = "LoanAmount";
        LoanAmountProp.setNamespace(NAMESPACE);
        LoanAmountProp.setValue(LoanAmount);
        request.addProperty(LoanAmountProp);


        PropertyInfo AnnualTaxProp = new PropertyInfo();
        AnnualTaxProp.type = PropertyInfo.STRING_CLASS;
        AnnualTaxProp.name = "AnnualTax";
        AnnualTaxProp.setNamespace(NAMESPACE);
        AnnualTaxProp.setValue(AnnualTax);
        request.addProperty(AnnualTaxProp);

        PropertyInfo AnnualInsuranceProp = new PropertyInfo();
        AnnualInsuranceProp.type = PropertyInfo.STRING_CLASS;
        //AnnualInsuranceProp.type = PropertyInfo.INTEGER_CLASS;
        AnnualInsuranceProp.name = "AnnualInsurance";
        AnnualInsuranceProp.setNamespace(NAMESPACE);
        //AnnualInsuranceProp.setValue(AnnualInsurance);
        AnnualInsuranceProp.setValue(Integer.parseInt(AnnualInsurance));
        request.addProperty(AnnualInsuranceProp);


        // this works too
//		request.addProperty("AnnualInsurance",AnnualInsurance);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht;
        System.out.println(envelope.bodyOut.toString());
        ht = new HttpTransportSE(URL);

        ht.debug = true;
        ht.call(SOAP_ACTION, envelope);



        String MonthlyPrincipalAndInterest;
        String MonthlyTax;
        String MonthlyInsurance;
        String TotalPayment;
        //final  SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        SoapObject response = (SoapObject)envelope.getResponse();
        MonthlyPrincipalAndInterest = response.getPrimitivePropertyAsString("MonthlyPrincipalAndInterest");
        MonthlyTax = response.getProperty(1).toString();
        MonthlyInsurance = response.getPrimitivePropertyAsString("MonthlyInsurance");
        TotalPayment = response.getProperty(3).toString();


        String str = "Monthly Prin and Int. = " + String.format("%.2f", Float.parseFloat(MonthlyPrincipalAndInterest)) +
                "\nMonthly Tax = " +String.format("%.2f", Float.parseFloat(MonthlyTax)) +
                "\nMonthly Insurance = " + String.format("%.2f", Float.parseFloat(MonthlyInsurance)) +
                "\nTotal Payment = " +String.format("%.2f", Float.parseFloat(TotalPayment));

        return str;
    }
}
