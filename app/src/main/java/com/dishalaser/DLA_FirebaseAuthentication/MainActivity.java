package com.dishalaser.DLA_FirebaseAuthentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 7171;

    List<AuthUI.IdpConfig> providers;
    Button btnSignout;
    Button btnPrevCases;
    Button btnNewCase;
    Button btnChat;
    Button btnFAQ;
    TextView tvUserName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignout = findViewById(R.id.btn_signout);
        tvUserName = findViewById(R.id.tv_user_name);
        btnPrevCases = findViewById(R.id.btn_previous_cases);
        btnNewCase = findViewById(R.id.btn_new_cases);
        btnChat = findViewById(R.id.btn_chatbox);
        btnFAQ = findViewById(R.id.btn_faqs);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );
        showSignInOptions();

        btnSignout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btnSignout.setEnabled(false);
                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                }
        });
        btnNewCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, newCaseForm.class));
//                setContentView(R.layout.new_case_form);
            }
        });
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyTheme)
                .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE)
        {
            if(data!=null) {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (resultCode == RESULT_OK) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.getDisplayName()!=null)
                        tvUserName.setText(user.getDisplayName());
                    Toast.makeText(MainActivity.this, "" + user.getEmail(), Toast.LENGTH_LONG).show();
                    btnSignout.setEnabled(true);
                } else {
                    Toast.makeText(MainActivity.this, "" + response.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            else{
                finish();
            }
        }
    }
}
