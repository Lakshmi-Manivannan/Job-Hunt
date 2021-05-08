package com.example.jobhuntapp.HelperClasses;

public class UserHelperClass {

    String full_name,u_name,password,email,pno,img_id;
    public UserHelperClass() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public UserHelperClass(String full_name, String u_name, String password, String email, String pno, String img_id) {
        this.full_name = full_name;
        this.u_name = u_name;
        this.password = password;
        this.email = email;
        this.pno = pno;
        this.img_id = img_id;
    }
}
