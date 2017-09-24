package com.atguigu.crud.test;

import com.atguigu.crud.bean.Employee;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 使用Spring的测试模块测试页面的请求功能 ，进一步测试Crud的正确性
 * spring4测试的时候
 * Created by fisherman on 2017/9/19.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {
    //传入springMVc的IOC容器  //只能装在iOC容器里面的，要是他本身就需要加WebAppConfiguration
    @Autowired
    WebApplicationContext context;

    //模拟mVC请求
    MockMvc mockMvc;

    @Before                 //每次这个方法都初始化 junit中的
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();

        //请求成功后，请求域中会有pageInfo，我们可以取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码：" + pi.getPageNum());
        System.out.println("总页码：" + pi.getPages());
        System.out.println("总记录数：" + pi.getTotal());
        System.out.println("在页面中需要连续显示的页码");
        int[] nums = pi.getNavigatepageNums();
        for (int i : nums) {
            System.out.print(" " + i);
        }
//获取员工数据
        List<Employee> list = pi.getList();
        for (Employee e : list
                ) {
            System.out.println("ID: " + e.getdId() + "==>Name: " + e.getEmpName());
        }
    }
}
