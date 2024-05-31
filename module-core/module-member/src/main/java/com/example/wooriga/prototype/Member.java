package com.example.wooriga.prototype;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Member implements Cloneable {

    private String name;
    private Address address;

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Member deepClone() throws CloneNotSupportedException {
        Address copyAddress = new Address(this.address.getCity());
        return new Member(this.name, copyAddress);
    }
}
