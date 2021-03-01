package com.example.smartelectricity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private TextView dateDisplay;
    private TextView timeDisplay;
    private TextView txtSelect;
    private TextView txtstatus;
    private TextView txtStatus2;

    private Button btnAuto;
    private Button btnManual;


    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtSelect = (TextView)findViewById(R.id.txtSelect);
        txtSelect.bringToFront();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtstatus = (TextView)findViewById(R.id.statusNow);
        txtStatus2 = (TextView)findViewById(R.id.statusNow2);

        timeDisplay = (TextView)findViewById(R.id.txtTime);
        timeDisplay.bringToFront();
        dateDisplay = (TextView)findViewById(R.id.txtDate);
        dateDisplay.bringToFront();
        // FORMAT JAM
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String formatWaktu = timeFormat.format(calendar.getTime());
        String formatTanggal = dateFormat.format(calendar.getTime());
        timeDisplay.setText(formatWaktu);
        dateDisplay.setText(formatTanggal);

        // AMBIL DATA DARI FIREBASE
        mDatabase.child("Auto/Alert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String statusAuto = dataSnapshot.getValue(String.class);
                if (statusAuto.equals("Auto ON"))
                {
                    txtstatus.setText("Smart Mode ON");
                }

                else {
                    txtstatus.setText("Smart Mode OFF");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("Manual/Alert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String statusManual = dataSnapshot.getValue(String.class);
                if (statusManual.equals("Manual ON"))
                {
                    txtStatus2.setText("Manual Light ON");
                }
                else {
                    txtStatus2.setText("Manual Light OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        btnAuto = (Button)findViewById(R.id.btnAutoIntent);
        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AutoActivity.class);
                startActivity(i);
            }
        });

        btnManual = (Button)findViewById(R.id.btnManualIntent);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(HomeActivity.this, ManualActivity.class);
                startActivity(m);
            }
        });

    }
}