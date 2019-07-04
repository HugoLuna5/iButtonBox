package lunainc.mx.com.ibuttonbox.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sdsmdg.tastytoast.TastyToast;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.StudentHomeActivity;

public class RegisterActivity extends AppCompatActivity {


    public @BindView(R.id.back)
    TextView backText;

    public @BindView(R.id.name)
    TextInputEditText nameField;

    public @BindView(R.id.apellidoP)
    TextInputEditText apellidoPField;

    public @BindView(R.id.apellidoM)
    TextInputEditText apellidoMField;

    public @BindView(R.id.email)
    TextInputEditText emailField;

    public @BindView(R.id.numControl)
    TextInputEditText numControlField;

    public @BindView(R.id.password)
    TextInputEditText passwordField;

    public @BindView(R.id.confirmPass)
    TextInputEditText confirmPassField;

    public @BindView(R.id.newton_cradle_loading)
    NewtonCradleLoading loading;

    public @BindView(R.id.btnRegister)
    Button btnRegister;



    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);



        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToIntro();
            }
        });

        loading.setVisibility(View.INVISIBLE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                loading.start();

                String name = nameField.getText().toString();
                String apellidoP = apellidoPField.getText().toString();
                String apellidoM = apellidoMField.getText().toString();
                String email = emailField.getText().toString();
                String numControl = numControlField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPassField.getText().toString();


                /**
                 * Validar que los datos no esten vacios
                 * y que las contraseñas coincidan
                 */
                if ( (!name.isEmpty() && !apellidoP.isEmpty() && !apellidoM.isEmpty() && !email.isEmpty() && !numControl.isEmpty()
                        && !password.isEmpty() && !confirmPassword.isEmpty()) && password.equals(confirmPassword) ){



                    auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {



                            Map<String, Object> user = new HashMap<>();
                            user.put("uid", authResult.getUser().getUid());
                            user.put("name",name);
                            user.put("apellidoP", apellidoP);
                            user.put("apellidoM", apellidoM);
                            user.put("email", email);
                            user.put("numControl", numControl);
                            user.put("image", "default");
                            user.put("type_account", "student");
                            user.put("thumb_image", "default");
                            user.put("device_token", FirebaseInstanceId.getInstance().getToken());
                            user.put("created_at", System.currentTimeMillis());


                            firebaseFirestore.collection("Users").document(authResult.getUser().getUid()).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            TastyToast.makeText(getApplicationContext(), "¡Se ha creado tu cuenta con exito!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            goToHome();
                                            loading.setVisibility(View.INVISIBLE);
                                            loading.stop();
                                        }
                            });




                        }
                    });




                }else {
                    TastyToast.makeText(getApplicationContext(), "¡Upps! Debes llenar todos los campos", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    loading.stop();
                    loading.setVisibility(View.INVISIBLE);
                }


            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToIntro();
    }


    public void goToHome(){
        Intent intent = new Intent(this, StudentHomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToIntro(){

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        finish();


    }
}
