package com.xiong.exam.model;

import javax.persistence.*;
import java.io.Serializable;

public class Xuser {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String uname;

    /**
     * 密码(加密)
     */
    private String password;

    /**
     * 性别(0男,1女)
     */
    private Integer sex;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return uname - 用户名
     */
    public String getUname() {
        return uname;
    }

    /**
     * 设置用户名
     *
     * @param uname 用户名
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * 获取密码(加密)
     *
     * @return password - 密码(加密)
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码(加密)
     *
     * @param password 密码(加密)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取性别(0男,1女)
     *
     * @return sex - 性别(0男,1女)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别(0男,1女)
     *
     * @param sex 性别(0男,1女)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取电话号码
     *
     * @return tel - 电话号码
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置电话号码
     *
     * @param tel 电话号码
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
}