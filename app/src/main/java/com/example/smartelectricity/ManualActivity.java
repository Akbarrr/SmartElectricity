package com.example.smartelectricity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ManualActivity extends AppCompatActivity {
    private TextView timeDisplay, dateDisplay, teksAuto;
    private Switch mSwitchAuto;

    private ImageView imageOn, imageOff;


    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);


        timeDisplay = (TextView)findViewById(R.id.txtTime);
        timeDisplay.bringToFront();
        dateDisplay = (TextView)findViewById(R.id.txtDate);
        dateDisplay.bringToFront();

        teksAuto = (TextView)findViewById(R.id.txtAuto);
        teksAuto.bringToFront();

        mSwitchAuto = (Switch)findViewById(R.id.switchAuto);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        imageOff = (ImageView)findViewById(R.id.lampOff);
        imageOn = (ImageView)findViewById(R.id.lampOn);


        // FORMAT JAM
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String formatWaktu = timeFormat.format(calendar.getTime());
        String formatTanggal = dateFormat.format(calendar.getTime());
        timeDisplay.setText(formatWaktu);
        dateDisplay.setText(formatTanggal);


        // AMBIL DATA DARI DATABASE
        mDatabase.child("Manual/Alert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String statos = dataSnapshot.getValue(String.class);
                if (statos.equals("Manual ON"))
                {
                    imageOff.setVisibility(View.INVISIBLE);
                    imageOn.setVisibility(View.VISIBLE);
                    teksAuto.setText("LIGHTS ON");
                    mSwitchAuto.setChecked(true);
                    //txtindicator.setText("TOGGLE AUTO AKTIF");

                }
                else {
                    imageOff.setVisibility(View.VISIBLE);
                    imageOn.setVisibility(View.INVISIBLE);
                    teksAuto.setText("LIGHTS OFF");
                    mSwitchAuto.setChecked(false);
                    //txtindicator.setText("TOGGLE AUTO INAKTIF");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // KIRIM DATA KE FIREBASE
        mSwitchAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //mSwitchManual.setChecked(false);
                    mDatabase.child("Manual/Value").setValue(1);
                    mDatabase.child("Manual/Alert").setValue("Manual ON");
                    teksAuto.setText("LIGHTS ON");
                    imageOff.setVisibility(View.INVISIBLE);
                    imageOn.setVisibility(View.VISIBLE);

                    // DISABLE MODE AUTO
                    mDatabase.child("Auto/Value").setValue(0);
                    mDatabase.child("Auto/Alert").setValue("Auto OFF");


                }
                else {
                    mDatabase.child("Manual/Value").setValue(0);
                    mDatabase.child("Manual/Alert").setValue("Manual OFF");
                    teksAuto.setText("LIGHTS OFF");
                    imageOff.setVisibility(View.VISIBLE);
                    imageOn.setVisibility(View.INVISIBLE);


                }
            }
        });


    }
}