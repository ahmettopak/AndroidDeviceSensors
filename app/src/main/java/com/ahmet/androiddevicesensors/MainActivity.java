package com.ahmet.androiddevicesensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    private Sensor rotationVectorSensor;
    private TextView gyroscopeDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroscopeDataTextView = findViewById(R.id.gyroText);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (gyroscope == null) {
            Toast.makeText(this, "Cihazınızda gyroscope sensörü bulunmuyor. ", Toast.LENGTH_SHORT).show();
        }


        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "Cihazınızda accelerometer sensörü bulunmuyor. ", Toast.LENGTH_SHORT).show();

        }

        if (accelerometer != null) {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "Cihazınızda rotationVector sensörü bulunmuyor. ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    private float[] accelerometerData = new float[3];
    private float[] gyroscopeData = new float[3];
    private static final float THRESHOLD = 0.2f; // Eşik değeri (threshold) - Bu değeri ayarlayarak hassasiyeti kontrol edebilirsiniz


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            int x = (int) (event.values[0]*100);
            int y = (int) (event.values[1]*100);
            int z = (int) (event.values[2]*100);

            accelerometerData = event.values.clone();

            // Gyroscope verilerini kullanmak için burada gerekli işlemleri yapabilirsiniz.
            Örnek: gyroscopeDataTextView.setText("X: " + x + "\nY: " + y + "\nZ: " + z);
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            gyroscopeData = event.values.clone();

            //Örnek: gyroscopeDataTextView.setText("X: " + x + "\nY: " + y + "\nZ: " + z);
        }

//        float roll = (float) Math.toDegrees(Math.atan2(accelerometerData[1], accelerometerData[2]));
//        float pitch = (float) Math.toDegrees(Math.atan2(-accelerometerData[0], Math.sqrt(accelerometerData[1] * accelerometerData[1] + accelerometerData[2] * accelerometerData[2])));
//
//        float rateOfTurn = gyroscopeData[2]; // Z ekseni etrafında dönme hızı
//
//        if (Math.abs(rateOfTurn) > THRESHOLD) {
//            if (rateOfTurn > 0) {
//                // Sağa dönme tespit edildi
//                Log.d("VR Uygulama", "Sağa dönüyor");
//            } else {
//                // Sola dönme tespit edildi
//                Log.d("VR Uygulama", "Sola dönüyor");
//            }
//        }
//        else {
//            // Cihaz sabit duruyor
//            Log.d("VR Uygulama", "Sabit duruyor");
//        }


        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            float[] orientationValues = new float[3];

            SensorManager.getRotationMatrix(rotationMatrix, null, event.values, event.values);
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            float azimuth = orientationValues[0];
//            float pitch = orientationValues[1];
//            float roll = orientationValues[2];

            // Yatay (azimuth), dikey (pitch) ve eğik (roll) dönüş açılarını kullanabilirsiniz.

//            String floatStringX = String.valueOf(event.values[0]);
//            String floatStringY = String.valueOf(event.values[1]);
//            String floatStringZ = String.valueOf(event.values[2]);
//
//            String firstFourCharactersX = floatStringX.substring(0, Math.min(floatStringX.length(), 1));
//            String firstFourCharactersY = floatStringY.substring(0, Math.min(floatStringY.length(),1));
//            String firstFourCharactersZ = floatStringZ.substring(0, Math.min(floatStringZ.length(), 1));
//
//            int resultIntX = Integer.parseInt(firstFourCharactersX);
//            int resultIntY = Integer.parseInt(firstFourCharactersY);
//            int resultIntZ = Integer.parseInt(firstFourCharactersZ);
//
//
//            // Örnek: gyroscopeDataTextView.setText("X: " + azimuth + "\nY: " + pitch + "\nZ: " + roll);
//            Örnek: gyroscopeDataTextView.setText("X: " + resultIntX + " Y: " + resultIntY + " Z: " +resultIntZ);
            int x = (int) (event.values[0] * 100);
            int y = (int) (event.values[1] * 100);
            int z = (int) (event.values[2] *100);

            //Örnek: gyroscopeDataTextView.setText("X: " + x + "\nY: " + y + "\nZ: " + z);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Gyroscope hassasiyet değişikliklerini ele almak için gerektiğinde bu metodu kullanabilirsiniz.
    }
}
