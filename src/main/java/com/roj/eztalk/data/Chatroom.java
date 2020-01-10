package com.roj.eztalk.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    @Column(length = 200)
    private String description;
    @NonNull
    private String language;
    @NonNull
    private String type;

    @JsonIgnore
    @ManyToMany(mappedBy = "memberOf")
    private List<User> members = new ArrayList<>();

    public void addMember(User user){
        this.members.add(user);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "chatroom")
    private List<Message> messages = new ArrayList<>();
}