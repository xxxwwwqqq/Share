package com.example.yanxiaopeidemo.entitys;

public class School {
    private int img;
    private String name;
    private String location;
    private String tag;
    private String score;

    public School() {
    }

    public School(int img, String name, String location, String tag, String score) {
        this.img = img;
        this.name = name;
        this.location = location;
        this.tag = tag;
        this.score = score;
    }

    public School(String name, String location, String tag, String score) {
        this.name = name;
        this.location = location;
        this.tag = tag;
        this.score = score;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "School{" +
                "img=" + img +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", tag='" + tag + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
