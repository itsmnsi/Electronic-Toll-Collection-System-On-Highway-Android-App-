package com.csociety.tollbookingsystem;

public class Token {
    public String uid,source,destination,tokens,amount,purchaseStatus,tokenid;
    public Token(){

    }

    public Token(String uid, String tokenid,String source, String destination, String tokens, String amount,String purchaseStatus) {
        this.uid = uid;
        this.tokenid=tokenid;
        this.source = source;
        this.destination = destination;
        this.tokens = tokens;
        this.amount = amount;
        this.purchaseStatus=purchaseStatus;
    }
}
