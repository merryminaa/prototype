package com.example.wooriga.prototype;

public class Main {

    public static void main(String[] args) {
        Member member = new Member("james", new Address("Seoul"));
        Member shallowClone;
        Member deepClone;

        try {
            shallowClone = (Member) member.clone(); // 얕은복사
            deepClone = member.deepClone(); // 깊은복사
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        member.setName("Tom");
        member.getAddress().setCity("Jeju");

        System.out.println("member name is " + member.getName());
        System.out.println("member city is " + member.getAddress().getCity());

        System.out.println("shallowClone name is " + shallowClone.getName());
        System.out.println("shallowClone city is " + shallowClone.getAddress().getCity());

        System.out.println("deepClone name is " + deepClone.getName());
        System.out.println("deepClone city is " + deepClone.getAddress().getCity());
    }
}
