package com.roj.eztalk.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Rating {
    public Rating(Integer rating, User author, Material material){
        this.rating = rating;
        this.author = author;
        this.material = material;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "material", referencedColumnName = "id")
    private Material material;
}