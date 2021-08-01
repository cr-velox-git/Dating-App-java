package com.silverphoenix.soca.fragment.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverphoenix.soca.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {

    private List<PostModel> postModelList;
    private Context context;

    public PostAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public PostAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.viewHolder holder, int position) {
        holder.setData(context, postModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {

        private final ImageView postImage;
        private final TextView postText;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_item_image);
            postText = itemView.findViewById(R.id.post_item_text);

        }

        private void setData(Context context, PostModel postModel) {
            postImage.setImageResource(postModel.getImage());
            postText.setText(postModel.getName());

            itemView.setOnClickListener (v->{
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            });
        }

    }

}
