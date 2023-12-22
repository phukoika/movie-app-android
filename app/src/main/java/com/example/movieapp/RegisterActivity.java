package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private TextInputEditText editTextEmail,editTextPassword, editTextUsername, editTextCPassword;
    private TextInputLayout inputEmail, inputPassword, inputUsername, inputCPassword;
    private TextView  tvLogin;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnRegister = findViewById(R.id.btnRegister);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCPassword = findViewById(R.id.editTextCPassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputCPassword = findViewById(R.id.inputCPassword);
        tvLogin = findViewById(R.id.tvLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void register() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cPassword = editTextCPassword.getText().toString().trim();
        if (validate(username, email, password, cPassword, inputUsername, inputEmail, inputPassword, inputCPassword)){
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        saveUserToFirebase(username, email);
                    }else {
                        Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveUserToFirebase(String username, String email) {
        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterActivity.this, "Register is successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("This","onFailure   : "  + e.toString());
            }
        });
    }

    private boolean validate(String username,String email, String password, String cPassword, TextInputLayout usernameInputLayout, TextInputLayout emailInputLayout, TextInputLayout passwordInputLayout, TextInputLayout cPasswordInputLayout) {
        boolean isEmailValid = isValidEmail(email);
        boolean isPasswordValid = isValidPassword(password);
        boolean isCPasswordValid = isValidPassword(cPassword);
        if(username.isEmpty()){
            showError(usernameInputLayout, "Username is required");
        }
        if (email.isEmpty()) {
            showError(emailInputLayout, "Email is required");
        } else if (!isEmailValid) {
            showError(emailInputLayout, "Invalid email address");
        }   else {
            clearError(emailInputLayout);
        }
        if(password.isEmpty()){
            showError(passwordInputLayout, "Password is required");
        }
        else if (!isPasswordValid) {
            showError(passwordInputLayout, "Password must be at least 6 characters long.");
        } else {
            clearError(passwordInputLayout);
        }
        if(cPassword.isEmpty()){
            showError(cPasswordInputLayout, "Confirm password is required");
        } else if (!isCPasswordValid) {
            showError(cPasswordInputLayout, "Confirm password must be at least 6 characters long");
        } else if (cPassword == password) {
            showError(cPasswordInputLayout, "Confirm password must match the entered password");
        }

        return isEmailValid && isPasswordValid;
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void showError(TextInputLayout inputLayout, String errorMessage) {
        inputLayout.setError(errorMessage);
        inputLayout.setErrorEnabled(true);
    }

    private void clearError(TextInputLayout inputLayout) {
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);
    }
}
