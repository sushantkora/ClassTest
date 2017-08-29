package com.example.kora1101.classtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public TextView x_value;
    public TextView y_value;
    public TextView z_value;
    public TextView magnetic_x;
    public TextView magnetic_y;
    public TextView magnetic_z;
    public TextView gyro_x;
    public TextView gyro_y;
    public TextView gyro_z;

    public TextView light_x;
    public TextView light_y;
    public TextView light_z;

    public Button on;
    public Button off;
    int index=20;
    double filteredData=0;
    int graphSize=200;
    GraphView graph_normal;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private Sensor light;
    private Sensor gyro;
    private Sensor accelerometer;
    private LineGraphSeries<DataPoint> series_normal, series_filtered;
    boolean message=true;
    public List<Acclerometer> Data=new ArrayList<>();
    public List<magnetic> DataA=new ArrayList<>();
    public List<gyro> DataB=new ArrayList<>();
    public List<light> DataC=new ArrayList<>();
    List<Double> normalData =new ArrayList<>(500);
    HandlerClass handlerClass;

    public static Button button_smb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph_normal=(GraphView)findViewById(R.id.graph_normal);
        x_value=(TextView)findViewById(R.id.x_id);
        y_value=(TextView)findViewById(R.id.y_id);
        z_value=(TextView)findViewById(R.id.z_id);

        magnetic_x=(TextView)findViewById(R.id.magnetic_x);
        magnetic_y=(TextView)findViewById(R.id.magnetic_y);
        magnetic_z=(TextView)findViewById(R.id.magnetic_z);


        gyro_x=(TextView)findViewById(R.id.gyro_x);
        gyro_y=(TextView)findViewById(R.id.gyro_y);
        gyro_z=(TextView)findViewById(R.id.gyro_z);

        light_x=(TextView)findViewById(R.id.light_x);
        light_y=(TextView)findViewById(R.id.light_y);
        light_z=(TextView)findViewById(R.id.light_z);

        on=(Button)findViewById(R.id.button);


        handlerClass=new HandlerClass();

        graph_normal.getViewport().setScalable(true);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            light = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }

        graph_normal.getViewport().setYAxisBoundsManual(true);
        graph_normal.getViewport().setMinY(0);
        graph_normal.getViewport().setMaxY(30);

        series_normal = new LineGraphSeries<DataPoint>();
        series_normal.setTitle("sqrt(x*x+y*y+z*z)");
        series_normal.setColor(Color.RED);

        graph_normal.addSeries(series_normal);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message =true;
                Data=new ArrayList<Acclerometer>();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message =false;
            }
        });



    }
    int delay=0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (message && sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            delay++;
            if (delay == 5) {
                delay = 0;
            }
            if (delay == 0) {
                float x, y, z;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                normalData.add(Math.sqrt(x * x + y * y + z * z));
                x_value.setText(Float.toString(x));
                y_value.setText(Float.toString(y));
                z_value.setText(Float.toString(z));
                Acclerometer accelerationData = new Acclerometer(index, x, y, z);
                handlerClass.obtainMessage(2, accelerationData).sendToTarget();
                index++;
                if (Data == null) {
                    Data = new ArrayList<>();
                } else {
                    Data.add(accelerationData);
                }


            }
        }
        if (message && sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            delay++;
            if (delay == 5) {
                delay = 0;
            }
            if (delay == 0) {
                float x, y, z;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                normalData.add(Math.sqrt(x * x + y * y + z * z));
                magnetic_x.setText(Float.toString(x));
                magnetic_y.setText(Float.toString(y));
                magnetic_z.setText(Float.toString(z));
                magnetic accelerationData = new magnetic(index, x, y, z);
                handlerClass.obtainMessage(2, accelerationData).sendToTarget();
                index++;
                if (DataA == null) {
                    DataA = new ArrayList<>();
                } else {
                    DataA.add(accelerationData);
                }


            }
        }
        if (message && sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            delay++;
            if (delay == 5) {
                delay = 0;
            }
            if (delay == 0) {
                float x, y, z;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                normalData.add(Math.sqrt(x * x + y * y + z * z));
                gyro_x.setText(Float.toString(x));
                gyro_y.setText(Float.toString(y));
                gyro_z.setText(Float.toString(z));
                gyro accelerationData = new gyro(index, x, y, z);
                handlerClass.obtainMessage(2, accelerationData).sendToTarget();
                index++;
                if (DataB == null) {
                    DataB = new ArrayList<>();
                } else {
                    DataB.add(accelerationData);
                }


            }
        }
        if (message && sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            delay++;
            if (delay == 5) {
                delay = 0;
            }
            if (delay == 0) {
                float x, y, z;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                normalData.add(Math.sqrt(x * x + y * y + z * z));
                light_x.setText(Float.toString(x));
                light_y.setText(Float.toString(y));
                light_z.setText(Float.toString(z));
                light accelerationData = new light(index, x, y, z);
                handlerClass.obtainMessage(2, accelerationData).sendToTarget();
                index++;
                if (DataC == null) {
                    DataC = new ArrayList<>();
                } else {
                    DataC.add(accelerationData);
                }


            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    class HandlerClass extends Handler {
        HandlerClass() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    series_filtered.appendData(
                            new DataPoint(index,filteredData),true,graphSize);

                    return;
                case 2:
                    series_normal.appendData(
                            new DataPoint(index,((Acclerometer )msg.obj).NormalData()),true,graphSize);

                    return;
                default:
                    return;
            }
        }
    }
    public void OnclickButtonListener(){
        button_smb =(Button)findViewById(R.id.button);
        button_smb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("com.example.kora1101.classtest;");
                        startActivity(intent);
                    }
                }
        );


}
