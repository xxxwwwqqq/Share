package net.onest.mypartprj.exersise;

public class QuestionBank {
    private String course;
    private int num;

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

    public QuestionBank() {
    }

    public QuestionBank(String course, int num) {
        this.course = course;
        this.num = num;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "course='" + course + '\'' +
                ", num=" + num +
                '}';
    }
}
