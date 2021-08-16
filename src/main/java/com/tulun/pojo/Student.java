package com.tulun.pojo;

import java.util.Date;

/**
 * @author QiangQin
 * * @date 2021/8/16
 */
public class Student {
    private Integer id;
    private String name;
    private String email;
    private Integer graduate_time;
    private String school;
    private String tlclass;//班级
    private Date create_time;//插入数据库的时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGraduate_time() {
        return graduate_time;
    }

    public void setGraduate_time(Integer graduate_time) {
        this.graduate_time = graduate_time;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTlclass() {
        return tlclass;
    }

    public void setTlclass(String tlclass) {
        this.tlclass = tlclass;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", graduate_time=" + graduate_time +
                ", school='" + school + '\'' +
                ", tlclass='" + tlclass + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
