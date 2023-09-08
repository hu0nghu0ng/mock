package com.example.newmockup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Todo> todoList;
}
