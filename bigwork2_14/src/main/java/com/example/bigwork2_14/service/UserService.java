package com.example.bigwork2_14.service;

import com.example.bigwork2_14.dao.UserDao;
import com.example.bigwork2_14.pojo.Clazz;
import com.example.bigwork2_14.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

public interface UserService {
    public String lookOverSer(Model model);
    public void doDropClazz(Integer id);
    public void chooseClazz(Clazz clazz, User currentUser);
    public void addClazz(String name);
    public User findByName(String name);
    public void save(User user);
}
