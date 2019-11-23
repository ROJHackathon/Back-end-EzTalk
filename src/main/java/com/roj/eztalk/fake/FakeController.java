package com.roj.eztalk.fake;

import com.roj.eztalk.data.*;
import com.roj.eztalk.exception.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api-fake/")
public class FakeController {
    private int counter = 0;

    @GetMapping("/test-material")
    public Material testMaterial() {
        return new Material(Long.valueOf(1), "Some Title", "Some Description", "Some Language", "Some Provider",
                "Some url", "true/false", 0, generateCoverUrl());
    }

    @GetMapping("/user/{id}/request-feed")
    public List<Material> fakeFeed(@PathVariable Long id) {
        if (id == -1) {
            throw new UserNotFoundException(id);
        }
        ArrayList<Material> ret = new ArrayList<>();
        ret.add(new Material(Long.valueOf(1), "Material" + counter++, "an intro to French", "French", "ROJFake",
                "www.fake.com", "false", 179, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), "Material" + counter++, "an intro to French", "French", "ROJFake",
                "www.fake.com", "false", 179, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), "Material" + counter++, "an intro to French", "French", "ROJFake",
                "www.fake.com", "false", 179, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), "Material" + counter++, "an intro to French", "French", "ROJFake",
                "www.fake.com", "false", 179, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), "Material" + counter++, "an intro to French", "French", "ROJFake",
                "www.fake.com", "false", 179, generateCoverUrl()));
        return ret;
    }

    @GetMapping("/user/{id}/history")
    public List<String> fakeHistory(@PathVariable Long id) {
        if (id == -1) {
            throw new UserNotFoundException(id);
        }
        ArrayList<String> ret = new ArrayList<>();
        ret.add("French Tutorial" + id.toString());
        ret.add("French Restaurant");
        ret.add("history of French");
        ret.add("French food");
        ret.add("learning French");
        ret.add("traveling to French");
        return ret;
    }

    @GetMapping("/top-word")
    public List<String> fakeTopWord() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("topword1");
        ret.add("topword2");
        ret.add("topword3");
        ret.add("topword4");
        ret.add("topword5");
        ret.add("topword6");
        return ret;
    }

    @PostMapping("/search-material")
    public List<Material> fakeSearch(@RequestBody SearchEntry searchEntry) {
        ArrayList<Material> ret = new ArrayList<>();
        ret.add(new Material(Long.valueOf(1), searchEntry.getText() + " 1", "description 1", "French", "ROJFake",
                "www.fake.com", "false", 11, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), searchEntry.getText() + " 2", "description 2", "French", "ROJFake",
                "www.fake.com", "false", 22, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), searchEntry.getText() + " 3", "description 3", "French", "ROJFake",
                "www.fake.com", "false", 33, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), searchEntry.getText() + " 4", "description 4", "French", "ROJFake",
                "www.fake.com", "false", 44, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), searchEntry.getText() + " 5", "description 5", "French", "ROJFake",
                "www.fake.com", "false", 55, generateCoverUrl()));
        ret.add(new Material(Long.valueOf(1), searchEntry.getText() + " 6", "description 6", "French", "ROJFake",
                "www.fake.com", "false", 66, generateCoverUrl()));
        return ret;
    }

    @PostMapping("/material/{id}/comment")
    public ResponseEntity<String> fakeComment(@RequestBody Comment comment, @PathVariable Long id) {
        if (id == -1) {
            throw new MaterialNotFoundException(id);
        } else if (comment.getId() == -1) {
            throw new MaterialNotFoundException(comment.getId());
        }
        return ResponseEntity.ok(comment.getUser().getName() + " : " + comment.getContent());
    }

    @PostMapping("/material/{id}/like")
    public ResponseEntity<String> fakeLike(@RequestBody User user, @PathVariable Long id) {
        return ResponseEntity.ok(user.getName() + " liked " + id.toString());
    }

    @GetMapping("/material/{id}/get-comment")
    public List<Comment> fakeGetComment(@PathVariable Long id) {
        if (id == -1) {
            throw new MaterialNotFoundException(id);
        }
        List<Comment> ret = new ArrayList<>();
        ret.add(new Comment("Comment 1", new User("fake user 1")));
        ret.add(new Comment("Comment 2", new User("fake user 2")));
        ret.add(new Comment("Comment 3", new User("fake user 3")));
        ret.add(new Comment("Comment 4", new User("fake user 4")));
        ret.add(new Comment("Comment 5", new User("fake user 5")));
        ret.add(new Comment("Comment 6", new User("fake user 6")));
        ret.add(new Comment("Comment 7", new User("fake user 7")));
        ret.add(new Comment("Comment 8", new User("fake user 8")));
        ret.add(new Comment("Comment 9", new User("fake user 9")));
        ret.add(new Comment("Comment 10", new User("fake user 10")));
        return ret;
    }

    @GetMapping("/material/{id}")
    public Material fakeGetMaterial(@PathVariable Long id) {
        return new Material(Long.valueOf(1), "Some Title", "Some Description", "Some Language", "Some Provider",
                "Some url", "true/false", 0, FakeController.generateCoverUrl());
    }

    public static String generateCoverUrl() {
        String ret = "https://cdn.framework7.io/placeholder/nature-1000x600-%s.jpg";
        Long index = Math.round(Math.random() * 7 + 1);
        return String.format(ret, index.toString());
    }
}