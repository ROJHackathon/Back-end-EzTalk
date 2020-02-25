package com.roj.eztalk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    private String avatarUrl = null;
    private String email = null;
    private String language = null;
    private String targetLanguage = null;
    private String preference = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_chatroom", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "chatroom_id"))
    private List<Chatroom> memberOf = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Message> messages;

    public void addChatroom(Chatroom chatroom) {
        this.memberOf.add(chatroom);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Rating> ratings = new ArrayList<>();

    public void addRating(Rating rating){
        this.ratings.add(rating);
    }

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Session session;
}