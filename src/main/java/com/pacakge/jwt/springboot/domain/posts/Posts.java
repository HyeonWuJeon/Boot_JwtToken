package com.pacakge.jwt.springboot.domain.posts;

import com.pacakge.jwt.springboot.domain.BaseTimeEntity;
import com.pacakge.jwt.springboot.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String type;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    private Member member;


    @Builder
    public Posts(Member member,String type,String title, String content ) {
        this.member = member;
        this.type = type;
        this.title = title;
        this.content = content;

    }

    public void update(String type , String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }
}
