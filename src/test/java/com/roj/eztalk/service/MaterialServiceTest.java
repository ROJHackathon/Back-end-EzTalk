package com.roj.eztalk.service;

import org.junit.Assert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.roj.eztalk.domain.*;
import com.roj.eztalk.dao.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MaterialServiceTest {
    @TestConfiguration
    static class MaterialServiceTestContextConfiguration {
        @Bean
        public MaterialService MaterialService() {
            return new MaterialService();
        }
    }
    @MockBean
    CommentRepository commentRepository;

    @MockBean
    MaterialRepository materialRepository;

    @Autowired
    MaterialService materialService;

    @Test
    public void comment() {

    }

    @Test
    public void love() {
        
    }
}