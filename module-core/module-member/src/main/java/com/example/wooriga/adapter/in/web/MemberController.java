package com.example.wooriga.adapter.in.web;


import com.example.wooriga.application.port.in.MemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/members")
public class MemberController {

    private final MemberUseCase memberUseCase;

    @PostMapping
    public void create() {
        memberUseCase.createMember();
    }


}
