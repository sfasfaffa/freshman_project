package com.example.bigwork2_14.controller;

import com.example.bigwork2_14.dao.ClazzDao;
import com.example.bigwork2_14.pojo.Clazz;
import com.example.bigwork2_14.pojo.User;
import com.example.bigwork2_14.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller

public class AdministratorController {
    @Autowired
    private ClazzService clazzService;
    @RequestMapping("/administrator/findallclazz")
    public String findAllClazz(Model model){
        model.addAttribute("msg",clazzService.findAll().toString());//返回所有课程
        return "admin/findclazz";
    }
    @RequestMapping("/administrator/dofindallclazz")
    public String author(@RequestParam("clazzname") Integer id,
                         @RequestParam("choose") Integer choose
                        ,Model model){
        if(clazzService.findById(id)==null){//若课程不存在
            model.addAttribute("eup","课程不存在");
            return "/admin/findclazz";
        }else {
            Clazz clazz=clazzService.getById(id);
                    clazz.setState(choose);
            clazzService.save(clazz);
        }
        return "/role/administrator";
        }
}

