package com.silverphoenix.soca.fragment.match;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.silverphoenix.soca.R;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.viewHolder> {

    private List<CardStackModel> cardStackModelList;

    public CardStackAdapter(List<CardStackModel> cardStackModelList) {
        this.cardStackModelList = cardStackModelList;
    }

    public List<CardStackModel> getCardStackModelList() {
        return cardStackModelList;
    }

    public void setCardStackModelList(List<CardStackModel> cardStackModelList) {
        this.cardStackModelList = cardStackModelList;
    }

    @NonNull
    @Override
    public CardStackAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_matching, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardStackAdapter.viewHolder holder, int position) {
        holder.setData(cardStackModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardStackModelList.size();
    }


    static class viewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView name, age, city;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            city = itemView.findViewById(R.id.item_city);
        }

        void setData(CardStackModel cardStackModel) {
            Glide.with(itemView.getContext()).load(cardStackModel.getImage()).placeholder(R.drawable.gg).into(imageView);
            name.setText(cardStackModel.getName());
            age.setText(cardStackModel.getAge());
            city.setText(cardStackModel.getCity());
        }
    }

}
