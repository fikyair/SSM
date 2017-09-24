package com.atguigu.crud.test;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试dao层
 * Created by fisherman on 2017/9/18.
 * <p>
 * 推荐Spring的项目就可以使用Spring的单元测试。可以自动注入需要的文件
 * 1、导入Spring  test模块
 * 2、ContextConfiguration 指定Spring配置文件的位置
 * 3、Autowired
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD() {
        /*//1、创建Spring IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2、从容器中获取mapper
        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);*/
        System.out.println(departmentMapper);

        //1、插入几个部门
        //为了插入方便在Department.java里面写好有参构造器
      /*  departmentMapper.insertSelective(new Department(null,"开发部"));
        departmentMapper.insertSelective(new Department(null,"测试部"));*/

        // 2、员工的插入

        //  employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@163.com", 1));
        //3、批量插入多个员工：批量使用可以执行批量操作的sqlSession  在IOC容器中配置一个
        /*for () {
            employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@163.com", 1));
        }*/
       /* EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 100; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null, uid, "M", uid + "@163.com", 1));
        }*/
    }
}
