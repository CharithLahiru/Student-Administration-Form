package lk.ijse.dep10.app.model;

import lk.ijse.dep10.app.util.Gender;
import lk.ijse.dep10.app.util.StudyDuration;

import java.util.ArrayList;

public class Students {
    private String id;
    private String name;
    private Gender gender;
    private ArrayList<String> contacts;
    private String degree;
    private ArrayList<String> modules;
    private StudyDuration partTime;

    public Students(String id, String name, Gender gender, ArrayList<String> contacts, String degree, ArrayList<String> modules, StudyDuration partTime) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.contacts = contacts;
        this.degree = degree;
        this.modules = modules;
        this.partTime = partTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudyDuration getPartTime() {
        return partTime;
    }

    public Students() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public ArrayList<String> getModules() {
        return modules;
    }

    public void setModules(ArrayList<String> modules) {
        this.modules = modules;
    }

    public StudyDuration isPartTime() {
        return partTime;
    }

    public void setPartTime(StudyDuration partTime) {
        this.partTime = partTime;
    }
}
