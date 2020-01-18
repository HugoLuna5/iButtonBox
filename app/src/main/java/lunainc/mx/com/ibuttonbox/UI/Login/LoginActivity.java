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
import lunainc.mx.com.ibuttonbox.Connect.APIService;
import lunainc.mx.com.ibuttonbox.Connect.ApiUtils;
import lunainc.mx.com.ibuttonbox.Model.ResponseDefaultLR;
import lunainc.mx.com.ibuttonbox.Model.User;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private APIService mAPIService;
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

        mAPIService = ApiUtils.getAPIService();

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


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("email", email)
                            .addFormDataPart("password", password)
                            .build();


                    mAPIService.loginUser(requestBody).enqueue(new Callback<ResponseDefaultLR>() {
                        @Override
                        public void onResponse(Call<ResponseDefaultLR> call, Response<ResponseDefaultLR> response) {
                            if (response.isSuccessful()){


                                if (response.body().getStatus().equals("success")){
                                    TastyToast.makeText(getApplicationContext(), "¡Bienvenido!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                    loading.setVisibility(View.INVISIBLE);
                                    loading.stop();
                                    getData(response.body().getToken());
                                }else{
                                    TastyToast.makeText(LoginActivity.this, response.body().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                }

                            }else{
                                TastyToast.makeText(LoginActivity.this, "Oucrrio un error", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                loading.setVisibility(View.INVISIBLE);
                                loading.stop();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseDefaultLR> call, Throwable t) {
                            TastyToast.makeText(LoginActivity.this, "Oucrrio un error", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            loading.setVisibility(View.INVISIBLE);
                            loading.stop();
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




    public void getData(String token){
        String completeToken = "Bearer "+token;
        mAPIService.getDataUser("Accept", completeToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){

                    String type = response.body().getType_account();
                    String typeAcc = "";
                    if (Integer.parseInt(type) == 1){
                        typeAcc = "student";
                    }else {
                        typeAcc = "teacher";
                    }

                    String email = response.body().getCorreo();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("type_account", typeAcc);
                    editor.putString("email", email);
                    editor.putString("token", token);
                    editor.apply();

                    new Constants().checkAndGoTo(LoginActivity.this, typeAcc);

                }else{
                    TastyToast.makeText(LoginActivity.this, response.body().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void goToIntro(){

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        finish();


    }


}
