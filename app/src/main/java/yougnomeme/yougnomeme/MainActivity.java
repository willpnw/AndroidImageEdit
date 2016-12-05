package yougnomeme.yougnomeme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String FILTER_PREFS = "FilterPrefs";
    private static final int CAMERA_REQUEST = 1888;
    SensorManager sensorManager  = null;
    int alpha = 0;
    int red = 0;
    int green = 0;
    int blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSeekBars();
        setupImageView();
        setupButtons();
        setupSensors();
    }

    private void setUpSeekBars() {
        restorePrefs();
        ((SeekBar)findViewById(R.id.uxSeekBarW)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                (findViewById(R.id.uxImageView)).setScaleX(((float)progress)/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ((SeekBar)findViewById(R.id.uxSeekBarH)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                (findViewById(R.id.uxImageView)).setScaleY(((float)progress)/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ((SeekBar)findViewById(R.id.uxSeekBarA)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = 255 * progress / 100;
                ((ImageView)findViewById(R.id.uxImageView)).setColorFilter(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ((SeekBar)findViewById(R.id.uxSeekBarR)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = 255 * progress / 100;
                ((ImageView)findViewById(R.id.uxImageView)).setColorFilter(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ((SeekBar)findViewById(R.id.uxSeekBarG)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = 255 * progress / 100;
                ((ImageView)findViewById(R.id.uxImageView)).setColorFilter(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ((SeekBar)findViewById(R.id.uxSeekBarB)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue = 255 * progress / 100;
                ((ImageView)findViewById(R.id.uxImageView)).setColorFilter(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void restorePrefs() {
        SharedPreferences prefs = getSharedPreferences(FILTER_PREFS, 0);
        ((SeekBar)findViewById(R.id.uxSeekBarW)).setProgress(prefs.getInt("width", 100));
        ((SeekBar)findViewById(R.id.uxSeekBarH)).setProgress(prefs.getInt("height", 100));
        ((SeekBar)findViewById(R.id.uxSeekBarA)).setProgress(prefs.getInt("alpha", 0));
        ((SeekBar)findViewById(R.id.uxSeekBarR)).setProgress(prefs.getInt("red", 0));
        ((SeekBar)findViewById(R.id.uxSeekBarG)).setProgress(prefs.getInt("green", 0));
        ((SeekBar)findViewById(R.id.uxSeekBarB)).setProgress(prefs.getInt("blue", 0));
        ((Button)findViewById(R.id.uxRotate)).setText(prefs.getBoolean("rotate", false) ? "Manual" : "Rotate");
    }

    private void setupImageView() {
        (findViewById(R.id.uxImageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView)findViewById(R.id.uxImageView);
            imageView.setImageBitmap(photo);

            findViewById(R.id.uxSliders).setVisibility(View.VISIBLE);
            findViewById(R.id.uxButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.uxInstruct).setVisibility(View.GONE);
        }
    }

    private void setupButtons() {
        (findViewById(R.id.uxSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(FILTER_PREFS, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("width", ((SeekBar)findViewById(R.id.uxSeekBarW)).getProgress());
                editor.putInt("height",((SeekBar)findViewById(R.id.uxSeekBarH)).getProgress());
                editor.putInt("alpha", ((SeekBar)findViewById(R.id.uxSeekBarA)).getProgress());
                editor.putInt("red",   ((SeekBar)findViewById(R.id.uxSeekBarR)).getProgress());
                editor.putInt("green", ((SeekBar)findViewById(R.id.uxSeekBarG)).getProgress());
                editor.putInt("blue",  ((SeekBar)findViewById(R.id.uxSeekBarB)).getProgress());
                editor.putBoolean("rotate", ((Button)findViewById(R.id.uxRotate)).getText() == "Rotate" ? false : true);
                editor.commit();
                Toast.makeText(MainActivity.this, "Settings Saved", Toast.LENGTH_SHORT).show();
            }
        });

        (findViewById(R.id.uxRotate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) findViewById(R.id.uxRotate);
                String mode = (String) button.getText();
                button.setText(mode == "Manual" ? "Rotation" : "Manual");
                Toast.makeText(MainActivity.this, mode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(((Button)findViewById(R.id.uxRotate)).getText() == "Manual")  {
            if (event.sensor.getType()==Sensor.TYPE_ORIENTATION){
                int r = (int)(((event.values[0])*100)/360);
                int g = (int)(((event.values[1]+180)*100)/360);
                int b = (int)(((event.values[2]+90)*100)/180);
                ((SeekBar)findViewById(R.id.uxSeekBarR)).setProgress(r);
                ((SeekBar)findViewById(R.id.uxSeekBarG)).setProgress(g);
                ((SeekBar)findViewById(R.id.uxSeekBarB)).setProgress(b);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
