package com.example.username.tripadvisor_signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogOutActivity extends AppCompatActivity implements View.OnClickListener {


    // Authentication Objects
    private Button mLogOutBtn;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    // Database Objects
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    // Database field for specific user to write
    DatabaseReference userRef = database.getReference().child("User");
    DatabaseReference userDesRef = userRef.child("Location");
    // Database field for group users to read
    DatabaseReference groupRef = database.getReference().child("Group");

    // Write data to database
    private EditText dest1Field, dest2Field;

    private Button mFirebaseBtn;


    // Retrieve data from database
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(LogOutActivity.this, MainActivity.class));
                }
            }
        };


        findViewById(R.id.logOutBtn).setOnClickListener(this);
        findViewById(R.id.writeFireBaseBtn).setOnClickListener(this);
        findViewById(R.id.retrieveBtn).setOnClickListener(this);


        dest1Field = (EditText) findViewById(R.id.dest1);
        dest2Field = (EditText) findViewById(R.id.dest2);

        // Retrieve data of User (from other user should be implemented this week)
        //
        // next week:
        //      retrieve group data
        //      display them in another page
        //      display in the form of a ListView
        textView = (TextView) findViewById(R.id.textView);

        groupRef.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String location = dataSnapshot.getValue(String.class);
                textView.setText(location);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }



    // every events trigger by buttons on this page is defined here
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.logOutBtn:
                mAuth.signOut();
                return;

            // Next Week:
            //      should be replaced with a push method other then using a button
            case R.id.writeFireBaseBtn:
                // Add destinations for current User
                userDesRef.child("1").setValue(dest1Field.getText().toString().trim());
                userDesRef.child("2").setValue(dest2Field.getText().toString().trim());


                // Add destinations to Group data
                groupRef.child("1").setValue(dest1Field.getText().toString().trim());
                groupRef.child("2").setValue(dest2Field.getText().toString().trim());
                return;

            case R.id.retrieveBtn:
                groupRef.child("3").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String location = dataSnapshot.getValue(String.class);
                        textView.setText(location);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }
    }
}
