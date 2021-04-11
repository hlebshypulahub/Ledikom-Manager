package org.openjfx.ledicom.entities.inspection;

public class Checkup {
    private int id;
    private String answer;
    private String note;
    private Violation violation;
    private Question question;

    public Checkup(Question question) {
        this.question = question;
    }

    public Checkup(int id, String answer, String note, Violation violation, Question question) {
        this.id = id;
        this.answer = answer;
        this.note = note;
        this.violation = violation;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
