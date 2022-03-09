package com.example.bigwork2_14.controller;

import com.example.bigwork2_14.dao.ClazzDao;
import com.example.bigwork2_14.dao.UserDao;
import com.example.bigwork2_14.pojo.Clazz;
import com.example.bigwork2_14.pojo.User;
import com.example.bigwork2_14.service.UserService;
import org.apache.coyote.Request;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class MyController {
    @Autowired
    private UserService userService;

    //登录过后的选择界面
    @RequestMapping("/choose")
    public String toChoose(){
        return "/in/choose";
    }
    //初始界面
    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello Shiro");
        return "index";//index：索引
    }
    //添加新用户
    @RequestMapping ("/user/add")
    public String add(){
        return "/user/add";
    }
    @RequestMapping  (value = "user/doadd")
    public String girladd(@RequestParam("username") String name,
                        @RequestParam("password")String pwd,
                        @RequestParam("perms")String perms,
                          Model model){
        if(userService.findByName(name)==null){//确保用户名不重复
            User user=new User();
            user.setPerms(perms);
            user.setName(name);
            user.setPwd(pwd);
            userService.save(user);
            return "index";
        }else {
            model.addAttribute("msg","用户已经存在");
            return "/user/add";
        }
    }
    //更新用户名
    @RequestMapping("/user/doupdateusername")
    public String updateUserName(@RequestParam("username") String name,
                         @RequestParam("ppassword")String ppwd,
                         @RequestParam("nusername")String nName,
                                 Model model){
        if(userService.findByName(name)==null){//查找用户
            model.addAttribute("msg","没有此用户");
            return "/user/updateusername";
        }else{
            User user=userService.findByName(name);
            if(ppwd.equals(user.getPwd())){//判断密码是否正确
                user.setName(nName);
                userService.save(user);
                return "index";
            }else{
                model.addAttribute("msg","密码错误");
                return "/user/updateusername";
            }
        }
    }
    @RequestMapping("/user/updateusername")
    public String updateUserName(){
        return "/user/updateusername";
    }
    // 更新密码
    @RequestMapping("/user/doupdate")
    public String update(@RequestParam("username") String name,
                         @RequestParam("ppassword")String ppwd,
                         @RequestParam("npassword")String npwd,
                         Model model){
        User user=userService.findByName(name);
        if(user==null){  //查找用户
            model.addAttribute("msg","未查找到此用户");
            return "user/update";
        }else{
            if(ppwd.equals(user.getPwd())){//判断密码是否正确
                user.setPwd(npwd);
                userService.save(user);
                return "index";
            }else{
                model.addAttribute("msg","密码错误");
                return "user/update";
            }
        }
    }
    @RequestMapping("/user/update")
    public String update(){
    return "/user/update";
    }
    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/dologin")
    public String login(String username,String password,Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);//执行登陆方法，如果没有异常就说明ok了
            return "in/choose";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
    //若没有页面的访问权限
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }
    //注销
    @RequestMapping("/logout")
    public String logout(){
        return "index";
    }
    @RequestMapping("/student")
    //进入choose页面之后可以选择进入以下三个界面
    public String studentRole(){
        return "role/student";
    }
    @RequestMapping("/teacher")
    public String teacherrole(){
        return "role/teacher";
    }
    @RequestMapping("/administrator")
    public String administratorRole(){
        return "role/administrator";
    }

}
