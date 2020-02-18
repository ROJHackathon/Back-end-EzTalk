package com.roj.eztalk.service;

import org.junit.Assert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.roj.eztalk.domain.User;
import com.roj.eztalk.dao.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void register_name_available() {
        User user = new User("hello", "123");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findByName("hello")).thenReturn(Optional.empty());
        User ret = userService.register(user.getName(), user.getPassword());
        Assert.assertEquals(user.getName(), ret.getName());
        Assert.assertEquals(user.getPassword(), ret.getPassword());
        // Assert.assertTrue(ret == user);
        verify(userRepository, times(1)).save(ret);
        verify(userRepository, times(1)).findByName("hello");
    }

    @Test
    public void register_name_unavailable() {
        User user = new User("hello", "123");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findByName("hello")).thenReturn(Optional.of(new User("hello", "456")));
        User ret = userService.register(user.getName(), user.getPassword());
        Assert.assertEquals(null, ret);
        verify(userRepository, times(0)).save(any(User.class));
        verify(userRepository, times(1)).findByName("hello");
    }

    @Test
    public void setPreference_user_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newPreference = "English";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        user.setPreference(newPreference);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setPreference(id, newPreference);
        Assert.assertEquals(user.getName(), ret.getName());
        Assert.assertEquals(newPreference, ret.getPreference());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void setPreference_user_not_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newPreference = "English";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        user.setPreference(newPreference);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setPreference(id, newPreference);
        Assert.assertEquals(null, ret);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void setEmail_user_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newEmail = "123@ucl.ac.uk";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        user.setEmail(newEmail);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setEmail(id, newEmail);
        Assert.assertEquals(user.getName(), ret.getName());
        Assert.assertEquals(newEmail, ret.getEmail());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void setEmail_user_not_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newEmail = "123@ucl.ac.uk";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        user.setEmail(newEmail);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setEmail(id, newEmail);
        Assert.assertEquals(null, ret);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void setLanguage_user_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newLanguage = "French";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        user.setLanguage(newLanguage);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setLanguage(id, newLanguage);
        Assert.assertEquals(user.getName(), ret.getName());
        Assert.assertEquals(newLanguage, ret.getLanguage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void setLanguage_user_not_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newLanguage = "French";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        user.setLanguage(newLanguage);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setLanguage(id, newLanguage);
        Assert.assertEquals(null, ret);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void setTargetLanguage_user_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newTargetLanguage = "French";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        user.setTargetLanguage(newTargetLanguage);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setTargetLanguage(id, newTargetLanguage);
        Assert.assertEquals(user.getName(), ret.getName());
        Assert.assertEquals(newTargetLanguage, ret.getTargetLanguage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void setTargetLanguage_user_not_exist() {
        User user = new User("hello", "123");
        Long id = 123L;
        String newTargetLanguage = "French";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        user.setTargetLanguage(newTargetLanguage);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        User ret = userService.setTargetLanguage(id, newTargetLanguage);
        Assert.assertEquals(null, ret);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(user);
    }
}