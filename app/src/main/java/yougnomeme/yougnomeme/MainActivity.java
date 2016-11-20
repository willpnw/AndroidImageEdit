package yougnomeme.yougnomeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSeekBars();
    }

    private void setUpSeekBars() {
        ((ImageView)findViewById(R.id.uxImageView)).setScaleX((float)0.5);
        ((ImageView)findViewById(R.id.uxImageView)).setScaleY((float)0.5);
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
    }
}
