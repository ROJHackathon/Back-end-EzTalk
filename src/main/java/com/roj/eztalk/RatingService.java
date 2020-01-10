package com.roj.eztalk;

import java.util.List;
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