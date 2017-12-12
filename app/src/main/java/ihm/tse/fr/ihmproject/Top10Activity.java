package ihm.tse.fr.ihmproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Top10Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.top_10_activity);

        Collections.sort(BestScore.scores, Collections.<Integer>reverseOrder());
        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, BestScore.scores);
        System.out.println(BestScore.scores);
        ListView listView = findViewById(R.id.list_item);

        listView.setAdapter(adapter);

        Button b = findViewById(R.id.homepage_button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Top10Activity.this, HomeActivity.class));
            }
        });
    }
}
