package lunainc.mx.com.ibuttonbox.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Login.LoginActivity;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

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

        firebaseFirestore.collection("Users").document(uid_user).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String fullName = documentSnapshot.getString("name") + documentSnapshot.getString("apellidoP") + documentSnapshot.getString("apellidoM");

                        type_account = documentSnapshot.getString("type_account");
                        nameUser.setText(fullName);
                        emailUser.setText(documentSnapshot.getString("email"));

                    }
                });


    }

    public void events(){

        btnActionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signOut();
                new Constants().goToNextActivity(PerfilActivity.this, new LoginActivity());

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
