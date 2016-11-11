package meisteam.pf.post.ch.pedometer;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by User on 11.11.2016.
 */
public class CommandHandler extends Activity implements StepListener, Runnable {
    private Object[] commandList;
    private int amountOfSteps = 0;

    private TextView commandView;

    public CommandHandler(Object[] commands, TextView viewElement) {
        this.commandList = commands;
        this.commandView = viewElement;
    }

    @Override
    public void onStep() {
        // Check registered steps
        if (amountOfSteps == 0 && commandList.length == 0) {
            Toast.makeText(this, "Kein Befehl registriert!", Toast.LENGTH_SHORT).show();
        } else if (amountOfSteps != 0) {
            amountOfSteps--;
            commandView.setText("Still " + amountOfSteps + " steps to go..");
        }
    }

    private void drawAttentionToDirectionChange(String direction) {
        commandView.setText("Please change direction once to \"" + direction + "\".");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addNextAmountOfSteps(int steps) {
        amountOfSteps = steps;
        commandView.setText("Still " + amountOfSteps + " steps to go..");
    }

    private void removeFirstArrayItem(Object[] array) {
        commandList = Arrays.copyOfRange(array, 1, array.length);
    }

    @Override
    public void run() {
        while (commandList.length != 0) {
            if (commandList[0].getClass().equals(String.class)) {
                drawAttentionToDirectionChange((String) commandList[0]);
                removeFirstArrayItem(commandList);
            } else if (commandList[0].getClass().equals(int.class)) {
                if (amountOfSteps == 0) {
                    addNextAmountOfSteps((int) commandList[0]);
                    removeFirstArrayItem(commandList);
                }
            } else {
                throw new IllegalArgumentException("Cannot handle argument of class " + commandList[0].getClass());
            }
        }

        Toast.makeText(this, "Befehl ausgeführt. Ziel erreicht!", Toast.LENGTH_SHORT).show();
    }
}