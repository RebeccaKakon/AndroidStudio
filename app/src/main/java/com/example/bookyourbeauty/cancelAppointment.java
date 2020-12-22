package com.example.bookyourbeauty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class cancelAppointment extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference referenceRoot;
    Appointment newAppo;
    List<String> nameTreatmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);
        String clientId= getIntent().getStringExtra("email");


        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        referenceRoot=rootNode.getReference("Appointment");
        referenceRoot.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //go through all appointments
                String currentIdClient;
                for (DataSnapshot s : snapshot.getChildren()) {
                    //if appointment is free add to list
                    currentIdClient= s.child("idClient").getValue().toString();
                    if (currentIdClient.equals(clientId)) {
                        String Tid=s.child("tretmantId").getValue().toString();
                        referenceRoot=rootNode.getReference("Treatments");
                        referenceRoot.addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //go through all appointments
                                String currentTreatid;
                                for (DataSnapshot s : snapshot.getChildren()) {
                                    //if appointment is free add to list
                                    currentTreatid= s.child("idTreatment").getValue().toString();
                                    if (currentTreatid.equals(Tid)) {
                                        String Tname=s.child("treatmentName").getValue().toString();
                                        nameTreatmentList.add(Tname);
                                    }
                                }
//                                arrayList.add(Tname);
//                                arrayAdapter.notifyDataSetChanged();

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        nameTreatmentList.add(s.child("tretmantId").getValue().toString());
                    }
                }
                //make adapter to connect between the spinner to appointment list
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookAppointmentActivity.this, android.R.layout.simple_spinner_dropdown_item, managerList);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                managersSpinner.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}