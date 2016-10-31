# AppQuest Treasure Map

![HSR](http://appquest.hsr.ch/images/fho.png)

## About

\#TODO

AppQuest Pedometer is the 4. application for the [App Quest 2016](http://appquest.hsr.ch/) Treasure Hunt. The application must be able to count the steps and tell when you have to turn right/left to find the treasure.

### General
|   |  |
|---|---|
| AppQuest Repository | [AppQuest 2016](https://github.com/mirioeggmann/appquest) |
| Application Requirements | http://appquest.hsr.ch/2016/schrittzaehler |
| Minimum API Level | [API level 23 (Marshmallow)](https://developer.android.com/about/versions/marshmallow/android-6.0.html) |
| Development Environment | [Android Studio](https://developer.android.com/studio/index.html) |

### Example

\#TODO

![AppQuest Pedometer](http://appquest.hsr.ch/2014/wp-content/uploads/stepcounter.png)

### Links

- https://de.wikipedia.org/w/index.php?title=Warteschlange_(Datenstruktur)&redirect=no#Ringpuffer
 

### Given code snippets

\#TODO

Formula
```
sqrt(x^2 + y^2 + z^2)
```

[Download classes](http://appquest.hsr.ch/2015/wp-content/uploads/stepcounter-android.zip)

[stepcounter.java](https://gist.githubusercontent.com/misto/ea370d1ddcaa4a57df1bef2dce77fe1e/raw/673e6046d23f0980143ff80a0eda41e52f5394be/stepcounter.java)
```
public class Activity extends Activity implements StepListener {

	private SensorManager sensorManager;
	private Sensor sensor;
	private StepCounter stepCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walk);

		stepCounter = new StepCounter(this);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (stepCounter != null) {
			sensorManager.registerListener(stepCounter, sensor, SensorManager.SENSOR_DELAY_FASTEST);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (stepCounter != null) {
			sensorManager.unregisterListener(stepCounter);
		}
	}

	@Override
	public void onStep() {
		// Ein Schritt wurde gemacht!
	}
---

AppQuest Logbuch format
```
{
  "task": "Schrittzaehler",
  "startStation": 1,
  "endStation": 4
}
```

## License
[MIT License](https://github.com/mirioeggmann/appquest-pedometer/blob/master/LICENSE)
