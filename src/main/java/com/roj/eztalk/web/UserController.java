package com.roj.eztalk.web;

import com.roj.eztalk.service.*;
import com.roj.eztalk.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@Api(tags = {"User Controller"})
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/")
public class UserController {
    @Autowired
    UserService userService;
    // user
    @GetMapping("user/{id}")
    @ApiOperation(value = "Get an material detail information by its material id", tags = "Admin Operations")
    public UserItem getUser(@PathVariable Long id, HttpServletResponse response) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return new UserItem(user.get());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    // user history
    @GetMapping("user/{id}/history")
    @ApiOperation(value = "Get someones search history information by its user id (Not Token)", tags = "User Management")
    public List<String> getHistory(@PathVariable Long id, HttpServletResponse response) {
        // TODO: history
        ArrayList<String> retval = new ArrayList<>();
        retval.add("history 1");
        retval.add("history 2");
        retval.add("history 3");
        retval.add("history 4");
        retval.add("history 5");
        return retval;
    }
}