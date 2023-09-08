package com.example.newmockup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customerNumber")
    private Integer customerNumber;

    @Column(name = "customerName")
    private String customerName;
    
}
