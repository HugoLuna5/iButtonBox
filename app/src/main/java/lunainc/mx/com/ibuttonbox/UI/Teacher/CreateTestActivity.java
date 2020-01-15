package lunainc.mx.com.ibuttonbox.UI.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Model.Group;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

public class CreateTestActivity extends AppCompatActivity {


    public @BindView(R.id.spinner)
    MaterialSpinner spinner;

    public @BindView(R.id.grupo)
    MaterialSpinner grupo;

    public @BindView(R.id.nameTest)
    TextInputEditText nameTest;

    public @BindView(R.id.descTest)
    TextInputEditText descTest;

    public @BindView(R.id.btnAction)
    Button btnAction;

    private String name;
    private String desc;
    private String type;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore firestoreGroup;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private String uid_user;
    private List<String> groups = new ArrayList<String>();
    private List<Group> groupsC = new ArrayList<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        ButterKnife.bind(this);
        
        setValuesSpinner();
        initVars();
        events();
        setListGroups();
        
    }

    public void initVars(){
        progressDialog= new ProgressDialog(CreateTestActivity.this);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        uid_user = auth.getCurrentUser().getUid();

        progressDialog.setMessage("Espere un momento...");
        progressDialog.setTitle("Obteniendo datos");
        progressDialog.show();
    }


    private void events() {
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setValuesSpinner() {
        spinner.setItems("Verdadero/ Falso", "Opcion multiple");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    public void setListGroups(){
        Query query = firebaseFirestore.collection("Groups").whereEqualTo("uid_creator", uid_user);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot document : task.getResult()){

                            groups.add(document.getString("name"));
                            Group group = new Group();
                            group.setName(document.getString("name"));
                            group.setUid(document.getString("uid"));
                            group.setUid_creator(document.getString("uid_creator"));
                            groupsC.add(group);


                    }
                    grupo.setItems(groups);
                    progressDialog.dismiss();


                }
            }
        });




    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new Constants().goToNextActivity(CreateTestActivity.this, new TeacherHomeActivity());
    }
}
