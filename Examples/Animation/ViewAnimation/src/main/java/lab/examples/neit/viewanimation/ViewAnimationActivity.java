package lab.examples.neit.viewanimation;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class ViewAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_animation, menu);
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

    public void animate(View v) {
        TextView tv = (TextView) findViewById(R.id.textView);
        Animation moveText = AnimationUtils.loadAnimation(this, R.anim.animation);
        tv.startAnimation(moveText);
        //Stretch, then simultaneously spin and rotate a View object.
        TextView tvOtherTextView = (TextView) findViewById(R.id.tvOherTextView);
        Animation moveTextOther = AnimationUtils.loadAnimation(this, R.anim.animation2);
        tvOtherTextView.startAnimation(moveTextOther);
    }

}
