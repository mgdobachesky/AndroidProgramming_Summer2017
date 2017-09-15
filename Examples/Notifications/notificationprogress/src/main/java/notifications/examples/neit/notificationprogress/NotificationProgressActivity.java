package notifications.examples.neit.notificationprogress;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class NotificationProgressActivity extends ActionBarActivity {
    private static final int MY_NOTIFICATION_ID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_progress);
        Button buttonSend = (Button)findViewById(R.id.btnSend);
        buttonSend.setOnClickListener(sendNotificationListener);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_progress, menu);
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
            final NotificationManager notificationManager;
            final Notification.Builder myNotification;
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            myNotification = new Notification.Builder(NotificationProgressActivity.this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentText("My First Notification With Progress Bar")
                    .setAutoCancel(true);

            // Start a lengthy operation in a background thread
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            int incr;
                            // Do the "lengthy" operation 20 times
                            for (incr = 0; incr <= 100; incr += 5) {
                                // Sets the progress indicator to a max value, the
                                // current completion percentage, and "determinate"
                                // state
                                // This is for the determinate progress bar
                                myNotification.setProgress(100, incr, false);
                                // This is for an indeterminate activity indicator
                                //myNotification.setProgress(0,0,true);
                                // Displays the progress bar for the first time.
                                notificationManager.notify(MY_NOTIFICATION_ID, myNotification.getNotification());
                                // Sleeps the thread, simulating an operation
                                // that takes time
                                try {
                                    // Sleep for 5 seconds
                                    Thread.sleep(1 * 1000);
                                } catch (InterruptedException e) {
                                    Log.d("Progress Notification", "sleep failure");
                                }
                            }
                            // When the loop is finished, updates the notification
                            myNotification.setContentText("Download complete")
                                    // Removes the progress bar
                                    .setProgress(0, 0, false);
                            notificationManager.notify(MY_NOTIFICATION_ID, myNotification.getNotification());
                        }

                    }).start();
        }};
}