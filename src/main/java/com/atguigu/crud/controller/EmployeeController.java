package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fisherman on 2017/9/19.
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /*
    * 单个 或批量删除  二合一
    * 批量：1-2-3
    * 单个删除：1
    * */

    @ResponseBody
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids) {
        if (ids.contains("-")) {
           //批量删除

            List<Integer> del_ids=new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合
            for (String string: str_ids
                 ) {
                    del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
        } else {
            //单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }


        return Msg.success();
    }


    //员工更新方法       empId和对象中的属性一样   Ajax直接发PUT请求不行，第一种方式解决是POST请求(由web.xml过滤器转化为PUT请求)+ data:$("#myModal2 form").serialize()+"&_method=PUT",
    //第二种方式是 SPringmvc提供的HttpPutFormContentFiler

    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee) {
        System.out.println("将要更新的数据" + employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }


    //根据id查询员工
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg geEmp(@PathVariable("id") Integer id) {  //PathVariable 指定从路径中获取
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }


    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {

        PageHelper.startPage(pn, 5);
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /*
    * 查询员工数据 分页*/
    // @RequestMapping("/emps")                           //默认第一页数据
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {

        //引入pageHelper分页插件，在查询之前只需要调用，传入页码以及分页每页的大小
        PageHelper.startPage(pn, 5);
        //startPage后面紧跟着这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps, 5);
        //连续显示的页数是5页
        //包装查出来的结果，只需要将pageInfo交给页面，封装了详细的分页信息
        //包括我们查询出来的数据
        model.addAttribute("pageInfo", page);
//下一步需要单元测试，验证正确性
        return "list";
    }


    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg insertEmpsInfo(Employee employee, BindingResult result) {
        System.out.println("----------------");
       /* if (result.hasErrors()) {
            //显示后端校验失败的错误信息
            Map<String,Object> map=new HashMap<>();
            List<FieldError> list=result.getFieldErrors();
            for (FieldError field:list
                 ) {
                System.out.println("错误字段名："+field.getField());
                System.out.println("错误信息："+field.getDefaultMessage());
                   map.put(field.getField(),field.getDefaultMessage());
            }
            return Msg.fail().add("errorfield",map);
        } else {*/

        employeeService.saveEmpsInfo(employee);
        return Msg.success();


    }

    @ResponseBody
    @RequestMapping("/checkUser")
    public Msg checkEmp(@RequestParam("empName") String empName) {
        System.out.println("控制器" + empName);

        //先判断用户名时否是合法的表达式
       /* String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if (empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5为中文");//添加到返回的信息中
        }*/
        //数据库用户名校验
        boolean b = employeeService.cheUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail();//.add("va_msg", "用户名不可用！")
        }
    }

}
