package lunainc.mx.com.ibuttonbox.UI.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Adapter.GroupAdapter;
import lunainc.mx.com.ibuttonbox.Adapter.MemberAdapter;
import lunainc.mx.com.ibuttonbox.Connect.APIService;
import lunainc.mx.com.ibuttonbox.Connect.ApiUtils;
import lunainc.mx.com.ibuttonbox.Holder.MemberHolder;
import lunainc.mx.com.ibuttonbox.Holder.TestHolder;
import lunainc.mx.com.ibuttonbox.Model.Group;
import lunainc.mx.com.ibuttonbox.Model.Member;
import lunainc.mx.com.ibuttonbox.Model.Test;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiembrosFragment extends Fragment {

    private View view;
    public @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public @BindView(R.id.btnAction)
    FloatingActionButton btnAction;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String uid_user;

    private APIService mAPIService;
    private SharedPreferences sharedPref;
    private String token =  "";
    private MemberAdapter memberAdapter;
    private ArrayList<Member> members;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static MiembrosFragment newInstance(String parameter) {

        Bundle args = new Bundle();
        args.putString("parameter", parameter);
        MiembrosFragment fragment = new MiembrosFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);

        ButterKnife.bind(this, view);

        initVars();
        loadData();

        return view;
    }


    public void initVars() {

        auth = FirebaseAuth.getInstance();
        uid_user = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Context context = getActivity().getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);

        mAPIService = ApiUtils.getAPIService();
        token = sharedPref.getString(("token"), "noLogged");

        members = loadData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        //linearLayoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        memberAdapter = new MemberAdapter(getActivity(), members);
        memberAdapter.notifyDataSetChanged();
        //memberAdapter.setClickListener(getActivity());


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i("tag", "This'll run 800 milliseconds later");
                        recyclerView.setAdapter(memberAdapter);
                    }
                },
                800);

        btnAction.setVisibility(View.GONE);
    }


    public ArrayList<Member> loadData() {
        ArrayList<Member> members = new ArrayList<Member>();
        String completeToken = "Bearer "+token;
        String data = getArguments().getString("parameter");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_group", data)

                .build();

        mAPIService.membersGroups("Accept", completeToken, requestBody).enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {

                if (response.isSuccessful()){

                    if(response.body().getStatus().equals("success")){

                        if (response.body().getMembers().size() > 0){

                            for (int i = 0; i < response.body().getMembers().size(); i++) {

                                Member member = response.body().getMembers().get(i);
                                members.add(member);


                            }

                        }

                    }else{
                        TastyToast.makeText(getActivity(), response.body().getMessage(),TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                }else {
                    TastyToast.makeText(getActivity(), "Ocurrio un error al conectarse al servidor",TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                TastyToast.makeText(getActivity(), t.getMessage(),TastyToast.LENGTH_LONG, TastyToast.ERROR);

            }
        });

        return members;
    }


}
