package com.roj.eztalk;

import java.util.Optional;

import com.roj.eztalk.data.Comment;
import com.roj.eztalk.data.CommentRepository;
import com.roj.eztalk.data.Material;
import com.roj.eztalk.data.MaterialRepository;
import com.roj.eztalk.data.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    CommentRepository commentRepository;
    
    public Optional<Material> findById(Long id){
        return materialRepository.findById(id);
    }

    public Comment comment(User author, Material material, String content){
        Comment comment = new Comment(content, author, material);
        comment = commentRepository.save(comment);
        return comment;
    }
}