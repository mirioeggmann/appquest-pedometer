package meisteam.pf.post.ch.pedometer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LogFactory {
    private Context context;
    private Intent logBook;

    public LogFactory(Context context) {
        // Save private variables
        this.context = context;

        // Check if logbook is installed
        logBook = new Intent("ch.appquest.intent.LOG");
        if (context.getPackageManager().queryIntentActivities(logBook, PackageManager.MATCH_DEFAULT_ONLY).isEmpty()) {
            Toast.makeText(context, "Logbook app not installed", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public Intent getLogActivity(int startLocation, int endLocation) {
        // Create log message
        JSONObject jsonMessage = new JSONObject();

        try {
            // First part of json message
            jsonMessage.put("task", "Schrittzaehler");
            jsonMessage.put("startStation", startLocation);
            jsonMessage.put("endStation", endLocation);
        } catch (JSONException e) {
            Log.e(getClass().toString(), e.getLocalizedMessage());
        }

        // Add collected data
        Log.i(getClass().toString(), "Collected log data: " + jsonMessage.toString());
        logBook.putExtra("ch.appquest.logmessage", jsonMessage.toString());

        // Log
        return logBook;
    }
}