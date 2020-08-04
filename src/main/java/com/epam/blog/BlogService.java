package com.epam.blog;

import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BlogService {

    Flux<Blog> findAllByBloggerNickName(String nickName);

    Flux<Blog> findAll(Sort sort);

    Mono<Blog> save(Blog blog);

    Mono<Blog> update(String id, String content);

}
