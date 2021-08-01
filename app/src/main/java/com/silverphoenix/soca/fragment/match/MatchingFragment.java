package com.silverphoenix.soca.fragment.match;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silverphoenix.soca.R;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;


public class MatchingFragment extends Fragment {


    private ImageView refreshImg, dislikeImg, superLikeImg, likeImg, flashImg;
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private CardStackAdapter cardStackAdapter;
    private String TAG = MatchingFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matching, container, false);

        refreshImg = view.findViewById(R.id.fm_refresh);
        dislikeImg = view.findViewById(R.id.fm_dislike);
        superLikeImg = view.findViewById(R.id.fm_super_like);
        likeImg = view.findViewById(R.id.fm_like);
        flashImg = view.findViewById(R.id.fm_flash);

        cardStackView = view.findViewById(R.id.match_card_stack_view);

        /*.......................... Card Stack Model List ............................*/


        /*.......................... Card Stack Model List ............................*/


        cardStackLayoutManager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name().toString() + " ratio=" + ratio);
//
                if (direction == Direction.Right) {
                    // likeImg?.setColorFilter(null)
                    likeImg.setImageResource(R.drawable.i_heart);
                    dislikeImg.setImageResource(R.drawable.i_o_x_mark);
                    superLikeImg.setImageResource(R.drawable.i_o_fire);
                    //like.setBackgroundColor(R.color.design_default_color_primary_variant)
                    //dislike.setBackgroundColor(R.color.teal_200)
                } else if (direction == Direction.Left) {
                    dislikeImg.setImageResource(R.drawable.i_remove);
                    likeImg.setImageResource(R.drawable.i_o_heart);
                    superLikeImg.setImageResource(R.drawable.i_o_fire);
                    // dislike.setBackgroundColor(R.color.design_default_color_primary_variant)
                    //like.setBackgroundColor(R.color.teal_200)
                } else if (direction == Direction.Top) {
                    dislikeImg.setImageResource(R.drawable.i_o_x_mark);
                    superLikeImg.setImageResource(R.drawable.i_fire);
                    likeImg.setImageResource(R.drawable.i_o_heart);
                } else if (direction == Direction.Bottom) {

                } else {
                    dislikeImg.setImageResource(R.drawable.i_o_x_mark);
                    superLikeImg.setImageResource(R.drawable.i_o_fire);
                    likeImg.setImageResource(R.drawable.i_o_heart);
                }
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + cardStackLayoutManager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right) {
                    Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top) {
                    Toast.makeText(getContext(), "Direction Top", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left) {

                    Toast.makeText(getContext(), "Direction Left", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom) {
                    Toast.makeText(getContext(), "Direction Bottom", Toast.LENGTH_SHORT).show();
                }

                // Paginating
                if (cardStackLayoutManager.getTopPosition() == cardStackAdapter.getItemCount() - 5) {
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound_1: " + cardStackLayoutManager.getTopPosition());

            }

            @Override
            public void onCardCanceled() {
                dislikeImg.setImageResource(R.drawable.i_o_x_mark);
                superLikeImg.setImageResource(R.drawable.i_o_fire);
                likeImg.setImageResource(R.drawable.i_o_heart);
                Log.d(TAG, "onCardRewound: " + cardStackLayoutManager.getTopPosition());

            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                dislikeImg.setImageResource(R.drawable.i_o_x_mark);
                superLikeImg.setImageResource(R.drawable.i_o_fire);
                likeImg.setImageResource(R.drawable.i_o_heart);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText().toString());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                dislikeImg.setImageResource(R.drawable.i_o_x_mark);
                superLikeImg.setImageResource(R.drawable.i_o_fire);
                likeImg.setImageResource(R.drawable.i_o_heart);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText().toString());
            }
        });

        cardStackLayoutManager.setStackFrom(StackFrom.None);
        cardStackLayoutManager.setVisibleCount(3);
        cardStackLayoutManager.setTranslationInterval(8.0f);
        cardStackLayoutManager.setScaleInterval(0.95f);
        cardStackLayoutManager.setSwipeThreshold(0.3f);
        cardStackLayoutManager.setMaxDegree(20.0f);
        cardStackLayoutManager.setDirections(Direction.FREEDOM);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Manual);
        cardStackLayoutManager.setOverlayInterpolator(new LinearInterpolator());
        cardStackAdapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.setAdapter(cardStackAdapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    private void paginate() {
        List<CardStackModel> oldList = cardStackAdapter.getCardStackModelList();
        List<CardStackModel> newList = new ArrayList(addList());
        CardStackCallback callback = new CardStackCallback(newList, oldList);
        cardStackAdapter.setCardStackModelList(newList);
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(cardStackAdapter);
    }

    private List<CardStackModel> addList() {
        List<CardStackModel> cardStackModelList = new ArrayList<>();

        cardStackModelList.add(new CardStackModel( "https://www.w3schools.com/css/img_mountains.jpg", "Markonah", "24", "Jember"));
        cardStackModelList.add(new CardStackModel( "https://www.w3schools.com/css/img_mountains.jpg", "Markonah", "24", "Jember"));
        cardStackModelList.add(new CardStackModel( "https://www.w3schools.com/css/img_mountains.jpg", "Markonah", "24", "Jember"));
        cardStackModelList.add(new CardStackModel( "https://www.w3schools.com/css/img_mountains.jpg", "Markonah", "24", "Jember"));
        cardStackModelList.add(new CardStackModel( "https://www.w3schools.com/css/img_mountains.jpg", "Markonah", "24", "Jember"));

        return cardStackModelList;
    }

    ;

}