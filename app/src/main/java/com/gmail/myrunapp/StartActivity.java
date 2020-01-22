package com.gmail.myrunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    private EditText eMail, password;
    private DbHelper dbHelper;
    private UsersManager usersManager;
    private List<UserData> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        eMail = findViewById(R.id.editTextEMailS);
        password = findViewById(R.id.editTextPasswordS);

        dbHelper = new DbHelper(this);
        usersManager = new UsersManager(dbHelper);
    }

    public void signIn(View view) {

        userList = usersManager.getUsers();

        Map<String, String> pairsPas = new HashMap<>();
        Map<String, String> pairsName = new HashMap<>();

        for (UserData x: userList) {
            pairsPas.put(x.getEmail(), x.getPassword());
            pairsName.put(x.getEmail(), x.getName());
        }

        if (!eMail.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
            if (!pairsPas.containsKey(eMail.getText().toString().trim())) {
                Toast.makeText(this, "wrong eMale", Toast.LENGTH_SHORT).show();
            } else if (!pairsPas.get(eMail.getText().toString().trim()).equals(password.getText().toString().trim())){
                Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("eMail", eMail.getText().toString().trim());
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);

    }
}



