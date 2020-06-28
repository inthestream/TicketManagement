package com.tck.restapp;

import com.tck.model.User;
import com.tck.service.SecurityService;
import com.tck.service.UserService;
import com.tck.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @ResponseBody
    @RequestMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseBody
    @RequestMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return Util.getSuccessResult(userService.getUser(id));
    }

    @ResponseBody
    @RequestMapping(value="/register/admin", method= RequestMethod.POST)
    public Map<String, Object> registerAdmin(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password
    ) {
        userService.createUser(username, password, 3);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value="/login/admin", method=RequestMethod.POST)
    public Map<String, Object> loginAdmin(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password
    ) {
        User user = userService.getUser(username, password, 3);

        if(user == null) {
            return Util.getUserNotAvailableError();
        }

        String subject = user.getUserid() + "=" + user.getUsertype();
        String token = securityService.createToken(subject, (15 * 100 * 60));

        return Util.getSuccessResult(token);
    }

    @ResponseBody
    @RequestMapping(value="login/customer", method=RequestMethod.POST)
    public Map<String, Object> loginCustomer(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password
    ) {
        User user = userService.getUser(username, password, 1);

        if(user == null) {
            return Util.getUserNotAvailableError();
        }

        String subject = user.getUserid() + "=" + user.getUsertype();
        String token = securityService.createToken(subject, (15 * 1000 * 60));

        return Util.getSuccessResult(token);
    }

    @ResponseBody
    @RequestMapping(value="/login/csr", method=RequestMethod.POST)
    public Map<String, Object> loginCSR(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password
    ) {
        User user = userService.getUser(username, password, 2);

        if(user == null) {
            return Util.getUserNotAvailableError();
        }

        String subject = user.getUserid() + "=" + user.getUsertype();
        String token = securityService.createToken(subject, (15 * 1000 * 60));

        return Util.getSuccessResult(token);
    }

    @ResponseBody
    @RequestMapping(value="/register/customer", method=RequestMethod.POST)
    public Map<String, Object> registerCustomer(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password
    ) {
        userService.createUser(username, password, 2);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value="/register/csr", method=RequestMethod.POST)
    public Map<String, Object> registerCSR(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password
    ) {
        userService.createUser(username, password, 2);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value="", method=RequestMethod.PUT)
    public Map<String, Object> updateUser(
            @RequestParam(value="userid") Integer userid,
            @RequestParam(value="username") String username) {
        userService.updateUser(userid, username);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public Map<String, Object> deleteUser(
            @PathVariable("id") Integer userid) {
        userService.deleteUser(userid);
        return Util.getSuccessResult();
    }



}
