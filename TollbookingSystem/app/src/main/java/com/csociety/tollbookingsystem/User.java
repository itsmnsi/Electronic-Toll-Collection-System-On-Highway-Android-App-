package com.csociety.tollbookingsystem;

public class User {
    public String fname,emailid,mobile,vehicleno,drivingno,userid;
    public User(){

    }
    public User(String fname,String emailid,String mobile,String vehicleno,String drivingno,String userid){
        this.fname=fname;
        this.emailid=emailid;
        this.mobile=mobile;
        this.vehicleno=vehicleno;
        this.drivingno=drivingno;
        this.userid=userid;
       // this.rpassword=rpassword;
       // this.rcpassword=rcpassword;

    }
}
