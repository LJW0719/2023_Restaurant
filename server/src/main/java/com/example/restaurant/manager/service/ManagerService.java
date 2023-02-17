package com.example.restaurant.manager.service;

import com.example.restaurant.manager.dao.ManagerDao;
import com.example.restaurant.manager.domain.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    ManagerDao managerDao;

    public Manager findByEmail(String email){
        return managerDao.findByEmail(email);
    }

    public int add(Manager manager){
        return managerDao.addManager(manager);
    }

    public Manager login(String email, String password){
        return managerDao.loginManager(email, password);
    }
}
