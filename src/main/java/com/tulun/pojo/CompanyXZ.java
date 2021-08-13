package com.tulun.pojo;

import jdk.internal.org.objectweb.asm.tree.InnerClassNode;

import java.util.Date;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */
//  实习僧 校招 公司信息
public class CompanyXZ {
    private Integer id;
    private String name;
    private String job_url;
    private String work_city;
    private String price;
    private String job_name;
    private String company_info;
    private Date create_time;
    private Integer state;
    private String md5_key;

    @Override
    public String toString() {
        return "CompanyXZ{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job_url='" + job_url + '\'' +
                ", work_city='" + work_city + '\'' +
                ", price='" + price + '\'' +
                ", job_name='" + job_name + '\'' +
                ", company_info='" + company_info + '\'' +
                ", create_time=" + create_time +
                ", state=" + state +
                ", md5_key='" + md5_key + '\'' +
                '}';
    }

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

    public String getJob_url() {
        return job_url;
    }

    public void setJob_url(String job_url) {
        this.job_url = job_url;
    }

    public String getWork_city() {
        return work_city;
    }

    public void setWork_city(String work_city) {
        this.work_city = work_city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getCompany_info() {
        return company_info;
    }

    public void setCompany_info(String company_info) {
        this.company_info = company_info;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMd5_key() {
        return md5_key;
    }

    public void setMd5_key(String md5_key) {
        this.md5_key = md5_key;
    }
}
