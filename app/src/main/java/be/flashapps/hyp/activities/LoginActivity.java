package be.flashapps.hyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.orhanobut.logger.Logger;

import java.util.List;

import be.flashapps.hyp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @OnClick(R.id.btn_log_in)
    public void onclick(){
        loginValidator.validate();
    }

    @OnClick(R.id.btn_register)
    public void register(){
        registerValidator.validate();
    }

    @NotEmpty
    @Password
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;

    @NotEmpty
    @BindView(R.id.et_username)
    AppCompatEditText etUsername;


    Validator loginValidator,registerValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = getFireBaseAuthInstance() ;

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                    Logger.d("onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Logger.d("onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

         loginValidator =new Validator(this);
        loginValidator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                login();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                validationFailed(errors);
            }
        });

        registerValidator =new Validator(this);
        registerValidator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                registerUser();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                validationFailed(errors);
            }
        });

    }

    public void validationFailed(List<ValidationError> errors){
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void login() {
        String email=etUsername.getText().toString(),password=etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Logger.d("signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Logger.d("signInWithEmail:failed"+ task.getException());
                            Toast.makeText(LoginActivity.this, "authentication failed login",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }

                        // ...
                    }
                });

    }


    public void registerUser(){
        String email=etUsername.getText().toString(),password=etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       Snackbar.make(findViewById(android.R.id.content), "createUserWithEmail:onComplete:" + task.isSuccessful(),Snackbar.LENGTH_LONG).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "authentication has failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }

                        // ...
                    }
                });
    }

}
