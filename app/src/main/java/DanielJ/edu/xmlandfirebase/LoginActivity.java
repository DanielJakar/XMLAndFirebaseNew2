package DanielJ.edu.xmlandfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    //properties:
    private FirebaseAuth mAuth;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

    }

    private String getEmail(){
        return etEmail.getText().toString();
    }

    private String getPassword(){
        return etPassword.getText().toString();
    }

    private boolean isEmailValid(){
        String email = getEmail();
        boolean isEmailValid = email.contains("@") && email.length() > 5;
//        Pattern emailAddress = Patterns.EMAIL_ADDRESS;
//        return emailAddress.matcher(email).matches();
        if (!isEmailValid)
            etEmail.setError("Invalid Email Address");
        else
            etEmail.setError(null);

        return isEmailValid;
    }

    private boolean isPasswordValid(){
        String password = getEmail();
        boolean isPasswordValid = password.length() > 5;

        if (!isPasswordValid)
            etEmail.setError("Password must contain at least 6 characters");
        else
            etEmail.setError(null);

        return isPasswordValid;
    }

    @OnClick(R.id.btnRegister)
    public void register() {
        //get the email
        String email = getEmail();
        //get the password
        String password = getPassword();
        //isEmail valid
        //isPassword valid
        if (!isEmailValid() | !isPasswordValid())
            return;
        showProgress(true);

//        Task<AuthResult> registerTask = mAuth.createUserWithEmailAndPassword(email, password);
//        registerTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//            }
//        });
//        registerTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//            }
//        });

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                gotoMain();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showError(e);
            }
        });



    }

    private void gotoMain() {
        showProgress(false);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private ProgressDialog dialog;
    //A progress dialog contains:
    //Title, Message, Icon AND a ProgressBar.
    private void showProgress (boolean show){
        //Lazy loading...Not initialized in onCreate/start
        if (dialog == null){
            dialog = new ProgressDialog(this);

            dialog.setMessage("Logging You In");
            dialog.setTitle("Connecting...");
        }

        if (show)
            dialog.show();
        else
            dialog.dismiss();
    }

    private void showError(Exception e) {
        showProgress(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        Snackbar.make(etEmail, e.getMessage(), Snackbar.LENGTH_INDEFINITE).
                setAction("OK", listener ).show();
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        //get the email
        String email = getEmail();
        //get the password
        String password = getPassword();
        //isEmail valid
        //isPassword valid
        if (!isEmailValid() | !isPasswordValid())
            return;
        FirebaseAuth.getInstance().signInWithEmailAndPassword(getEmail(), getPassword()).
                addOnSuccessListener( ).


    }

//    private void validateForm(){
//        //get the email
//        String email = getEmail();
//        //get the password
//        String password = getPassword();
//        //isEmail valid
//        //isPassword valid
//        if (!isEmailValid() | !isPasswordValid())
//            return;
//    }
}
