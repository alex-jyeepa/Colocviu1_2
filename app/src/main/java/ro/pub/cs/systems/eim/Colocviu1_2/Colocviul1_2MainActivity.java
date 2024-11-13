package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Colocviul1_2MainActivity extends AppCompatActivity {

    Button add, compute;
    EditText nextTerm, allTerms;
    int modified = 0;
    BroadcastReceiver broadcastReceiver = new Colocviu1_2BroadcastListener();
    IntentFilter intentFilter = new IntentFilter();
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            int sum = result.getData().getIntExtra("sum", -1);
            allTerms.setText(String.valueOf(sum));
            Toast.makeText(getApplicationContext(), "The activity returned with result " + sum, Toast.LENGTH_LONG).show();
            if (Integer.parseInt(allTerms.getText().toString()) > 10) {
                Intent intent2 = new Intent(this, Colocviu1_2Service.class);
                intent2.putExtra("all_terms", Integer.parseInt(allTerms.getText().toString()));
                startService(intent2);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        allTerms = findViewById(R.id.all_terms);
        nextTerm = findViewById(R.id.next_term);
        add = findViewById(R.id.add_button);
        compute = findViewById(R.id.compute_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allTermsText = allTerms.getText().toString();
                String nextTermText = nextTerm.getText().toString();
                if (allTermsText.isEmpty()) {
                    allTerms.setText(nextTermText);
                    nextTerm.setText("");
                    modified = 1;
                } else {
                    allTerms.setText(allTermsText + "+" + nextTermText);
                    nextTerm.setText("");
                    modified = 1;
                }
            }
        });
        Context context = this;
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modified == 0) {
                    return;
                }
                Intent intent = new Intent(context, Colocviu1_2SecondaryActivity.class);
                intent.putExtra("all_terms", allTerms.getText().toString());
                activityResultLauncher.launch(intent);

                modified = 0;
            }
        });

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey("all_terms")) {
                allTerms.setText(savedInstanceState.getString("all_terms"));
            } else {
                allTerms.setText("");
            }
        }

        intentFilter.addAction("ro.pub.cs.systems.eim.Colocviu1_2.intent.action.sum");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            intentFilter.setPriority(100);
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("all_terms", allTerms.getText().toString());
        savedInstanceState.putString("next_term", nextTerm.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}