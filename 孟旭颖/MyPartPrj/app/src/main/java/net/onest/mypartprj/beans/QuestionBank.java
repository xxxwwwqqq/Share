package net.onest.mypartprj.beans;

public class QuestionBank {
    private String course;
    private int num;
    private int kemuSta;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getKemuSta() {
        return kemuSta;
    }

    public void setKemuSta(int kemuSta) {
        this.kemuSta = kemuSta;
    }

    public QuestionBank() {
    }

    public QuestionBank(String course, int num, int kemuSta) {
        this.course = course;
        this.num = num;
        this.kemuSta = kemuSta;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "course='" + course + '\'' +
                ", num=" + num +
                ", kemuSta=" + kemuSta +
                '}';
    }
}
