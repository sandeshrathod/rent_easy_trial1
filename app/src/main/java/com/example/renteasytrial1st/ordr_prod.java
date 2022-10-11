package com.example.renteasytrial1st;

public class ordr_prod {
    String  pid,pname,deposit,Rent, reqstduid;

    public ordr_prod() {

    }
    public ordr_prod(String pid,String pname, String deposit, String Rent,String reqstduid) {
        this.pid=pid;
        this.pname = pname;

        this.deposit = deposit;
        this.Rent = Rent;
        this.reqstduid=reqstduid;

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

    public String getReqstduid() {
        return reqstduid;
    }

    public void setReqstduid(String reqstduid) {
        this.reqstduid = reqstduid;
    }
}
