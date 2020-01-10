package com.roj.eztalk.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Rating {
    public Rating(Integer rating, User author, Material material){
        this.rating = rating;
        this.author = author;
        this.material = material;
    }

    @Id
    @GeneratedValue
    private Long id;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;
}