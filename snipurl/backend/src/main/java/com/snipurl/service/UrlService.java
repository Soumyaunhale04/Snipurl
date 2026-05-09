package com.snipurl.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.snipurl.entity.Url;
import com.snipurl.repository.UrlRepository;
import java.util.UUID;

@Service
public class UrlService {

  @Autowired
  UrlRepository urlRepository;

  public Optional<Url> shortenUrl(String originalString){

     Url url = new Url();
     
    //  generating a random string of 8 length
     UUID uuid = UUID.randomUUID();
     String uuidString = uuid.toString().substring(0,8);

    // setting the properties
      url.setShortcode(uuidString);
      url.setOriginalurl(originalString);

    // setting up creation time and expiration time using LocalDateTime
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime expiryTime = now.plusDays(10);
      url.setCreatedTime(now);
      url.setExpiryTime(expiryTime);

      // returning an optional object
      Optional<Url> optional = Optional.ofNullable(urlRepository.save(url));
      return optional;

  }

  public Optional<Url> getOriginaUrl(String shortCode){
    return urlRepository.findUrlByShortcode(shortCode);
  }
}
