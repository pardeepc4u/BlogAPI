package com.epam.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    public String id;

    public String blogTitle;

    public String bloggerName;

    @Indexed(unique = true)
    public String bloggerNickname;

    public String blogContent;

    public Date publishDate;

    public Date updateDate;

    public List<String> blogComments;
}
