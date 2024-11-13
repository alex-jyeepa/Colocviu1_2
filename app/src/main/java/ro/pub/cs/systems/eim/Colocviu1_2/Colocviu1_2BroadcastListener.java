package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Colocviu1_2BroadcastListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(Constants.TAG, "Action: " + action);
        if ("ro.pub.cs.systems.eim.Colocviu1_2.intent.action.sum".equals(action)) {
            String data = intent.getStringExtra("data");
            Toast.makeText(context, data, Toast.LENGTH_LONG).show();
        }
    }
}
