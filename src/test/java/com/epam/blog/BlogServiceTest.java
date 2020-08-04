package com.epam.blog;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlogServiceTest {

    @Autowired
    BlogService blogService;

    @Autowired
    BlogRepository blogRepository;

    public Blog testBlog = Blog.builder()
            .id( UUID.randomUUID().toString() )
            .blogTitle( "Fifth Blog" )
            .bloggerName( "Fifth Blogger" )
            .blogContent( "This is my Fifth Blog!! AWS !!" )
            .publishDate( new Date(System.currentTimeMillis()) )
            .bloggerNickname( "Fifth" )
            .blogComments( Arrays.asList( "Fifth comment" ))
            .build();

    @Test
    public void givenAll_whenFindAll_thenReturnAll(){
        Mono<Blog> saved = blogService.save(testBlog );
        Flux<Blog> blogFlux = blogService.findAll( Sort.by("publishDate").descending());

        StepVerifier
                .create( blogFlux )
                .expectNextCount( 1 )
                .verifyComplete();
    }

    @Test
    public void givenBloggerNickname_whenFindAllByBloggerNickname_thenReturnBlogByBloggerNickname() {
        Blog saved = blogService.save(Blog.builder()
                .id( UUID.randomUUID().toString() )
				.blogTitle( "Sixth Blog" )
				.bloggerName( "Sixth Blogger" )
				.blogContent( "This is my Sixth Blog!! AWS !!" )
				.publishDate( new Date(System.currentTimeMillis()) )
				.bloggerNickname( "Sixth" )
				.blogComments( Arrays.asList( "Sixth comment" ))
				.build()
        ).block();
        Flux<Blog> blogFlux = blogService.findAllByBloggerNickName(saved.getId());

        StepVerifier
                .create(blogFlux)
                .assertNext(Blog -> {
                    assertEquals("Sixth Blog", Blog.getBlogTitle());
                    assertEquals("This is my Sixth Blog!! AWS !!"  , Blog.getBlogContent());
                    assertNotNull(Blog.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenContent_whenUpdateBlog_thenUpdateBlog() {
        blogService.save(testBlog).block();
        Mono<Blog> blogMono = blogService.update(testBlog.getId(), "My new updated content");

        StepVerifier
                .create(blogMono)
                .assertNext(Blog -> {
                    assertNotNull(Blog.getId());
                    assertEquals(Blog.getBlogContent(),"My new updated content");
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenBlog_whenSave_thenSaveBlog() {
        Mono<Blog> blog = blogService.save(testBlog);

        StepVerifier
                .create(blog)
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .expectNextCount( 1 )
                .verifyComplete();
    }

}
