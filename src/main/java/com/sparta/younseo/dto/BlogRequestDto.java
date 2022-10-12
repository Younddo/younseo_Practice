package com.sparta.younseo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BlogRequestDto {

    private String title;
    private String contents;


    public BlogRequestDto( String title, String contents) {

        this.title = title;
        this.contents = contents;

    }



}
