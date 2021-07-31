package com.silverphoenix.soca.signUpLogin;


public class QuestionModel {
    public static final int  Q_DEFAULT = -1;
    public static final int  Q_YES = 1;
    public static final int  Q_NO = 0;

    private String question;
    private int respond;

    public QuestionModel(String question, int respond) {
        this.question = question;
        this.respond = respond;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getRespond() {
        return respond;
    }

    public void setRespond(int respond) {
        this.respond = respond;
    }
}
