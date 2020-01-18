package lunainc.mx.com.ibuttonbox.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Model.Group;
import lunainc.mx.com.ibuttonbox.R;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> groups;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public GroupAdapter(Context context, List<Group> groups) {
        this.mInflater = LayoutInflater.from(context);
        this.groups = groups;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = groups.get(position);
        //holder.myTextView.setText(animal);

        holder.name.setText(group.getName());
        holder.groupName.setText(group.getDesc());
        String color = "#"+group.getColor();
        Log.e("Color", color);
        holder.statusView.setBackgroundColor(Color.parseColor(color));
        holder.time.setText(group.getCreated_at());


    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public @BindView(R.id.statusView)
        View statusView;

        public @BindView(R.id.cardContainer)
        CardView cardContainer;

        public @BindView(R.id.name)
        TextView name;

        public @BindView(R.id.groupName)
        TextView groupName;

        public @BindView(R.id.time)
        TextView time;

        public @BindView(R.id.numMembers)
        TextView numMembers;

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
    public Group getItem(int id) {
        return groups.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
