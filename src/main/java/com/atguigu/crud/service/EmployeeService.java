package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.EmployeeMapper;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by fisherman on 2017/9/19.
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /*
    * 查询所有员工*/
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);//查所有 所以没有什么条件
    }

    public void saveEmpsInfo(Employee employee) {
        //insert是连id也插入
        employeeMapper.insertSelective(employee);  //其中id自增
    }

    //检查用户名  true代表姓名可以用 数据库中没有

    public boolean cheUser(String empName) {

        EmployeeExample example = new EmployeeExample();
        //创建查询条件
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        System.out.println("服务层： 有" + count + " 条相同数据");
        return count == 0;
    }

    //按id查询
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    //员工更新
    public void updateEmp(Employee employee) {


        employeeMapper.updateByPrimaryKeySelective(employee);//因为不带员工名字，带了什么信息就更新什么信息
    }

    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from XXX where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);

    }
}
