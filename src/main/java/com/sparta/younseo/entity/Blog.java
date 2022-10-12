package com.sparta.younseo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.younseo.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Setter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Blog extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne //사람 하나가 여러개의 글을 가질 수 있음 - 영속성 컨텍스트가 인지함, DB에는 저장 X
    @JoinColumn(nullable = false) // DB에 실제로 테이블을 만들기 위함
    private Member member;


    @Column (nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;





    public Blog( String title, String contents) {

        this.title = title;
        this.contents = contents;

    }

    public Blog(BlogRequestDto requestDto) {

        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }

    public void update(BlogRequestDto requestDto) {

        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }

}
