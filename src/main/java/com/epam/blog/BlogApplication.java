package com.epam.blog;

import javafx.util.Builder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	ApplicationRunner init(BlogRepository blogRepository){

		Object [][] data = {
				{"First Blog" , "First Blogger", "This is my First Blog!! AWS !!" , "First", "First comment" },
				{"Second Blog" , "Second Blogger", "This is my Second Blog!! AWS !!" , "Second", "Second comment" },
				{"Third Blog" , "Third Blogger", "This is my Third Blog!! AWS !!" , "Third", "Third comment" },
				{"Forth Blog" , "Forth Blogger", "This is my Forth Blog!! AWS !!" , "Forth", "Forth comment" },
				{"Fifth Blog" , "Fifth Blogger", "This is my Fifth Blog!! AWS !!" , "Fifth", "Fifth comment" },
		};

//		Blog fifth = Blog.builder()
//				.blogTitle( "Fifth Blog" )
//				.bloggerName( "Fifth Blogger" )
//				.blogContent( "This is my Fifth Blog!! AWS !!" )
//				.publishDate( new Date(System.currentTimeMillis()) )
//				.bloggerNickname( "Fifth" )
//				.blogComments( Arrays.asList( "Fifth comment" ))
//				.build();

		return args -> {
			blogRepository.deleteAll()
					.thenMany(
							Flux.just( data )
									.map( array -> { return new Blog().builder()
											.id( UUID.randomUUID().toString())
											.blogTitle( (String) array[0] )
											.bloggerName( (String) array[1] )
											.blogContent( (String) array[2] )
											.publishDate(new Date(System.currentTimeMillis()))
											.bloggerNickname( (String) array[3]  )
											.blogComments( Arrays.asList((String) array[4] ))
											.build();
										})
							.flatMap( blogRepository::save )
					)
					.thenMany( blogRepository.findAll() )
					.subscribe(blog -> System.out.println( "Adding Blog : " + blog.toString()));
		};
	}
}
