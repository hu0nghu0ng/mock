package com.example.newmockup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;
}
