package com.example.smartelectricity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {
    private Button mFirebaseBtn;
    private Button mFirebaseBtnOff;
    private Button mFirebaseManualOff;
    private Button mFirebaseAutoOff;
    private Switch mSwitchAuto;
    private Switch mSwitchManual;

    private TextView txtindicator;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparent(this);
        mFirebaseBtn = (Button) findViewById(R.id.btn_relay);
        mFirebaseBtnOff = (Button) findViewById(R.id.btn_off);
        mFirebaseManualOff = (Button) findViewById(R.id.btn_manualoff);
        mFirebaseAutoOff = (Button) findViewById(R.id.btn_autooff);

        mSwitchAuto = (Switch) findViewById(R.id.switch1);
        mSwitchManual = (Switch) findViewById(R.id.switch2);

        txtindicator = (TextView) findViewById(R.id.txt_indicator);


        mDatabase = FirebaseDatabase.getInstance().getReference();

//        DatabaseReference toggleAutoRef = mDatabase.getRef("ToggleAuto/Value");





        // BTN UNTUK KIRIM MANUAL ON
        mFirebaseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mDatabase.child("Manual/Value").setValue(1);
                mDatabase.child("Manual/Alert").setValue("Manual On");

            }
        });


        // BTN UNTUK MANUAL OFF
        mFirebaseManualOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Manual/Value").setValue(0);
                mDatabase.child("Manual/Alert").setValue("Manual Off");
            }
        });

        // BTN UNTUK KIRIM ATUO ON
        mFirebaseBtnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Auto/Value").setValue(1);
                mDatabase.child("Auto/Alert").setValue("Auto On");
            }
        });


        // BTN UNTUK AUTO OFF
        mFirebaseAutoOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Auto/Value").setValue(0);
                mDatabase.child("Auto/Alert").setValue("Auto Off");
            }
        });

        mSwitchAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mSwitchManual.setChecked(false);
                    mDatabase.child("ToggleAuto/Value").setValue(1);
                    mDatabase.child("ToggleAuto/Alert").setValue("Toggle ON");
                }
                else {
                    mDatabase.child("ToggleAuto/Value").setValue(0);
                    mDatabase.child("ToggleAuto/Alert").setValue("Toggle OFF");
                }
            }
        });

        mSwitchManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mSwitchAuto.setChecked(false);
                    mDatabase.child("ToggleManual/Value").setValue(1);
                    mDatabase.child("ToggleManual/Alert").setValue("Toggle ON");
                }
                else {

                    mDatabase.child("ToggleManual/Value").setValue(0);
                    mDatabase.child("ToggleManual/Alert").setValue("Toggle OFF");

                }
            }
        });

        mDatabase.child("ToggleAuto/Alert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String statos = dataSnapshot.getValue(String.class);
                if (statos=="Toggle ON")
                {
                    txtindicator.setText("TOGGLE AUTO AKTIF");

                }
                else {

                txtindicator.setText("TOGGLE AUTO INAKTIF");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}