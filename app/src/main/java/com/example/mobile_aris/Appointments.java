package com.example.mobile_aris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Appointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.appointments);
        CardView pending_card;
                CardView completed_card;
        CardView cancelled_card;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointments:
                        return true;
                    case R.id.bitecases:
                        startActivity(new Intent(getApplicationContext(), bite_cases.class));
                        overridePendingTransition(0,0);
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

        pending_card = findViewById(R.id.pending_card);

        pending_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, pending_tab.class);
                startActivity(intent);
            }
        });
        completed_card = findViewById(R.id.completed_card);

        completed_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, completed_tab.class);
                startActivity(intent);
            }
        });
        cancelled_card = findViewById(R.id.cancelled_card);

        cancelled_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, cancelled_tab.class);
                startActivity(intent);
            }
        });
        Button SetAppointment;


        SetAppointment = findViewById(R.id.SetAppointment);

        SetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, set_appointment.class);
                startActivity(intent);
            }
        });
    }
}