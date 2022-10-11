package com.example.renteasytrial1st;

public class product {


    String  pname,deposit,Rent,pid;

    public product() {

    }
    public product(String pname, String deposit, String Rent,String pid) {

        this.pname = pname;
        this.deposit = deposit;
        this.Rent = Rent;
        this.pid = pid;


    }
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getdeposit() {
        return deposit;
    }

    public void setdeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String rent) {
        Rent = rent;
    }

    public String getpid() {
        return pid;
    }

    public void setpid(String pid) {
        this.pid = pid;
    }





}
