package com.roj.eztalk.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    @Id @GeneratedValue private long id;
    @NonNull private String name;
    @NonNull private String password;
    private String avatarUrl = null;
    private String email = null;
    private String language = null;
    private String preference = null;
}