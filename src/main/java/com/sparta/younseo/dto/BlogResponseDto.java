package com.sparta.younseo.dto;


import com.sparta.younseo.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDto {
    private String title;
    private String contents;
    private String email;

    public BlogResponseDto(Blog blog) {
        this.email = blog.getMember().getEmail();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
    }
}
