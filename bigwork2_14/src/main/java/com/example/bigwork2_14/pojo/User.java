package com.example.bigwork2_14.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @ManyToMany(fetch = FetchType.EAGER)//设置急加载
    @JoinTable(name = "clazzes_users",uniqueConstraints = {@UniqueConstraint(columnNames = {"c_id","u_id"})},
            joinColumns = { @JoinColumn(name="u_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "c_id",referencedColumnName = "id")})
    //User是关系维护方，此处创建了第三张表，由user id 和clazz id 共同确定该表的”id“
    private List<Clazz>  clazzes= new ArrayList<>();
    @Column
    private String name;
    @Column
    private String pwd;
    @Column
    private String perms;
    @Column
    public String getPerms() {
        return perms;
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", clazzes=" + clazzes +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", perms='" + perms + '\'' +
                '}';
    }
}
