package lunainc.mx.com.ibuttonbox.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.Connect.APIService;
import lunainc.mx.com.ibuttonbox.Connect.ApiUtils;
import lunainc.mx.com.ibuttonbox.Model.ResponseDefaultLR;
import lunainc.mx.com.ibuttonbox.Model.User;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Login.LoginActivity;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {


    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    public @BindView(R.id.image)
    CircleImageView image;
    public @BindView(R.id.nameUser)
    TextView nameUser;
    public @BindView(R.id.emailUser)
    TextView emailUser;
    public @BindView(R.id.btnActionClose)
    Button btnActionClose;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String uid_user;
    private String type_account;

    private APIService mAPIService;
    private SharedPreferences sharedPref;
    private String token =  "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ButterKnife.bind(this);

        initVars();
        configViews();
        loadData();
        events();

    }


    public void initVars(){
        auth = FirebaseAuth.getInstance();
        uid_user = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Context context = this.getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);

        mAPIService = ApiUtils.getAPIService();
        token = sharedPref.getString(("token"), "noLogged");
        type_account = sharedPref.getString(("type_account"), "noLogged");


    }

    public void configViews(){
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);

        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setTitle("Perfil");
        }

    }


    public void loadData(){
        String completeToken = "Bearer "+token;
        mAPIService.getDataUser("Accept", completeToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){


                    if (response.body().getStatus().equals("success")){

                        User user = response.body();

                        String fullName = user.getNombre()+" "+user.getApellidoP()+" "+user.getApellidoM();
                        nameUser.setText(fullName);
                        emailUser.setText(user.getCorreo());

                    }else{
                        TastyToast.makeText(PerfilActivity.this, response.body().getMessage() ,TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }


                }else{
                    TastyToast.makeText(PerfilActivity.this, "Ocurrio un error al conectarse al servidor",TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                TastyToast.makeText(PerfilActivity.this, "Ocurrio un error al conectarse al servidor",TastyToast.ERROR,TastyToast.LENGTH_LONG);

            }
        });


    }

    public void events(){

        btnActionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String completeToken = "Bearer "+token;
                mAPIService.logoutUser("Accept", completeToken).enqueue(new Callback<ResponseDefaultLR>() {
                    @Override
                    public void onResponse(Call<ResponseDefaultLR> call, Response<ResponseDefaultLR> response) {

                       if (response.isSuccessful()){


                           if (response.body().getStatus().equals("success")){

                               //TastyToast.makeText(PerfilActivity.this, response.body().getMessage(), TastyToast.SUCCESS, TastyToast.LENGTH_SHORT);
                               SharedPreferences.Editor editor = sharedPref.edit();
                               editor.clear();
                               editor.apply();

                               new Constants().goToNextActivity(PerfilActivity.this, new LoginActivity());

                           }else{
                               TastyToast.makeText(PerfilActivity.this, response.body().getMessage(), TastyToast.ERROR, TastyToast.LENGTH_SHORT);

                           }


                       }else{
                           TastyToast.makeText(PerfilActivity.this, "Ocurrio un error al conectar con el servidor", TastyToast.ERROR, TastyToast.LENGTH_SHORT);
                       }

                    }

                    @Override
                    public void onFailure(Call<ResponseDefaultLR> call, Throwable t) {

                    }
                });


            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // handle close button click here
            goTo();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goTo();
    }


    public void goTo(){
       if (type_account.equals("student")){
           new Constants().goToNextActivity(PerfilActivity.this, new StudentHomeActivity());
       }else{
           new Constants().goToNextActivity(PerfilActivity.this, new TeacherHomeActivity());
       }
    }


}
