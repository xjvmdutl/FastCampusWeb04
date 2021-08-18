package com.example.FastCampusWeb04.controller;

import com.example.FastCampusWeb04.dto.Req;
import com.example.FastCampusWeb04.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
                     @PathVariable String userName
                     ){
        log.info("userId : {} , userName : {}",userId,userName);
        log.info("client req : {}",user);
        return user;
    }


    @PostMapping("/exchange")
    public User exchange(@RequestBody User user,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader
    ){
        log.info("x-authorization : {} , custom-header : {}",authorization,customHeader);
        log.info("client req : {}",user);
        return user;
    }


    @PostMapping("/genericExchange")
    public Req<User> exchange(
            //HttpEntity<String> entity,//순수 HttpEntity 값을 받는다
                    @RequestBody Req<User> user,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader
    ){
        //log.info("req : {}",entity.getBody());//로그 찍는용
        log.info("x-authorization : {} , custom-header : {}",authorization,customHeader);
        log.info("client req : {}",user);

        Req<User> response = new Req<>();
        response.setHeader(
                new Req.Header()
        );
        response.setResBody(
                user.getResBody()
        );
        return response;
    }
    @GetMapping("/naver")
    public String naver(){
        //https://openapi.naver.com/v1/search/local.json
        // ?query=%EC%A3%BC%EC%8B%9D
        // &display=10
        // &start=1
        // &sort=random
        String query = "중국집";//인코딩

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query",query)
                .queryParam("display",10)
                .queryParam("start",1)
                .queryParam("sort","random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id","YJH21CGt7XCdGA3HuxEA")
                .header("X-Naver-Client-Secret","Vw1Zze8az8")
                .build();
        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        return result.getBody();
    }


}
