package net.onest.mypartprj.beans;

public class SingleChoice {
    private String stem;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correct;
    private String analysis;
    private int keyNum;

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public int getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(int keyNum) {
        this.keyNum = keyNum;
    }

    public SingleChoice() {
    }

    public SingleChoice(String stem, String optionA, String optionB, String optionC, String optionD, String correct, String analysis, int keyNum) {
        this.stem = stem;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correct = correct;
        this.analysis = analysis;
        this.keyNum = keyNum;
    }

    @Override
    public String toString() {
        return "SingleChoice{" +
                "stem='" + stem + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", correct='" + correct + '\'' +
                ", analysis='" + analysis + '\'' +
                ", keyNum=" + keyNum +
                '}';
    }
}
