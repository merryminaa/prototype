package com.example.wooriga.factoryMethod;

public class MeetCertificate implements Charge {

    @Override
    public String getChargeType() {
        return "meet_certificate";
    }

}
