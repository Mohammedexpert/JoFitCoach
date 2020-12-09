package com.arslan6015.jofitcoach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arslan6015.jofitcoach.Model.UserGeneralInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText SignUpFullName,signUpEmail,singUpPassword,singUpConfirmPassword;
    FirebaseDatabase database;
    DatabaseReference dbRefrence;
    ProgressBar progressSignUp;
    Button SingupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_keyboard_backspace_24);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Create An Account");

        database = FirebaseDatabase.getInstance();
        String url = "https://jofitcoach-95da4-default-rtdb.firebaseio.com/UserInfo";
        dbRefrence = database.getReferenceFromUrl(url);


        SignUpFullName = findViewById(R.id.SignUpFullName);
        progressSignUp = findViewById(R.id.progressSignUp);
        signUpEmail = findViewById(R.id.signUpEmail);
        singUpPassword = findViewById(R.id.singUpPassword);
        singUpConfirmPassword = findViewById(R.id.singUpConfirmPassword);
        SingupBtn  = findViewById(R.id.SingupBtn);

        SingupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressSignUp.setVisibility(View.VISIBLE);
                final String fullName = SignUpFullName.getText().toString();
                String pass = singUpPassword.getText().toString();
                String confirmPass = singUpConfirmPassword.getText().toString();
                final String email = signUpEmail.getText().toString();

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(SignUpActivity.this, "Password Doesn't match", Toast.LENGTH_LONG).show();
                    progressSignUp.setVisibility(View.GONE);
                }else if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Make sure to fill all fields", Toast.LENGTH_LONG).show();
                    progressSignUp.setVisibility(View.GONE);
                } else {
                    progressSignUp.setVisibility(View.VISIBLE);
//                    mRlFading.setVisibility(View.VISIBLE);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        UserGeneralInfo userGeneralInfo = new UserGeneralInfo(
                                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                                fullName,
                                                email);

                                        dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userGeneralInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SignUpActivity.this, "SignUp Complete", Toast.LENGTH_SHORT)
                                                        .show();
                                                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
                                                        .show();
                                                Log.e("TAG",e.getMessage());
                                            }
                                        });

//
//                                        Toast.makeText(SignUpActivity.this, "SignUp Complete", Toast.LENGTH_SHORT)
//                                                .show();
//                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
//                                        finish();
                                    } else {
                                        progressSignUp.setVisibility(View.GONE);
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG)
                                                .show();
                                        Log.e("TAG","Second: "+task.getException().getMessage());

                                    }

                                }
                            });
                }
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}