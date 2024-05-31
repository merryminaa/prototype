package com.example.wooriga.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

public class Member {

    // 순수 domain layer => 독립적인 POJO, annotation-free

    private Long seq;
    private String id;
    private String password;
    private String name;

    @Builder
    public Member(Long seq, String id, String password, String name) {
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.name = name;
    }

}
