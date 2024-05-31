package com.example.wooriga.application.service;

import com.example.wooriga.RedisInfo;
import com.example.wooriga.RedisRepository;
import com.example.wooriga.application.port.in.MemberUseCase;
import com.example.wooriga.application.port.out.MemberPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private final MemberPort memberPort;
    private final RedisRepository redisRepository;

    @Override
    public void createMember() {
//        redisRepository.save(new RedisInfo("TEST", LocalDateTime.now())); // test
//        memberPort.save();
    }
}
