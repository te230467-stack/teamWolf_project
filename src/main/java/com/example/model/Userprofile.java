package com.example.model;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "userprofiles")
public class Userprofile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phonenumber;

    public Userprofile(){
    }

    public Userprofile(String email,String phonenumber,User user){
        this.email = email;
        this.phonenumber = phonenumber;
        this.user = user;
    }

    public Long getId(){
        return id;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPhonenumber(){
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber){
        this.phonenumber = phonenumber;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
}