package meisteam.pf.post.ch.pedometer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

	private static final int BUFFER_SIZE = 10;
	private SensorManager sensorManager;
	private RingBuffer initialRotation = new RingBuffer(BUFFER_SIZE);
	private RingBuffer rotation = new RingBuffer(BUFFER_SIZE);
	private Sensor rotationSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walk);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	float[] rotationMatrix = new float[16];
	float[] orientationVals = new float[3];

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
			SensorManager.getOrientation(rotationMatrix, orientationVals);

			orientationVals[0] = (float) Math.toDegrees(orientationVals[0]);

			// Zuerst füllen wir den Buffer mit der Initialrotation um den
			// Startwinkel zu bestimmen, und wenn dieser voll ist (was sehr
			// schnell passiert), dann füllen wir einen zweiten RingBuffer.
			if (initialRotation.getCount() < BUFFER_SIZE) {
				initialRotation.put(orientationVals[0]);
			} else {
				rotation.put(orientationVals[0]);
			}

			// Wenn der zweite Buffer auch gefüllt ist, vergleichen wir die
			// beiden Durchschnittswerte fortlaufend, und sobald wir eine
			// Drehung von grösser als 50 Grad erkennen, melden wir dies.
			if (rotation.getCount() >= BUFFER_SIZE) {
				float r = Math.abs(rotation.getAverage() - initialRotation.getAverage());
				if (r > 50) {
					Toast.makeText(this, "Du hast dich gedreht!", Toast.LENGTH_SHORT).show();
					;
				}
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
