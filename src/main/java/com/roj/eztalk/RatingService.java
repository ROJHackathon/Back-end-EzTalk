package com.roj.eztalk;

import java.util.Optional;

import com.roj.eztalk.data.Material;
import com.roj.eztalk.data.Rating;
import com.roj.eztalk.data.RatingRepository;
import com.roj.eztalk.data.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    SessionService sessionService;
    @Autowired
    MaterialService materialService;
    @Autowired
    RatingRepository ratingRepository;
    public Rating rate(Integer token, Long materialId, Integer rate){
        Optional<User> opUser = sessionService.getUserByToken(token);
        if(!opUser.isPresent()) return null;
        User user = opUser.get();

        Optional<Material> opMaterial = materialService.findById(materialId);
        if(!opMaterial.isPresent()) return null;
        Material material = opMaterial.get();

        Rating rating = new Rating(rate, user, material);
        ratingRepository.save(rating);
        return null;
    }
}