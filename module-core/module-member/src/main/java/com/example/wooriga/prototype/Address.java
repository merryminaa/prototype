package com.example.wooriga.prototype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String city;

    public Address(String city) {
        this.city = city;
    }
}
