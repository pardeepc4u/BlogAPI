package com.epam.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Flux<Blog> findAllByBloggerNickName(String nickName) {
//        return blogRepository.findById( nickName );
        return blogRepository.findAllByBloggerNickname(nickName);
    }

    @Override
    public Flux<Blog> findAll(Sort sort) {
        return blogRepository.findAll( sort );
    }

    @Override
    public Mono<Blog> save(Blog blog) {
        return blogRepository.save( blog );
    }

    @Override
    public Mono<Blog> update(String id, String content) {
        return blogRepository.findById(id)
                .map(
                        p-> Blog.builder()
                        .id( id )
                        .bloggerName( p.getBloggerName() )
                        .bloggerNickname( p.getBloggerNickname() )
                        .blogTitle( p.getBlogTitle() )
                        .blogContent( content )
                        .publishDate( p.getPublishDate() )
                        .updateDate( new Date(System.currentTimeMillis()) )
                        .blogComments( p.getBlogComments() )
                        .build()
                )
                .flatMap(this.blogRepository::save);
    }
}
