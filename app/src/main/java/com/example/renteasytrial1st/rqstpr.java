package com.example.renteasytrial1st;

public class rqstpr {
    String  pid,pname,deposit,Rent;

    public rqstpr(String pid, String pname, String deposit, String rent) {
        this.pid = pid;
        this.pname = pname;
        this.deposit = deposit;
        Rent = rent;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String rent) {
        Rent = rent;
    }
}
