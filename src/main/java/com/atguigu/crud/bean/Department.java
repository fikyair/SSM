package com.atguigu.crud.bean;

public class Department {
    private Integer deptId;

    private String deptName;

    public Department(Integer deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }//一旦生成有参构造器，一定要生成无参构造器

    public Department() {
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }
}