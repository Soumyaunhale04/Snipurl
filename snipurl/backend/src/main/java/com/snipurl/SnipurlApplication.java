package com.snipurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// @EnableCaching --- turns ON Spring’s caching system. without this @Cachable, @CachePut, @CacheEvict won't work.
@EnableCaching
public class SnipurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnipurlApplication.class, args);
	}

}

/*
Suppose your app fetches a URL from PostgreSQL.
Without cache: Request → Service → Database → Response
With Redis cache: 
First Request:
Request → DB → Redis stores result → Response
Next Requests:
Request → Redis → Response
 */