package com.csociety.serverappforqr;


public class User {
    public String fname,userid,emailid,mobile,drivingno,vehicleno;
    public User(){

    }
    public User(String fname,String userid,String emailid,String mobile,String drivingno,String vehicleno){
        this.fname=fname;
        this.emailid=emailid;
        this.mobile=mobile;
        this.drivingno=drivingno;
        this.vehicleno=vehicleno;
        this.userid=userid;


    }
    public User(String fname,String emailid,String mobile){
        this.fname=fname;
        this.emailid=emailid;
        this.mobile=mobile;


    }
}

