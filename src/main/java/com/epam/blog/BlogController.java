package com.epam.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController()
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public @ResponseBody
    Flux<Blog> getAllBlogs() {
        return blogService.findAll(Sort.by("publishDate").descending());
    }

    @PostMapping("/blog")
    public @ResponseBody
    Mono<Blog> addBlog(@RequestBody Blog blog){
        return blogService.save( blog );
    }

    @GetMapping("/blog/{nicks}")
    public @ResponseBody
    Flux<Blog> getAllBlogsByBlobberNickName(@PathVariable String nicks) {
        return blogService.findAllByBloggerNickName(  nicks );
    }
}
