package com.example.yanxiaopeidemo.entitys;

public class SchoolInfo {
    private String name;
    private String loc;
    private String belong;
    private String path;
    private String status;


    public SchoolInfo(String name, String loc, String belong, String path) {
        this.name = name;
        this.loc = loc;
        this.belong = belong;
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoc() {
        return loc;
    }


    public void setLoc(String loc) {
        this.loc = loc;
    }


    public String getBelong() {
        return belong;
    }


    public void setBelong(String belong) {
        this.belong = belong;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "School [name=" + name + ", loc=" + loc + ", belong=" + belong + ", path=" + path + "]";
    }
}
