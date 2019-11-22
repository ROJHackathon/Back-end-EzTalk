package com.roj.eztalk.fake;

import com.roj.eztalk.data.*;
import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fake/")
public class FakeController {
    @RequestMapping("/test-material")
    public Material testMaterial() {
        return new Material("Some Title", "Some Description", "Some Language", "Some Provider", "Some url",
                "true/false", 0);
    }

    @RequestMapping("/request-feed")
    public List<Material> fakeFeed() {
        ArrayList<Material> ret = new ArrayList<>();
        ret.add(new Material("French I", "an intro to French", "French", "ROJFake", "www.fake.com", "false", 179));
        ret.add(new Material("French II", "an intro to French", "French", "ROJFake", "www.fake.com", "false", 86));
        ret.add(new Material("In a French Restaurant", "common used sentence in a French restaurant", "French",
                "ROJFake", "www.fake.com", "false", 57));
        ret.add(new Material("French Travel Advice", "advice for tourists", "French", "ROJFake", "www.fake.com",
                "false", 659));
        ret.add(new Material("French food", "flash-card of various french dished", "French", "ROJFake", "www.fake.com",
                "true", 45));
        return ret;
    }

    @RequestMapping("/history")
    public List<String> fakeHistory() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("French Tutorial");
        ret.add("French Restaurant");
        ret.add("history of French");
        ret.add("French food");
        ret.add("learning French");
        ret.add("traveling to French");
        return ret;
    }

    @RequestMapping("/top-word")
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
    @RequestMapping("/search-material")
    public List<Material> fakeSearch() {
        ArrayList<Material> ret = new ArrayList<>();
        ret.add(new Material("search result 1", "description 1", "French", "ROJFake", "www.fake.com", "false", 11));
        ret.add(new Material("search result 2", "description 1", "French", "ROJFake", "www.fake.com", "false", 22));
        ret.add(new Material("search result 3", "description 1", "French", "ROJFake", "www.fake.com", "false", 33));
        ret.add(new Material("search result 4", "description 1", "French", "ROJFake", "www.fake.com", "false", 44));
        ret.add(new Material("search result 5", "description 1", "French", "ROJFake", "www.fake.com", "false", 55));
        ret.add(new Material("search result 6", "description 1", "French", "ROJFake", "www.fake.com", "false", 66));
        return ret;
    }

    @RequestMapping("/comment")
    public Integer fakeComment() {
        return 1;
    }

    @RequestMapping("/like")
    public Integer fakeLike() {
        return 1;
    }

    @RequestMapping("/get-comment")
    public List<Comment> gakeGetComment() {
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
}