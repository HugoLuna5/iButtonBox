package lunainc.mx.com.ibuttonbox.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.Model.Group;
import lunainc.mx.com.ibuttonbox.Model.Member;
import lunainc.mx.com.ibuttonbox.R;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<Member> members;
    private LayoutInflater mInflater;
    private GroupAdapter.ItemClickListener mClickListener;

    public MemberAdapter(Context context, List<Member> members) {
        this.mInflater = LayoutInflater.from(context);
        this.members = members;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_members, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Member member = members.get(position);
        String FullName = member.getUser().getNombre() +" "+member.getUser().getApellidoP()+ " " +member.getUser().getApellidoM();

        holder.name.setText(FullName);

        if (member.getUser().getImage().equals("avatar.png")){
            String route = "http://192.168.1.74:8000/avatars/"+member.getUser().getUid()+"/avatar.png";

            Glide.with(holder.image.getContext()).load(route).into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        return members.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public @BindView(R.id.name)
        TextView name;

        public @BindView(R.id.image)
        CircleImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    public Member getItem(int id) {
        return members.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(GroupAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
