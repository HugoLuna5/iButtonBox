package lunainc.mx.com.ibuttonbox.UI.Fragment.Student;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Holder.GroupHolder;
import lunainc.mx.com.ibuttonbox.Model.Group;
import lunainc.mx.com.ibuttonbox.Model.Member;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Groups.CreateGroupActivity;
import lunainc.mx.com.ibuttonbox.UI.Groups.GroupActivity;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;

public class GroupsStudentFragment extends Fragment {



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



        return view;
    }


    public void initVars(){

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        uid_user = auth.getCurrentUser().getUid();


        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnAction.setVisibility(View.GONE);
    }




    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData(){
        Query query = firebaseFirestore.collection("Members").whereEqualTo("uid_user", uid_user)
                .orderBy("join_at", Query.Direction.DESCENDING).limit(50);

        FirestoreRecyclerOptions<Member> recyclerOptions = new FirestoreRecyclerOptions.Builder<Member>().
                setQuery(query, Member.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Member, GroupHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(final GroupHolder holder, int i, Member member) {


             firebaseFirestore.collection("Groups").document(member.getUid_group())
                     .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {


                     Group group = new Group();
                     group.setName(documentSnapshot.getString("name"));
                     group.setDesc(documentSnapshot.getString("desc"));
                     group.setColor(documentSnapshot.getString("color"));
                     group.setUid(documentSnapshot.getString("uid"));
                     //group.setCreated_at(documentSnapshot.getLong("created_at"));


                     holder.name.setText(group.getName());
                     holder.groupName.setText(group.getDesc());
                     String color = "#"+group.getColor();
                     Log.e("Color", color);
                     holder.statusView.setBackgroundColor(Color.parseColor(color));

                     firebaseFirestore.collection("Members").whereEqualTo("uid_group", group.getUid())
                             .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                         @Override
                         public void onSuccess(QuerySnapshot snapshots) {
                             int members = snapshots.getDocumentChanges().size();
                             holder.numMembers.setText(members+" miembro(s)");
                         }
                     });



                     GetTimeAgo getTimeAgo = new GetTimeAgo();

                     //String lastSeenTime = getTimeAgo.getTimeAgo(group.getCreated_at(), getActivity());

                     //if (lastSeenTime != null) {
                     //    holder.time.setText(lastSeenTime);
                     //} else {
                         holder.time.setText("Hace un momento ");
                   //  }


                     holder.itemView.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             Intent intent = new Intent(getActivity(), GroupActivity.class);
                             intent.putExtra("uid_group", group.getUid());
                             getActivity().startActivity(intent);

                         }
                     });

                 }
             });


            }


            @Override
            public GroupHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item_group, group, false);

                return new GroupHolder(view);
            }
        };


        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }


}
