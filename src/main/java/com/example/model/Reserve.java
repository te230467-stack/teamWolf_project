package com.example.model;

import jakarta.persistence.*;


@Entity
@Table(name = "reserves")
public class Reserve{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "reserve_id",unique =  true,nullable = false)
    //private String reserve_id;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "orderStatus")
    private boolean orderStatus;

    //@ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    //private User user;

    public Reserve() {}

        // ゲッター・セッター
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

   // public String getReserve_id() { return reserve_id;}
    //public void setReserve_id(String reserve_id) { this.reserve_id = reserve_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    
    /*
    public User getUser(){
        return user; 
    }
    public void setUser(User user) { 
        this.user = user; 
    }
    */
    
    public boolean isOrderStatus(){
        return orderStatus;
    }

        @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
               // ", reserve_id='" + reserve_id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                //", user=" + user.getId().toString() +
                '}';
    }
}

    
