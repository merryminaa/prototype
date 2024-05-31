package com.example.wooriga;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter
public class RedisInfo {

    private String key;
    private LocalDateTime value;


    public RedisInfo(String key, LocalDateTime value) {
        this.key = key;
        this.value = value;
    }
}
