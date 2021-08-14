package com.example.FastCampusWeb04.controller;

import com.example.FastCampusWeb04.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server")
@Slf4j
public class ServerApiController {

    @RequestMapping("/hello")
    public User hello(@RequestParam String name,@RequestParam int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User post(@RequestBody User user,
                     @PathVariable int userId,
                     @PathVariable String userName,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader
                     ){
        log.info("userId : {} , userName : {}",userId,userName);
        log.info("x-authorization : {} , custom-header : {}",authorization,customHeader);
        log.info("client req : {}",user);
        return user;
    }




}
