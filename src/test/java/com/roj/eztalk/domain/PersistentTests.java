package com.roj.eztalk.domain;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.roj.eztalk.domain.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistentTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void randomTest() {
        User testUser = new User("test","123");
        entityManager.persist(testUser);
        entityManager.flush();

        Optional<User> opUser = userRepository.findByName("test");

        assertTrue(opUser.isPresent());
        assertTrue(opUser.get().getName().equals("test"));
    }
}