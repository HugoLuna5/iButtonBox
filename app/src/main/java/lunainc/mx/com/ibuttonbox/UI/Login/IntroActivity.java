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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

public class IntroActivity extends AppCompatActivity {


    public @BindView(R.id.join_us)
    Button joinBtn;

    public @BindView(R.id.login)
    TextView login;

    private FirebaseAuth auth;
    private FirebaseFirestore userData;
    private String user_uid;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        Context context = this.getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);
        userData = FirebaseFirestore.getInstance();
        validateSession();

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });


    }


    public void validateSession() {

        if (auth.getCurrentUser() != null) {
            user_uid = auth.getCurrentUser().getUid();


            String typeAcc = sharedPref.getString(("type_account"), "noLogged");

            if (!typeAcc.equals("noLogged")){
                new Constants().checkAndGoTo(IntroActivity.this, typeAcc);
            }


        }

    }


    public void goToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
