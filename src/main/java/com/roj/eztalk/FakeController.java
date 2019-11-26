package com.roj.eztalk;

import com.roj.eztalk.data.*;
import com.roj.eztalk.exception.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api-fake/")
public class FakeController {
    private int counter = 0;
    @Autowired
    private Account account;
    @Autowired
    private X5gonService x5gonService;

    @GetMapping("/get-user")
    public User getUser(@RequestParam(value="token") Integer token,
        HttpServletResponse response) {
        User user = account.getUserByToken(token);
        if(user == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new User();
        }
        return user;
    }
    @GetMapping("/get-online-user")
    public List<User> getOnlineUser() {
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
    //TODO: use status code
    @PostMapping("/sign-up")
    public User signUp(@RequestBody SignUpRequestBody body) {
        return account.signup(body.getUserName(), body.getPassword());
    }

    @PostMapping("/user/{id}/status")
    public String getStatus(@RequestBody Token token, @PathVariable Integer id) {
        return account.getStatus(token);
    }
    //TODO: use status code
    @PostMapping("/log-out")
    public LogOutResponseBody logOut(@RequestBody Token token) {
        return account.logout(token);
    }

    @GetMapping("/request-feed")
    public List<Material> requestFeed(@RequestParam Map<String, String> allParams,
        HttpServletResponse response) {
        String page = allParams.get("page");
        String token = allParams.get("token");
        
        try {
            String preference = account.getUserByToken(Integer.parseInt(token)).getPreference();
            return x5gonService.recommendMaterial(preference, page);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ArrayList<Material>();
        }
    }

    @PostMapping("/set-preference")
    public String setPreference(@RequestBody SetPreference body, HttpServletResponse response){
        String token = body.getToken();
        if(!account.isTokenValid(Integer.parseInt(token))){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String preference = body.getPreference();
        try{
        account.setPreference(Integer.parseInt(token), preference);
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Invalid Token";
        }
        return "Preference set";
    }

    @GetMapping("/history")
    public List<String> history(@RequestParam Map<String, String> allParams) {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("French Tutorial");
        ret.add("French Restaurant");
        ret.add("history of French");
        ret.add("French food");
        ret.add("learning French");
        ret.add("traveling to French");
        return ret;
    }

    @GetMapping("/top-word")
    public List<String> topWord() {
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
    public List<Material> search(@RequestBody SearchEntry searchEntry) {
        try {
            return x5gonService.searchMaterial(searchEntry.getText());
        } catch (Exception e) {
            return new ArrayList<Material>();
        }
    }

    // TODO: use id instead of name
    @PostMapping("/material/{id}/comment")
    public ResponseEntity<String> comment(@RequestBody Comment comment, @PathVariable Long id) {
        if (id == -1) {
            throw new MaterialNotFoundException(id);
        } else if (comment.getId() == -1) {
            throw new MaterialNotFoundException(comment.getId());
        }
        return ResponseEntity.ok(comment.getUser().getName() + " : " + comment.getContent());
    }

    // TODO: use id instead of name
    @PostMapping("/material/{id}/like")
    public ResponseEntity<String> like(@RequestBody User user, @PathVariable Long id) {
        return ResponseEntity.ok(user.getName() + " liked " + id.toString());
    }

    @GetMapping("/material/{id}/get-comment")
    public List<Comment> getComment(@PathVariable Long id) {
        if (id == -1) {
            throw new MaterialNotFoundException(id);
        }
        List<Comment> ret = new ArrayList<>();
        ret.add(new Comment("Comment 1", new User(1, "fake user 1", generateAvatarUrl())));
        ret.add(new Comment("Comment 2", new User(2, "fake user 2", generateAvatarUrl())));
        ret.add(new Comment("Comment 3", new User(3, "fake user 3", generateAvatarUrl())));
        ret.add(new Comment("Comment 4", new User(4, "fake user 4", generateAvatarUrl())));
        ret.add(new Comment("Comment 5", new User(5, "fake user 5", generateAvatarUrl())));
        ret.add(new Comment("Comment 6", new User(6, "fake user 6", generateAvatarUrl())));
        ret.add(new Comment("Comment 7", new User(7, "fake user 7", generateAvatarUrl())));
        ret.add(new Comment("Comment 8", new User(8, "fake user 8", generateAvatarUrl())));
        ret.add(new Comment("Comment 9", new User(9, "fake user 9", generateAvatarUrl())));
        ret.add(new Comment("Comment 10", new User(10, "fake user 10", generateAvatarUrl())));
        return ret;
    }

    @GetMapping("/material/{id}")
    public Material getMaterial(@PathVariable String id, HttpServletResponse response) {
        try {
            return x5gonService.getMaterialById(id);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return null;
    }

    @PostMapping("/create-chatroom")
    public Chatroom createChatroom(@RequestBody Chatroom rb) {
        return new Chatroom(1, rb.getName(), rb.getLanguage());
    }

    @GetMapping("/chatroom-list")
    public List<Chatroom> getChatroomList() {
        List<Chatroom> ret = new ArrayList<>();
        ret.add(new Chatroom(1, "chatroom 1", "english"));
        ret.add(new Chatroom(2, "chatroom 2", "french"));
        ret.add(new Chatroom(3, "chatroom 3", "chinese"));
        return ret;
    }

    @GetMapping("/official-chatroom-list")
    public List<Chatroom> getOfficialChatroomList() {
        List<Chatroom> ret = new ArrayList<>();
        ret.add(new Chatroom(1, "chatroom 1", "english"));
        ret.add(new Chatroom(2, "chatroom 2", "french"));
        ret.add(new Chatroom(3, "chatroom 3", "chinese"));
        return ret;
    }

    @GetMapping("/chatroom/{id}")
    public Chatroom getChatroomById(@PathVariable Integer id) {
        return new Chatroom(1, "ChatroomById", "chinese");
    }

    @GetMapping("/chatroom/{id}/get-user")
    public List<User> chatroomGetUsers(@PathVariable Integer id) {
        List<User> ret = new ArrayList<>();
        ret.add(new User(1, "member1", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member2", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member3", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member4", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member5", generateAvatarUrl(), null, null, null));
        return ret;
    }

    @GetMapping("/chatroom/{id}/get-messages")
    public List<Message> getMessages(@PathVariable Integer id) {
        List<Message> ret = new ArrayList<>();
        User u = new User(1, "BigMouth", generateAvatarUrl(), null, null, null);
        ret.add(new Message("blahblahblah", u));
        ret.add(new Message("jijijijijiji", u));
        ret.add(new Message("guaguaguagua", u));
        return ret;
    }

    @GetMapping("/chatroom/{id}/get-active-user")
    public List<User> getActiveUser(@PathVariable Integer id) {
        List<User> ret = new ArrayList<>();
        ret.add(new User(1, "member1", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member2", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member3", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member4", generateAvatarUrl(), null, null, null));
        ret.add(new User(1, "member5", generateAvatarUrl(), null, null, null));
        return ret;
    }

    @PostMapping("/chatroom/{id}/say")
    public String say(@PathVariable Integer id, @RequestBody Message message) {
        return "success";
    }

    public static String generateCoverUrl() {
        String ret = "https://cdn.framework7.io/placeholder/nature-1000x600-%s.jpg";
        Long index = Math.round(Math.random() * 7 + 1);
        return String.format(ret, index.toString());
    }

    public static String generateAvatarUrl() {
        String ret = "http://placeimg.com/80/80/people/";
        Long index = Math.round(Math.random() * 99 + 1);
        return ret + index.toString();
    }
}