package com.example.a001264912.jsonlab;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final int TIMEOUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // WebServer Request URL
        Button btnGetServerData = (Button) findViewById(R.id.btnGetServerData);

        btnGetServerData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // WebServer Request URL
                String serverURL = "http://34.227.59.215/FootballWebService/api/football/getFootballTeam";

                // Use AsyncTask execute Method To Prevent ANR Problem
                new LongOperation().execute(serverURL);
            }
        });
    }

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection c;
        //HttpClient httpClient = new DefaultHttpClient();
        //HttpGet httpGet = new HttpGet(URL);
        try {
            //   HttpResponse response = httpClient.execute(httpGet);
            //   StatusLine statusLine = response.getStatusLine();
            URL u = new URL(URL);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(TIMEOUT);
            c.setReadTimeout(TIMEOUT);
            c.connect();
            int statusCode = c.getResponseCode();
            //int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
//                HttpEntity entity = response.getEntity();
//                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    private class LongOperation  extends AsyncTask<String, Void, String> {

        //   TextView jsonParsed = (TextView)findViewById(R.id.jsonParsed);

        TextView txtDivisionName = (TextView)findViewById(R.id.txtDivisionName);
        TextView txtPlayerInformation = (TextView)findViewById(R.id.txtPlayerInformation);

        @Override
        protected String doInBackground(String... params) {
            /************ Make Post Call To Web Server ***********/
            return readJSONFeed(params[0]);
        }



        protected void onPostExecute(String result) {
            // NOTE: You can call UI Element here.


            /****************** Start Parse Response JSON Data *************/

            String OutputData = "";
            JSONObject jsonResponse;

            try {

                /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                jsonResponse = new JSONObject(result);

                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/

                txtDivisionName.setText("Division       " + jsonResponse.getString("Division"));
                JSONArray players = jsonResponse.getJSONArray("roster");
                int numberOfPlayers = players.length();
                StringBuffer playerInformaion = new StringBuffer();
                for(int i = 0; i < numberOfPlayers; i++) {
                    JSONObject playerDetails = players.optJSONObject(i);
                    playerInformaion.append("Name: ");
                    playerInformaion.append(playerDetails.getString("name"));
                    playerInformaion.append(" Position: ");
                    playerInformaion.append(playerDetails.getString("position"));
                    playerInformaion.append(" Weight: ");
                    playerInformaion.append(playerDetails.getString("weight"));
                    playerInformaion.append("\n");
                }
                txtPlayerInformation.setText(playerInformaion);



            } catch (JSONException e) {

                e.printStackTrace();
            }


        }



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
}
