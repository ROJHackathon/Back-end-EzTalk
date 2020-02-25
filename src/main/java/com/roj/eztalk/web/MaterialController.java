package com.roj.eztalk.web;

import com.roj.eztalk.service.*;
import com.roj.eztalk.domain.*;
import com.roj.eztalk.domain.request.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@Api(tags = {"Material Controller"})
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/")
public class MaterialController {
    @Autowired
    X5gonService x5gonService;

    @Autowired
    SessionService sessionService;

    @Autowired
    RatingService ratingService;

    @Autowired
    MaterialService materialService;

    @GetMapping("/material/{id}")
    @ApiOperation(value = "Get an material detail information by its material id", tags = "Material Management")
    public MaterialItem getMaterial(@PathVariable Long id, HttpServletResponse response) {
        try {
            response.setStatus(200);
            return x5gonService.getMaterialById(id);
        } catch (Exception e) {
            response.setStatus(400);
            return null;
        }
    }

    @PostMapping("/material/{id}/rate")
    @ApiOperation(value = "Rate a material by its id", tags = "Material Management")
    public RatingItem rate(@RequestBody RateRequest request, @PathVariable Long id, HttpServletResponse response) {
        Long token = request.getToken();
        Integer rate = request.getRate();
        Rating rating = ratingService.rate(token, id, rate);
        if (rating == null)
            response.setStatus(400);
        return new RatingItem(rating);
    }

    @PostMapping("/material/{id}/comment")
    @ApiOperation(value = "Make a comment on this material by id", tags = "Material Management")
    public CommentItem comment(@RequestBody CommentRequest request, @PathVariable Long id, HttpServletResponse response) {
        String content = request.getContent();
        Long token = request.getToken();

        Optional<Material> opMaterial = materialService.findById(id);
        if (!opMaterial.isPresent()) {
            response.setStatus(404);
            return null;
        }

        User user = sessionService.getUserByToken(token);
        if (user == null) {
            response.setStatus(400);
            return null;
        }

        Comment comment = materialService.comment(user, opMaterial.get(), content);
        return new CommentItem(comment);
    }

    @GetMapping("/material/{id}/get-comments")
    @ApiOperation(value = "get a list of comments made under current material by id", tags = {"Material Management","Admin Operations"})
    public List<CommentItem> getComments(@PathVariable Long id, HttpServletResponse response) {
        Optional<Material> opMaterial = materialService.findById(id);
        if (!opMaterial.isPresent()) {
            response.setStatus(404);
            return null;
        }
        Material material = opMaterial.get();
        response.setStatus(200);
        return material.getComments().stream().map(x -> new CommentItem(x)).collect(Collectors.toList());
    }

    @PostMapping("/material/{id}/love")
    @ApiOperation(value = "Love a material by its id", tags = "Material Management")
    public void love(@RequestBody Token token, @PathVariable Long id, HttpServletResponse response) {
        Material material = materialService.love(id);
        if (material == null) {
            response.setStatus(404);
        } else {
            response.setStatus(200);
        }
    }
}