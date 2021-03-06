package com.example.bookyourbeauty;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginManagerActivity extends AppCompatActivity     {//implements View.OnClickListener
    private EditText email;
    private EditText password;
    private Button login;

    DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setEditText();
        email= (EditText)findViewById(R.id.EmailAddress);
        password= (EditText) findViewById(R.id.Password);
        login= (Button) findViewById(R.id.Login);
        //  listenButtons();

        System.out.println("********************************** ");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailString= email.getText().toString();
                String passString= password.getText().toString();
                FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Managers");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren() ) {
                            //from DB
                            String emailManager = (String) postSnapshot.child("email").getValue().toString();
                            String passwordManager = (String) postSnapshot.child("password").getValue().toString();

                            System.out.println("**********************emailString=  " + emailString + "emailClient=  " + emailManager);
                            System.out.println("**********************passwordString=  " + passString + "passClient=  " + passwordManager);

                            if ((emailManager.equals(emailString)) && (passwordManager.equals(passString))) {
                                System.out.println("***********************in the if********************************");
//                                String currntClientPhone = (String) postSnapshot.child("phone").getValue().toString();
//                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkk"+currntClientPhone);
                                Toast.makeText(LoginManagerActivity.this, "your login was success", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginManagerActivity.this, ManagerOptionsActivity.class);
                                intent.putExtra("email", emailManager);
                                startActivity(intent);
                                break;
                            } else {
                                Toast.makeText(LoginManagerActivity.this, "you typ a Wrong email or password", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent2 = new Intent(v.getContext(), EnterManegerActivity.class);
                            startActivity(intent2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }

    // }
    private void setEditText() {
        email= (EditText)findViewById(R.id.EmailAddress);
        password= (EditText) findViewById(R.id.Password);
        login= (Button) findViewById(R.id.Login);

    }



}