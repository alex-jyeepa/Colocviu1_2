package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("all_terms")) {
            String allTerms = intent.getStringExtra("all_terms");
            if (allTerms != null) {
                String[] terms = allTerms.split("\\+");
                int sum = 0;
                for (String term : terms) {
                    sum += Integer.parseInt(term);
                }
                Intent result = new Intent();
                result.putExtra("sum", sum);
                setResult(RESULT_OK, result);
            } else {
                setResult(RESULT_CANCELED, new Intent());
            }
        } else {
            setResult(RESULT_CANCELED, new Intent());
        }
        finish();
    }
}