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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;


@Controller
public class StudentController {
    @Autowired
    ClazzService clazzService;
    @Autowired
    UserService userService;
    //返回的课程必须是经过授权的，而且是未被当前用户选择的
    @RequestMapping("/student/chooseclazz")
    public String stuChoClaz(Model model){
        List<Clazz> clazzes = clazzService.findByState(1);
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        List<Clazz> clazzes1 = currentUser.getClazzes();
        int i,j;
        for (i=0;i<clazzes.size();i++){
            for(j=0;j<clazzes1.size();j++){
                if(clazzes.get(i).getId().equals(clazzes1.get(j).getId())){
                    clazzes.remove(i);
                }
            }
        }
        model.addAttribute("msg",clazzes.toString());
        return "/student/chooseclazz";
    }
    //选择的课程如果不存在或者以选过
    @RequestMapping("/chooseclazz")
    public String stuChoClaz(@RequestParam("id") Integer id,Model model){
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        if(clazzService.getById(id)!=null){//判断课程是否存在
            Clazz clazz = clazzService.getById(id);
            int judge=1;
            for(Clazz clazz1:currentUser.getClazzes()){//判断课程是否已选
                if(clazz.getId()==clazz1.getId()){
                    judge=0;
                }
            }
            if(judge==1){
                if(clazz.getState()==0){//判断授权状态
                    model.addAttribute("outp","error");
                    return "/student/chooseclazz";
                }else if (clazz.getState()==1) {
                    userService.chooseClazz(clazz,currentUser);
                }
            }else{
                    model.addAttribute("outp","已选过该课");
                    return "/student/chooseclazz";
            }

        }else {
            model.addAttribute("outp","没有此课程！");
            return "/student/chooseclazz";
        }
        return "role/student";
    }
    //查询自己已选过的课程
    @RequestMapping("/student/lookover")
    public void lookOver(Model model){
        userService.lookOverSer(model);
    }
    //退课，用了一种歪门邪道，因为不知为何remove（clazz）就是不能移除
    @RequestMapping("/student/dropclazz")
    public String dropClazz(){
        return "/student/dropclazz";
    }
    @RequestMapping("/dodropclazz")
    public String doDropClazz(@RequestParam("id")Integer id){
        userService.doDropClazz(id);
        return "/role/student";
    }
}