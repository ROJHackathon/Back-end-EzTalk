package com.roj.eztalk.service;

import java.util.Optional;

import com.roj.eztalk.domain.Comment;
import com.roj.eztalk.dao.CommentRepository;
import com.roj.eztalk.domain.Material;
import com.roj.eztalk.dao.MaterialRepository;
import com.roj.eztalk.domain.User;
import com.roj.eztalk.domain.json.MaterialJson;
import com.roj.eztalk.domain.json.WeightedMaterialJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    // for comment and love(like)
    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    UtilService utilService;
    
    public Optional<Material> findById(Long id){
        return materialRepository.findById(id);
    }

    public Comment comment(User author, Material material, String content){
        Comment comment = new Comment(content, author, material);
        comment = commentRepository.save(comment);
        return comment;
    }

    // TODO: associate love with user
    public Material love(Long id){
        Optional<Material> opMaterial = materialRepository.findById(id);
        if(!opMaterial.isPresent()){
            return null;
        }
        Material material = opMaterial.get();
        int loves = material.getLove();
        material.setLove(loves + 1);
        return materialRepository.save(material);
    }

    public Material insertIfNotExists(MaterialJson materialJson){
        long id = Long.parseLong(materialJson.material_id);
        Optional<Material> material = materialRepository.findById(id);
        if(!material.isPresent()){
            Material newMaterial = new Material(id, utilService.generateCoverUrl(), 0);
            return materialRepository.save(newMaterial);
        } else {
            return material.get();
        }
    }

    public Material insertIfNotExists(WeightedMaterialJson materialJson){
        long id = Long.parseLong(materialJson.material_id);
        Optional<Material> material = materialRepository.findById(id);
        if(!material.isPresent()){
            Material newMaterial = new Material(id, utilService.generateCoverUrl(), 0);
            return materialRepository.save(newMaterial);
        } else {
            return material.get();
        }
    }
}