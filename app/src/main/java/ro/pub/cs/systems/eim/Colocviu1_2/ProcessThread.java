package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class ProcessThread extends Thread {
    private int sum;
    private Context context;

    public ProcessThread(Context context, int sum) {
        this.context = context;
        this.sum = sum;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            sendMessage();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        //broadcast the sum, the current date and time as a string
        String sum_string = Integer.toString(sum);
        intent.setAction("ro.pub.cs.systems.eim.Colocviu1_2.intent.action.sum");
        intent.putExtra("data", sum_string + " " + new Date(System.currentTimeMillis()));
        Log.d("service", sum_string + " " + new Date(System.currentTimeMillis()));
        context.sendBroadcast(intent);
    }
}
