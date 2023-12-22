package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputLayout inputEmail;
    private Button btnReset;
    private TextView tvRegister, tvLogin;
    private FirebaseAuth fAuth;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initView();
    }

    private void initView() {
        fAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        inputEmail = findViewById(R.id.inputEmail);
        tvLogin = findViewById(R.id.tvLogin);
        tvRegister = findViewById(R.id.tvRegister);
        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        Intent intent = new Intent(ResetActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        Intent intent = new Intent(ResetActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void reset() {
        String email = editTextEmail.getText().toString().trim();
        if(validate(email, inputEmail)){
            fAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password reset email sent. Check your email.", Toast.LENGTH_SHORT).show();
                                startCountdownTimer(10000);
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to send password reset email. Check your email and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
    private void startCountdownTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(ResetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private boolean validate(String email, TextInputLayout emailInputLayout) {
        boolean isEmailValid = isValidEmail(email);

        if (email.isEmpty()) {
            showError(emailInputLayout, "Email is required");
        } else if (!isEmailValid) {
            showError(emailInputLayout, "Invalid email address");
        }   else {
            clearError(emailInputLayout);
        }


        return isEmailValid;
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
