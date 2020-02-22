package com.roj.eztalk.service;

import java.util.List;
import java.util.Optional;

import com.roj.eztalk.domain.Material;
import com.roj.eztalk.domain.Rating;
import com.roj.eztalk.dao.RatingRepository;
import com.roj.eztalk.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    // for rating material
    @Autowired
    SessionService sessionService;
    @Autowired
    MaterialService materialService;
    @Autowired
    RatingRepository ratingRepository;
    public Rating rate(Long token, Long materialId, Integer rate){
        User user = sessionService.getUserByToken(token);
        if(user == null) return null;

        Optional<Material> opMaterial = materialService.findById(materialId);
        if(!opMaterial.isPresent()) return null;
        Material material = opMaterial.get();

        Rating rating = null;
        List<Rating> ratingList = ratingRepository.findByMaterialIdAndUserId(materialId);
        for(Rating r : ratingList) {
            if(r.getAuthor().getId() == user.getId()){
                rating = r;
                rating.setRating(rate);
                break;
            }
        }
        if(rating == null){
            rating = new Rating(rate, user, material);
        }
        rating = ratingRepository.save(rating);
        return rating;
    }
}