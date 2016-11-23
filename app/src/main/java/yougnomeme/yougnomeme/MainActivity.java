package yougnomeme.yougnomeme;

import android.app.Activity;
import android.content.Intent;
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
        setupSave();
        setupSensors();
    }

    private void setUpSeekBars() {
        ((SeekBar)findViewById(R.id.uxSeekBarW)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Progres", new Integer(progress).toString());
                ((ImageView)findViewById(R.id.uxImageView)).setScaleX(((float)progress)/100);
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
                Log.d("Progres", new Integer(progress).toString());
                ((ImageView)findViewById(R.id.uxImageView)).setScaleY(((float)progress)/100);
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
                Log.d("Progres", new Integer(progress).toString());
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
                Log.d("Progres", new Integer(progress).toString());
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
                Log.d("Progres", new Integer(progress).toString());
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
                Log.d("Progres", new Integer(progress).toString());
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

    private void setupImageView() {
        ((ImageView)findViewById(R.id.uxImageView)).setOnClickListener(new View.OnClickListener() {
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

            // Set up the view
            ((SeekBar)findViewById(R.id.uxSeekBarW)).setProgress(100);
            ((SeekBar)findViewById(R.id.uxSeekBarH)).setProgress(100);
            ((SeekBar)findViewById(R.id.uxSeekBarA)).setProgress(0);
            ((SeekBar)findViewById(R.id.uxSeekBarR)).setProgress(0);
            ((SeekBar)findViewById(R.id.uxSeekBarG)).setProgress(0);
            ((SeekBar)findViewById(R.id.uxSeekBarB)).setProgress(0);
            findViewById(R.id.uxSliders).setVisibility(View.VISIBLE);
            findViewById(R.id.uxSave).setVisibility(View.VISIBLE);
            findViewById(R.id.uxInstruct).setVisibility(View.GONE);
        }
    }

    private void setupSave() {
        ((Button)findViewById(R.id.uxSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) findViewById(R.id.uxSave);
                String mode = (String) button.getText();
                button.setText(mode == "Manual" ? "Rotation" : "Manual");

                ImageView imageView = (ImageView)findViewById(R.id.uxImageView);
                imageView.setDrawingCacheEnabled(true);
                Bitmap b = imageView.getDrawingCache();
                String res = MediaStore.Images.Media.insertImage(getContentResolver(), b,"MyTitle", "MyDescription");

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
        Log.d("Sensor", event.toString());
        if(((Button)findViewById(R.id.uxSave)).getText() == "Manual")  {
            if (event.sensor.getType()==Sensor.TYPE_ORIENTATION){
                int r = (int)(((event.values[0])*100)/360);
                int g = (int)(((event.values[1]+180)*100)/360);
                int b = (int)(((event.values[2]+90)*100)/180);
                ((SeekBar)findViewById(R.id.uxSeekBarR)).setProgress(r);
                ((SeekBar)findViewById(R.id.uxSeekBarG)).setProgress(g);
                ((SeekBar)findViewById(R.id.uxSeekBarB)).setProgress(b);
                Log.d("RGB: ", String.valueOf(r) + " " + String.valueOf(g) + " " + String.valueOf(b));
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
