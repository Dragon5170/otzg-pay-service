package com.otzg.file.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 相册或文件夹
 */
@Entity
@Table
public class Folder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //名称
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    //排序
    @Column(name = "sort")
    private Integer sort;

    public Folder() {
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Map getJson(){
        Map m=new TreeMap();
        m.put("id",this.getId());
        m.put("name",this.getName());
        return m;
    }
}
