package com.silverphoenix.soca.signUpLogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverphoenix.soca.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.Viewholder> {

    private final List<QuestionModel> questionModelList;

    public QuestionAdapter(List<QuestionModel> questionModelList) {
        this.questionModelList = questionModelList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.setData(questionModelList.get(position));
    }


    @Override
    public int getItemCount() {
        return questionModelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        private final TextView question;
        private final CheckBox yesBox;
        private final CheckBox noBox;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.q_question);
            yesBox = itemView.findViewById(R.id.q_yes);
            noBox = itemView.findViewById(R.id.q_no);


        }

        private void setData(QuestionModel questionModel) {

            question.setText(questionModel.getQuestion());

            yesBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    yesBox.setChecked(true);
                    questionModel.setRespond(QuestionModel.Q_YES);
                } else {
                    yesBox.setChecked(false);
                }
                noBox.setChecked(false);
            });


            noBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    noBox.setChecked(true);
                    questionModel.setRespond(QuestionModel.Q_NO);
                } else {
                    noBox.setChecked(false);
                }
                yesBox.setChecked(false);
            });
        }
    }
}
