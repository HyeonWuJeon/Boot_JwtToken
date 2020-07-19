package com.pack.jwt.springboot.web.post;

import com.pack.jwt.springboot.config.auth.LoginUser;
import com.pack.jwt.springboot.domain.user.Member;
import com.pack.jwt.springboot.service.MemberService;
import com.pack.jwt.springboot.service.PostsService;
import com.pack.jwt.springboot.web.dto.PostsListResponseDto;
import com.pack.jwt.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostsService postsService;
    //질문 페이지
    @GetMapping("/member/post")
    public String Post(Model model, @LoginUser String user){

        Member member = memberService.findMember(user);
        model.addAttribute("member", user);
        model.addAttribute("memberid", member);

        return "post-save";
    }

    /**
     * 전체 질문 조회
     */
    @GetMapping("/member/post/list")
    public String Postlist(Model model) {

        List<PostsListResponseDto> Posts = postsService.findAllDesc();
        model.addAttribute("posts", Posts);
        return "postlist";
    }

    @GetMapping("/member/post/answer/{id}")
    public String Postlist(@PathVariable Long id, Model model) {
        PostsResponseDto Post = postsService.findById(id);

        model.addAttribute("post", Post);
        return "SinglePost";
    }


    @GetMapping("/mypost/{id}")
    public String mypost(@PathVariable Long id, Model model){
        Member member = memberService.findMember(id);
        List<PostsListResponseDto> posts = postsService.findAllDesc();
        List<PostsListResponseDto> result = new ArrayList<>();
        for (PostsListResponseDto p : posts){
            if(p.getMember().getEmail().equals(member.getEmail())) {
                result.add(p);
            }
        }

        model.addAttribute("posts", result);
        return "mypost";
    }
}
