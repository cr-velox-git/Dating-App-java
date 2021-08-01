package com.silverphoenix.soca.fragment.reel;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverphoenix.soca.R;

import java.util.List;

public class ReelVideoAdapter extends RecyclerView.Adapter<ReelVideoAdapter.viewHolder> {

    private List<ReelVideoModel> reelVideoModelList;

    public ReelVideoAdapter(List<ReelVideoModel> reelVideoModelList) {
        this.reelVideoModelList = reelVideoModelList;
    }

    @NonNull
    @Override
    public ReelVideoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_reel_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReelVideoAdapter.viewHolder holder, int position) {
        holder.setData(reelVideoModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return reelVideoModelList.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {

        private TextView videoTitle, videoDes;
        private VideoView videoView;
        private ProgressBar videoProgressBar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.reelVideoView);
            videoProgressBar = itemView.findViewById(R.id.reelVideoProgressBar);

        }

        private void setData(ReelVideoModel reelVideoModel) {
            videoView.setVideoPath(reelVideoModel.getVideo());
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoProgressBar.setVisibility(View.GONE);
                    mp.start();
                    float videoRatio = (float)mp.getVideoWidth() / mp.getVideoHeight();
                    float screenRatio = (float)videoView.getWidth() / videoView.getHeight();
                    float scale = videoRatio / screenRatio;
                    if (scale >= 1f) {
                        videoView.setScaleX( scale);
                    } else {
                        videoView.setScaleY( (1f / scale));
                    }
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });
        }

    }
}
