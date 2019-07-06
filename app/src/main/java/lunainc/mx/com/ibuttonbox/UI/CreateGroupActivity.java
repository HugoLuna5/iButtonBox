package lunainc.mx.com.ibuttonbox.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

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

    }

    public void configViews(){
        toolbar.setTitle("Crear grupo");
        setSupportActionBar(toolbar);

        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setTitle("PUBLICAR");
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

                    Map<String, Object> group = new HashMap<>();
                    group.put("color", colorFin);
                    group.put("created_at", System.currentTimeMillis());
                    group.put("desc", desc);
                    group.put("name", name);
                    group.put("status", true);
                    group.put("uid", key);
                    group.put("code", key.substring(0,6));
                    group.put("uid_creator", uid_user);


                    firebaseFirestore.collection("Groups").document(key).set(group)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgrees.dismiss();
                                    new Constants().goToNextActivity(CreateGroupActivity.this, new TeacherHomeActivity());
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
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
