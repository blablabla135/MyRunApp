package com.gmail.myrunapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, eMail, password, confirmPassword;
    private DbHelper dbHelper;
    private UsersManager usersManager;
    private List<UserData> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.editTextUserNameR);
        eMail = findViewById(R.id.editTextEMailR);
        password = findViewById(R.id.editTextPasswordR);
        confirmPassword = findViewById(R.id.editTextPasswordConfirmR);

        dbHelper = new DbHelper(this);
        usersManager = new UsersManager(dbHelper);
    }

    public void signUp(View view) {

        userList = usersManager.getUsers();

        List<String> eMales = new ArrayList<String>();

        for (UserData x : userList) {
            eMales.add(x.getEmail());
        }

        if (!name.getText().toString().trim().equals("") && !eMail.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("") && !confirmPassword.getText().toString().trim().equals("")) {
            if (eMales.contains(eMail.getText().toString().trim())) {
                Toast.makeText(this, "this eMale is already taken", Toast.LENGTH_SHORT).show();
            } else if (!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                Toast.makeText(this, "confirm password", Toast.LENGTH_SHORT).show();
            } else {
                UserData user = new UserData();
                user.setEmail(eMail.getText().toString().trim());
                user.setName(name.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());

                usersManager.addUser(user);

                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}