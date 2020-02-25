package com.roj.eztalk.domain;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.roj.eztalk.dao.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistentTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;
    
    @Test
    public void randomTest() {
        User testUser = new User("test","123");
        userRepository.save(testUser);

        Session session = new Session();
        session.setUser(testUser);
        session = sessionRepository.save(session);
        Assert.assertEquals(testUser, session.getUser());

        sessionRepository.delete(session);

        Optional<User> u = userRepository.findByName("test");
        Assert.assertEquals(u.get(), testUser);
    }
    @Test
    public void update(){
        User user = new User("wong", "1215");
        userRepository.save(user);

        Session session = new Session();
        session.setUser(user);
        sessionRepository.save(session);

        Optional<User> opuser = userRepository.findByName("wong");
        User nuser = opuser.get();
        nuser.setPassword("321");
        userRepository.save(nuser);

        Assert.assertEquals("321", sessionRepository.findById(session.getToken()).get().getUser().getPassword());
    }
    
    @Test
    public void user() {
        User user = new User("admin", "admin");
        userRepository.save(user);

        Session session = new Session();
        session.setUser(user);
        sessionRepository.save(session);

        Optional<Session> opSession = sessionRepository.findByUser(user);
        Assert.assertEquals(true, opSession.isPresent());
        Assert.assertEquals(session, opSession.get());
        Assert.assertEquals(user, opSession.get().getUser());
    }
}