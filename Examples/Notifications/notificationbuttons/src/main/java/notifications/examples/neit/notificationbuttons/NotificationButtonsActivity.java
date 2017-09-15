package notifications.examples.neit.notificationbuttons;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class NotificationButtonsActivity extends ActionBarActivity {
    private static final int MY_NOTIFICATION_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_buttons);
        Button buttonSend = (Button)findViewById(R.id.btnSend);

        buttonSend.setOnClickListener(sendNotificationListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_buttons, menu);
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
    private View.OnClickListener sendNotificationListener = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            NotificationManager notificationManager;
            Notification.Builder myNotification;
            NotificationCompat.Builder myCompatNotification;
            notificationManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String channelID;
            myNotification = new Notification.Builder(NotificationButtonsActivity.this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentText("My First Notification!")
                    .setAutoCancel(true);

            myCompatNotification = new NotificationCompat.Builder(NotificationButtonsActivity.this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentText("My First Notification!")
                    .setAutoCancel(true);

            //Context context = getApplicationContext();
            Intent myIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            PendingIntent pendingIntent1
                    = PendingIntent.getActivity(NotificationButtonsActivity.this,
                    0, myIntent1,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            // need API 16 for this call
            // Create the action
            // API Version 23 style
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
                            "Google", pendingIntent1)
                            .build();
            myCompatNotification.addAction(action);
            myNotification.addAction(R.drawable.ic_launcher,"Google",pendingIntent1);

            Intent myIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yahoo.com"));
            PendingIntent pendingIntent2
                    = PendingIntent.getActivity(NotificationButtonsActivity.this,
                    0, myIntent2,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            myNotification.addAction(R.drawable.ic_launcher,"Yahoo",pendingIntent2);

            Intent myIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cnn.com"));
            PendingIntent pendingIntent3
                    = PendingIntent.getActivity(NotificationButtonsActivity.this,
                    0, myIntent3,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            myNotification.addAction(R.drawable.ic_launcher,"cnn",pendingIntent3);

            notificationManager.notify(MY_NOTIFICATION_ID, myNotification.build());
            // uncomment this if you want to see the API 23 version
            //notificationManager.notify(MY_NOTIFICATION_ID, myCompatNotification.build());

        }};

}
