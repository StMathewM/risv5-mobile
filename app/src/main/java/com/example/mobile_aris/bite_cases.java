package com.example.mobile_aris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class bite_cases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bite_cases);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bitecases);
        CardView Bite1_card;
        Bite1_card = findViewById(R.id.Bite1_card);
        Bite1_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( bite_cases.this, bite_case_file.class);
                startActivity(intent);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointments:
                        startActivity(new Intent(getApplicationContext(), Appointments.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bitecases:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), user_profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mypets:
                        startActivity(new Intent(getApplicationContext(), my_pets.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}