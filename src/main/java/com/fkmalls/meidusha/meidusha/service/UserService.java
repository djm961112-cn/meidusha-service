package com.fkmalls.meidusha.meidusha.service;

import com.fkmalls.meidusha.meidusha.dto.UserDto;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class UserService implements UserDto {

    @Autowired
    private UserDto userDto;

    @Override
    public List<UserEntity> selectUserByUserNameAndPassword(String userName,String password){
        return userDto.selectUserByUserNameAndPassword(userName,password);
    }

    @Override
    public int updateUserToken(String userToken,String userName){
        return userDto.updateUserToken(userToken,userName);
    }

    @Override
    public List<UserEntity> selectUserByToken(String userToken){
        return userDto.selectUserByToken(userToken);
    }

    @Override
    public int fixLogOutState(int state,Long id){
        return userDto.fixLogOutState(state, id);
    }

    @Override
    public int delUserToken(Long id){
        return userDto.delUserToken(id);
    }

    @Override
    public int fixLoginState(int state,String userName){
        return userDto.fixLoginState(state,userName);
    }

    @Override
    public Long selectIdByToken(String token){
        return userDto.selectIdByToken(token);
    }
    @Override
    public List<UserEntity> findUserByUserId(Long id){
        return userDto.findUserByUserId(id);
    }
    @Override
    public int updateUserHeader(String imagePath,Long id){
        return userDto.updateUserHeader(imagePath,id);
    }
    @Override
    public List<UserEntity> findAllUser(){
        /**
         * 查询所有未删除用户
         */
        return userDto.findAllUser();
    }
    @Override
    public List<UserEntity> findUserListByRoleId(Long roleId){
        return userDto.findUserListByRoleId(roleId);
    }

    @Override
    public int registerUser(UserEntity userEntity){
        return userDto.registerUser(userEntity);
    }

    @Override
    public List<UserEntity> findEmailsList(){
        return userDto.findEmailsList();
    }

    @Override
    public List<UserRoleEntity> fingAllRole(){
        return userDto.fingAllRole();
    }

    @Override
    public String findTitle(int id){
        return userDto.findTitle(id);
    }
}
