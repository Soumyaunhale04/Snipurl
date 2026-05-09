package com.snipurl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.snipurl.entity.Url;


public interface UrlRepository extends JpaRepository<Url, Long>{
  
  public Optional<Url> findUrlByShortcode(String shortcode);
}
