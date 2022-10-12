package com.sparta.younseo.service;

import com.sparta.younseo.entity.Member;
import com.sparta.younseo.repository.BlogRepository;
import com.sparta.younseo.dto.BlogRequestDto;
import com.sparta.younseo.entity.Blog;
import com.sparta.younseo.repository.MemberRepository;
import com.sparta.younseo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;



    @Transactional //posting service (create) 생성
    public Long post(BlogRequestDto requestDto) {
        Blog post = new Blog(requestDto);
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(
                ()->new IllegalArgumentException("없성")
        );
        post.setMember(member);
        blogRepository.save(post);
        return post.getId();
    }

    @Transactional //글 전체 목록 loading
    public List<Blog> get() {
        return blogRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional //하나의 글만 가지고 올 때
    public Blog getOne(Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional //글 삭제
    public Long deleteBlog(Long postId) { // postid는 글의 번호
        Blog deleteblog = blogRepository.findById(postId).orElseThrow( // 지우고자 하는 글을 blog1에다 담은 것이다.
                ()-> new IllegalArgumentException("해당 글이 없습니다.")
        );
        Long authorId = deleteblog.getMember().getId(); // 글의 작성자의 아이디 (글에 담겨있는 member에서 꺼내온 것)
        Long currentId = SecurityUtil.getCurrentMemberId(); // 현재 로그인하고 있는 사람의 아이디
        if (authorId==currentId) {
            blogRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("아이디가 다르잖아!!!!(삭제권한이 없습니다)");
        }

        return postId; // void여도 우리가 짠 로직은 다 수행되었기 때문에 어떤 값을 리턴하여도 크게 상관 없음
        // 만약에 프론트에서 성공하였을 때 리턴값으로 받고 싶은 내용이 있으면 수정해야 함 ㅎ
    }

    @Transactional
    public Long update(Long updateId, BlogRequestDto updateRequestDto) { // updateId 는 업데이트하고자 하는 글의 번호
        Blog updateblog = blogRepository.findById(updateId).orElseThrow( //update하고자 하는 글을 updateblog에 담은 것이다.
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Long authorId = updateblog.getMember().getId(); // 글의 작성자 아이디 (글에 담겨있는 member 테이블에서 꺼내온 것)
        Long currentId = SecurityUtil.getCurrentMemberId(); // securityutil에서 가져온 지금 현재 로그인하고 있는 사람의 아이디
        if (authorId == currentId) { //위 두개의 아이디가 일치한다면
            updateblog.update(updateRequestDto); // 업데이트를 해주세요 -> transactional을 사용했기 때문에 업데이트만 해주어도 저장 됨.
        } else {
            throw new IllegalArgumentException("수정권한이 없습니다."); // orElseThrow에는 이미 Illgal 이 함수가 정의되어 있지만, if else 구문으로 작성할 때에는 throw 해주어야 함!
        }
        return updateId;  // void여도 우리가 짠 로직은 다 수행되었기 때문에 어떤 값을 리턴하여도 크게 상관 없음
        // 만약에 프론트에서 성공하였을 때 리턴값으로 받고 싶은 내용이 있으면 수정해야 함 !! 기억하기
    }

}
