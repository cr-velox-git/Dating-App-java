package com.silverphoenix.soca.fragment.reel;

public class ReelVideoModel {

    private String video, title, des;

    public ReelVideoModel(String video, String title, String des) {
        this.video = video;
        this.title = title;
        this.des = des;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
