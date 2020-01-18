package lunainc.mx.com.ibuttonbox.UI.Groups;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sdsmdg.tastytoast.TastyToast;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.Connect.APIService;
import lunainc.mx.com.ibuttonbox.Connect.ApiUtils;
import lunainc.mx.com.ibuttonbox.Model.ResponseDefaultLR;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.PerfilActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity implements SpectrumPalette.OnColorSelectedListener {



    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    public @BindView(R.id.nameGroup)
    TextInputEditText nameGroup;
    public @BindView(R.id.descGroup)
    TextInputEditText descGroup;
    public @BindView(R.id.palette)
    SpectrumPalette palette;
    public @BindView(R.id.btnAction)
    Button btnAction;
    public @BindView(R.id.imageToolbar)
    CircleImageView imageToolbar;

    private ProgressDialog mProgrees;
    public String colorFin = "";

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String uid_user;

    private APIService mAPIService;
    private SharedPreferences sharedPref;
    private String token =  "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        ButterKnife.bind(this);

        initVars();
        configViews();
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

    }

    public void configViews(){
        toolbar.setTitle("Crear grupo");
        setSupportActionBar(toolbar);

        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setTitle("Crear grupo");
        }

        palette.setOnColorSelectedListener(this);

        mProgrees =  new ProgressDialog(this);


    }

    public void events(){
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = nameGroup.getText().toString();
                String desc = descGroup.getText().toString();


                if (!name.isEmpty() && !desc.isEmpty() && !colorFin.isEmpty()){
                    mProgrees.setMessage("Creando...");
                    mProgrees.setCanceledOnTouchOutside(false);
                    mProgrees.show();
                    String key = firebaseFirestore.collection("Groups").document().getId().toString();


                    String completeToken = "Bearer "+token;

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("color", colorFin)
                            .addFormDataPart("name", name)
                            .addFormDataPart("desc", desc)
                            .addFormDataPart("status", "1")
                            .addFormDataPart("code", key.substring(0,6))
                            .build();


                    mAPIService.createGroup("Accept", completeToken, requestBody).enqueue(new Callback<ResponseDefaultLR>() {
                        @Override
                        public void onResponse(Call<ResponseDefaultLR> call, Response<ResponseDefaultLR> response) {

                            if (response.isSuccessful()){

                                if (response.body().getStatus().equals("success")){
                                    TastyToast.makeText(CreateGroupActivity.this,  response.body().getMessage(),TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                    mProgrees.dismiss();
                                    goTo();
                                }else{
                                    TastyToast.makeText(CreateGroupActivity.this,  response.body().getMessage(),TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                }

                            }else{
                                TastyToast.makeText(CreateGroupActivity.this, "Ocurrio un error al conectarse al servidor",TastyToast.LENGTH_LONG, TastyToast.ERROR);

                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseDefaultLR> call, Throwable t) {
                            TastyToast.makeText(CreateGroupActivity.this, "Ocurrio un error al conectarse al servidor",TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        }
                    });

                }

            }
        });


        imageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Constants().goToNextActivity(CreateGroupActivity.this, new PerfilActivity());
            }
        });
    }

    @Override
    public void onColorSelected(int color) {
        colorFin = Integer.toHexString(color).toUpperCase();
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
        new Constants().goToNextActivity(CreateGroupActivity.this, new TeacherHomeActivity());
    }
}
