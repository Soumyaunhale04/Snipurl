package com.snipurl.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snipurl.entity.Url;
import com.snipurl.service.UrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;



@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> getShortenUrl(@RequestBody String url){
        String originalUrl = url.trim();
        Url urlobj  = urlService.shortenUrl(originalUrl).orElse(null);
        
        // post should return created object with status in response entity
       return new ResponseEntity<>(urlobj, HttpStatus.CREATED);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<Url> getOriginalString(@PathVariable String shortcode) {
        Url urlobj = urlService.getOriginaUrl(shortcode).orElse(null);
        
        // get should check for null conditions and return the status code accordingly
        if(urlobj != null){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(urlobj.getOriginalurl()));
             return ResponseEntity.status(302).headers(headers).body(urlobj);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
}
