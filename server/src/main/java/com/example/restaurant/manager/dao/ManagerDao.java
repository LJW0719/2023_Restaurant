package com.example.restaurant.manager.dao;

import com.example.restaurant.manager.domain.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManagerDao {
    public Manager findByEmail(String email);
    public int addManager(Manager manager);
    public Manager loginManager(@Param("email") String email, @Param("password") String password);
}
