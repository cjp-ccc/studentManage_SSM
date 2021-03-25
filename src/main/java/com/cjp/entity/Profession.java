package com.cjp.entity;

public class Profession {
    private Integer id;
    private String name;
    private Integer academyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAcademyId() {
        return academyId;
    }

    public void setAcademyId(Integer academyId) {
        this.academyId = academyId;
    }

    public Profession() {
    }

    public Profession(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Profession(Integer id, String name, Integer academyId) {
        this.id = id;
        this.name = name;
        this.academyId = academyId;
    }

    @Override
    public String toString() {
        return "Profession{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", academyId=" + academyId +
                '}';
    }
}
