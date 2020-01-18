package lunainc.mx.com.ibuttonbox.UI.Fragment.Teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import lunainc.mx.com.ibuttonbox.Model.Test;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Teacher.CreateTestActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;

public class HomeTeacherFragment extends Fragment {


    private View view;
    public @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public @BindView(R.id.btnAction)
    FloatingActionButton btnAction;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore firestoreGroup;

    private FirebaseAuth auth;
    private String uid_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);

        ButterKnife.bind(this, view);

        initVars();
        events();

        return view;
    }

    public void initVars(){

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        uid_user = auth.getCurrentUser().getUid();


        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        //linearLayoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public void events(){
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Crear nuevo test", Toast.LENGTH_SHORT).show();
                new Constants().goToNextActivity(getActivity(), new CreateTestActivity());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
       // loadData();
    }

    public void loadData(){
        Query query = firebaseFirestore.collection("Test").whereEqualTo("uid_creator", uid_user)
                .orderBy("time", Query.Direction.DESCENDING).limit(50);

        FirestoreRecyclerOptions<Test> recyclerOptions = new FirestoreRecyclerOptions.Builder<Test>().
                setQuery(query, Test.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Test, TestHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(final TestHolder holder, int i, Test test) {


                holder.statusView.setBackgroundColor(getResources().getColor(R.color.blue));
                holder.name.setText(test.getTitle());

                firestoreGroup.collection("Groups").document(test.getUid_group())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        holder.groupName.setText(documentSnapshot.get("name").toString());

                    }
                });

                GetTimeAgo getTimeAgo = new GetTimeAgo();

                String lastSeenTime = getTimeAgo.getTimeAgo(test.getTime(), getActivity());

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
