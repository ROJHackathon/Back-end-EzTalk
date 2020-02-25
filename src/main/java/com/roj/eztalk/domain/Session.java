package com.roj.eztalk.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Session {
    @Id @GeneratedValue
    private Long token;
    @OneToOne
    @JoinColumn(name="user", referencedColumnName = "id")
    private User user;
}