package com.restapi.model;

import java.time.LocalDate;
import java.time.Period;
//import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="patient")
public class Patient {

    @Id
    private String pid;
    private String firstname;
    private String lastname;
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate dob;
    
    @Transient
    private int age;


    public Patient(String firstname, String lastname, String gender, LocalDate dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dob = dob;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        //this.age = Period.between(dob, LocalDate.now()).getYears();
        this.dob = dob;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getAge() {
        this.age = Period.between(this.dob, LocalDate.now()).getYears();
        return age;
    }

   /* public void setAge(int age) {
        this.age = age;
    }
    */


   

    
    
}