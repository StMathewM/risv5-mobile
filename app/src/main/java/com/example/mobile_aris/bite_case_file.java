package com.example.mobile_aris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bite_case_file extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bite_case_file);
        Button SendHealthReport;
        SendHealthReport = findViewById(R.id.SendHealthReport);
        SendHealthReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( bite_case_file.this, health_report.class);
                startActivity(intent);
            }
        });
    }
}