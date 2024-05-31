package com.example.wooriga.adapter.out.persistence;

import com.example.wooriga.application.port.out.MemberPort;
import com.example.wooriga.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberAdapter implements MemberPort {

    private final MemberRepository memberRepository;

    @Override
    public void save(Member member) {
//        memberRepository.save(member);
    }
}
