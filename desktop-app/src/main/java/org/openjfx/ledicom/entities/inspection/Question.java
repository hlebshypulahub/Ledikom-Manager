package org.openjfx.ledicom.entities.inspection;

public class Question {
    private int id;
    private CheckupType checkupType;
    private String question;

    public Question(int id, CheckupType checkupType, String question) {
        this.id = id;
        this.checkupType = checkupType;
        this.question = question;
    }

    public CheckupType getCheckupType() {
        return checkupType;
    }

    public void setCheckupType(CheckupType checkupType) {
        this.checkupType = checkupType;
    }

    public Question() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
