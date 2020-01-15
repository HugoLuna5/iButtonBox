package lunainc.mx.com.ibuttonbox.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import lunainc.mx.com.ibuttonbox.Connect.APIService;
import lunainc.mx.com.ibuttonbox.Connect.ApiUtils;
import lunainc.mx.com.ibuttonbox.Model.ResponseDefaultLR;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public @BindView(R.id.phone)
    TextInputEditText phoneField;

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
    private SharedPreferences sharedPref;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        Context context = this.getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        mAPIService = ApiUtils.getAPIService();
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
                String phone = phoneField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPassField.getText().toString();


                /**
                 * Validar que los datos no esten vacios
                 * y que las contraseñas coincidan
                 */
                if ( (!name.isEmpty() && !apellidoP.isEmpty() && !apellidoM.isEmpty() && !email.isEmpty()
                        && !password.isEmpty() && !confirmPassword.isEmpty() && !phone.isEmpty()) && password.equals(confirmPassword) ){

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id_type", "1")
                            .addFormDataPart("name", name)
                            .addFormDataPart("last_name_p", apellidoP)
                            .addFormDataPart("last_name_m", apellidoM)
                            .addFormDataPart("phone", phone)
                            .addFormDataPart("device_token", FirebaseInstanceId.getInstance().getToken())
                            .addFormDataPart("email", email)
                            .addFormDataPart("password", password)
                            .build();



                    mAPIService.registerUser(requestBody).enqueue(new Callback<ResponseDefaultLR>() {
                        @Override
                        public void onResponse(Call<ResponseDefaultLR> call, Response<ResponseDefaultLR> response) {
                            if (response.isSuccessful()){

                                if (response.body().getStatus().equals("success")){

                                    TastyToast.makeText(getApplicationContext(), "¡Se ha creado tu cuenta con exito!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("type_account", "student");
                                    editor.putString("email", email);
                                    editor.putString("token", response.body().getToken());
                                    editor.apply();
                                    loading.setVisibility(View.INVISIBLE);
                                    loading.stop();
                                    new Constants().checkAndGoTo(RegisterActivity.this, "student");


                                }else{
                                    TastyToast.makeText(RegisterActivity.this, response.body().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                }


                            }else{
                                TastyToast.makeText(RegisterActivity.this, "Oucrrio un error", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDefaultLR> call, Throwable t) {
                            TastyToast.makeText(RegisterActivity.this, "Oucrrio un error :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
        //super.onBackPressed();
        goToIntro();
    }


    public void goToIntro(){

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        finish();


    }
}
