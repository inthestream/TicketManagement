package com.tck.restapp;

import com.tck.model.User;
import com.tck.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class UserTest {

    @Autowired
    public UserService userService;

    @Test
    public void testAllUsers() {
        List<User> users = userService.getAllUsers();
        assertEquals(4, users.size());
    }

    @Test
    public void testSingleUser() {
        User user = userService.getUser(101);
        assertTrue(user.getUsername().contains("David"));
    }
}
