package com.example.model;

import jakarta.persistence.*;


@Entity
@Table(name = "reverve")
public class Rereve{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
}

    
