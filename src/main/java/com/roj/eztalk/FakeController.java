package com.roj.eztalk;

import com.roj.eztalk.data.*;
import com.roj.eztalk.exception.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api-fake/")
public class FakeController {
    private int counter = 0;
    @Autowired
    private AccountInterface account;

    @GetMapping("/get-online-user")
    public List<User> getOnlineUser(){
        return account.getOnlineUser();
    }

    @GetMapping("/get-all-user")
    public List<User> getAllUser() {
        return account.getAllUser();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        User ret = new User();
        return ret;
    }

    @PostMapping("/login")
    public LoginResponseBody login(@RequestBody LoginRequestBody body) {
        return account.login(body.getUserName(), body.getPassword());
    }

    @PostMapping("/sign-up")
    public User signUp(@RequestBody SignUpRequestBody body) {
        return account.signup(body.getUserName(), body.getPassword());
    }

    @PostMapping("/user/{id}/status")
    public String getStatus(@RequestBody Token token, @PathVariable Long id) {
        return account.getStatus(token);
    }

    @PostMapping("/log-out")
    public LogOutResponseBody logOut(@RequestBody Token token) {
        return account.logout(token);
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

    // TODO: use id instead of name
    @PostMapping("/material/{id}/comment")
    public ResponseEntity<String> fakeComment(@RequestBody Comment comment, @PathVariable Long id) {
        if (id == -1) {
            throw new MaterialNotFoundException(id);
        } else if (comment.getId() == -1) {
            throw new MaterialNotFoundException(comment.getId());
        }
        return ResponseEntity.ok(comment.getUser().getName() + " : " + comment.getContent());
    }

    // TODO: use id instead of name
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
        ret.add(new Comment("Comment 1", new User(1, "fake user 1", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 2", new User(2, "fake user 2", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 3", new User(3, "fake user 3", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 4", new User(4, "fake user 4", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 5", new User(5, "fake user 5", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 6", new User(6, "fake user 6", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 7", new User(7, "fake user 7", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 8", new User(8, "fake user 8", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 9", new User(9, "fake user 9", "http://placeimg.com/80/80/people/3")));
        ret.add(new Comment("Comment 10", new User(10, "fake user 10", "http://placeimg.com/80/80/people/3")));
        return ret;
    }

    @GetMapping("/material/{id}")
    public Material fakeGetMaterial(@PathVariable Long id) {
        return new Material(Long.valueOf(1), "Some Title", "Some Description", "Some Language", "Some Provider",
                "Some url", "true/false", 0, FakeController.generateCoverUrl());
    }

    @GetMapping("/chatroom/get-list")
    public List<Chatroom> fakeGetChatroomList() {
        return new ArrayList<>();
    }

    @GetMapping("/chatroom/{id}/get-user")
    public List<User> fakeChatroomGetUsers(@PathVariable Long id) {
        List<User> ret = new ArrayList<>();
        return ret;
    }

    @GetMapping("/chatroom/{id}/get-messages")
    public List<Message> getMessages(@PathVariable Long id) {
        List<Message> ret = new ArrayList<>();
        return ret;
    }

    @GetMapping("/chatroom/{id}/get-active-user")
    public List<User> getActiveUser(@PathVariable Long id) {
        List<User> ret = new ArrayList<>();
        return ret;
    }

    public static String generateCoverUrl() {
        String ret = "https://cdn.framework7.io/placeholder/nature-1000x600-%s.jpg";
        Long index = Math.round(Math.random() * 7 + 1);
        return String.format(ret, index.toString());
    }
}