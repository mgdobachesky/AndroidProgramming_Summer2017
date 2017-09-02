package lab.examples.neit.gestureapplication;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    GestureDetector gestureScanner;
    TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = (TextView)findViewById(R.id.Results);


        gestureScanner = new GestureDetector(this, new GestureDetector.OnGestureListener() {



            @Override
            public boolean onDown(MotionEvent e) {
                String output = String.format("Down Event: (%f, %f)\n", e.getX(), e.getY());
                results.setText(results.getText() + output);
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                String output = String.format("Show Press Event: (%f, %f)\n", e.getX(), e.getY());
                results.setText(results.getText() + output);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                String output = String.format("Single Tap Up Event: (%f, %f)\n", e.getX(), e.getY());
                results.setText(results.getText() + output);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                String output = String.format("Scroll Event: (%f, %f), (%f, %f)\n\tdX = %f, dY = %f\n",
                        e1.getX(), e1.getY(), e2.getX(), e2.getY(), distanceX, distanceY);
                results.setText(results.getText() + output);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                String output = String.format("Long Press Event: (%f, %f)\n", e.getX(), e.getY());
                results.setText(results.getText() + output);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                String output = String.format("Fling Event: (%f, %f), (%f, %f)\n\tvX = %f, vY = %f\n",
                        e1.getX(), e1.getY(), e2.getX(), e2.getY(), velocityX, velocityY);
                results.setText(results.getText() + output);
                return true;
            }
        });
    }

    public boolean onTouchEvent(MotionEvent e) {
        return gestureScanner.onTouchEvent(e);
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

    public void ClearTextView(View v){
        results.setText("");
    }


}
