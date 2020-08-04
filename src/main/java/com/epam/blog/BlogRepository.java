package com.epam.blog;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {

//    Flux<Blog> findAllByBloggerNickname(String nickName);
//    Flux<Blog> findAll(Sort sort);
//    Flux<Blog> findAll();
//    Mono<Blog> save(Blog blog);
//    Mono<Blog> update(Blog blog);
    @Query("{ 'bloggerNickname': ?0 }")
    Flux<Blog> findAllByBloggerNickname(String nickName);

}
