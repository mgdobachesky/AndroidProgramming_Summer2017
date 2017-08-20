package lab.examples.neit.videoplayback;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


public class MainActivity extends ActionBarActivity {
    VideoView vView = null;
    MediaController mc = null;
    TextView ExternalStorage;
    TextView InternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExternalStorage = (TextView)findViewById(R.id.txtExternalStorage);
        InternalStorage = (TextView)findViewById(R.id.txtInternalStorage);
        ExternalStorage.setText(Environment.getExternalStorageDirectory().toString());
        InternalStorage.setText(getExternalFilesDir(null).toString());


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

    public void PlayVideo(View myView){
        vView = (VideoView) findViewById(R.id.videoView);
        mc = new MediaController(this);
        vView.setMediaController(mc);
        //vView.setVideoPath("/storage/emulated/0/Piano_keys.mp4");
        // using internal storage for now
        //vView.setVideoPath("/storage/emulated/legacy/Piano_keys.mp4");
        vView.setVideoPath(getExternalFilesDir(null).toString() + "/Piano_keys.mp4");
        vView.requestFocus();
        mc.show();
        vView.start();
    }
}
