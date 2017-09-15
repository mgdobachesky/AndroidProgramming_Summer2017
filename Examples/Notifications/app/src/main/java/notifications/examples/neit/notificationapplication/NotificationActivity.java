package notifications.examples.neit.notificationapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class NotificationActivity extends ActionBarActivity {
    private static final int MY_NOTIFICATION_ID=1;

    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button buttonSend = (Button)findViewById(R.id.btnSend);

        buttonSend.setOnClickListener(sendNotificationListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
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
    private OnClickListener sendNotificationListener = new OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            NotificationManager notificationManager;
            Notification.Builder myNotification;
            notificationManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            myNotification = new Notification.Builder(NotificationActivity.this)
           .setSmallIcon(R.drawable.ic_launcher)
           .setContentText("My First Notification!")
           .setAutoCancel(true);

            Context context = getApplicationContext();
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            PendingIntent pendingIntent
                    = PendingIntent.getActivity(NotificationActivity.this,
                    0, myIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

              myNotification.setContentIntent(pendingIntent);
            // using getNotification because we are on API 14, for API 16, I would use build
            notificationManager.notify(MY_NOTIFICATION_ID, myNotification.getNotification());

        }};


      }
