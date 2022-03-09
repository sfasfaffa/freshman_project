package com.example.bigwork2_14.controller;
import com.example.bigwork2_14.dao.ClazzDao;
import com.example.bigwork2_14.dao.UserDao;
import com.example.bigwork2_14.pojo.Clazz;
import com.example.bigwork2_14.pojo.User;
import com.example.bigwork2_14.service.ClazzService;
import com.example.bigwork2_14.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class TeacherController {
    @Autowired
    ClazzService clazzService;
    @Autowired
    UserService userService;
    //添加课程
    @RequestMapping("/teacher/addclazz")
    public String add(){
        return "teacher/addclazz";
    }
    @RequestMapping  (value = "/doaddclazz")
    public String clazzAdd(@RequestParam("clazzname") String clazzname
    ){
       userService.addClazz(clazzname);
        return "role/teacher";
    }
    //查询自己发布的课程，若state=1：已被授权，state=0：未授权
    @RequestMapping (value = "/teacher/myclazzes")
    public String myClazz(Model model){
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        model.addAttribute("msg",clazzService.findByTeacherId(currentUser.getId()));
        return "/teacher/myclazzes";
    }
}
