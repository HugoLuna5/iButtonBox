package lunainc.mx.com.ibuttonbox.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Holder.TestHolder;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;
import lunainc.mx.com.ibuttonbox.Model.Test;

public class StudentHomeActivity extends AppCompatActivity  {


    public @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    
    public @BindView(R.id.btnJoinGroup)
    FloatingActionButton btnJoinGroup;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore userData;
    private FirebaseFirestore firestoreGroup;
    private FirebaseAuth auth;
    private String user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        ButterKnife.bind(this);

        initVars();
        events();
        loadDataUser();
        loadData();

    }

   


    public void initVars(){

        auth = FirebaseAuth.getInstance();
        user_uid = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        userData = FirebaseFirestore.getInstance();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }



    private void events() {
        
        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(StudentHomeActivity.this, "Btn join group", Toast.LENGTH_SHORT).show();
                
                
            }
        });
    }



    public void loadDataUser(){

        userData.collection("Users").document(user_uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String typeAccount = documentSnapshot.getString("type_account");



                           if (typeAccount.equals("teacher")){

                                new Constants().goToNextActivity(StudentHomeActivity.this, new TeacherHomeActivity());

                           }else if(typeAccount.equals("admin")){
                               //new Constants().goToNextActivity(StudentHomeActivity.this, new TeacherHomeActivity());

                           }else{

                           }


                    }
                });

    }


    private void loadData(){


        Query query = firebaseFirestore.collection("Test").orderBy("time", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Test> recyclerOptions = new FirestoreRecyclerOptions.Builder<Test>().
                setQuery(query, Test.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Test, TestHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(final TestHolder holder, int i, Test test) {


                holder.name.setText(test.getTitle());

                firestoreGroup.collection("Groups").document(test.getUid_group())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        holder.groupName.setText(documentSnapshot.get("name").toString());

                    }
                });

                GetTimeAgo getTimeAgo = new GetTimeAgo();

                String lastSeenTime = getTimeAgo.getTimeAgo(test.getTime(), StudentHomeActivity.this);

                if (lastSeenTime != null) {
                    holder.time.setText(lastSeenTime);
                } else {
                    holder.time.setText("Hace un momento ");
                }





            }

            @NonNull
            @Override
            public TestHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item, group, false);

                return new TestHolder(view);
            }
        };


        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }




}
