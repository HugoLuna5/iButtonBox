package lunainc.mx.com.ibuttonbox.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdsmdg.tastytoast.TastyToast;
import com.victor.loading.newton.NewtonCradleLoading;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

public class LoginActivity extends AppCompatActivity {


    public @BindView(R.id.back)
    TextView backText;

    public @BindView(R.id.email)
    TextInputEditText emailField;

    public @BindView(R.id.password)
    TextInputEditText passwordField;

    public @BindView(R.id.newton_cradle_loading)
    NewtonCradleLoading loading;

    public @BindView(R.id.btnLogin)
    Button btnLogin;


    private FirebaseAuth auth;
    private FirebaseFirestore userData;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


        Context context = this.getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        loading.setVisibility(View.INVISIBLE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                loading.start();

                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();


                if (!email.isEmpty() && !password.isEmpty()){

                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            TastyToast.makeText(getApplicationContext(), "¡Bienvenido!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            loading.setVisibility(View.INVISIBLE);
                            loading.stop();
                            getData(authResult.getUser().getUid());

                        }
                    });


                }else{
                    TastyToast.makeText(getApplicationContext(), "¡Upps! Debes llenar todos los campos", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    loading.setVisibility(View.INVISIBLE);
                    loading.stop();
                }


            }
        });







        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToIntro();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToIntro();
    }




    public void getData(String user_uid){
        userData.collection("Users").document(user_uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String typeAccount = documentSnapshot.getString("type_account");
                        String email = documentSnapshot.getString("email");

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("type_account", typeAccount);
                        editor.putString("email", email);
                        editor.putString("user_uid", user_uid);
                        editor.apply();
                        new Constants().checkAndGoTo(LoginActivity.this, typeAccount);

                    }
                });
    }

    public void goToIntro(){

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        finish();


    }


}
