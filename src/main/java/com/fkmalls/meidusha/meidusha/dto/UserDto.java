package com.fkmalls.meidusha.meidusha.dto;

import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.entity.UserRoleEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 */
@Mapper
public interface UserDto {
    //SELECT * FROM `user` WHERE user_name='djm' AND user_password='123456';
    @Select(value = "SELECT * FROM `user` WHERE user_name=#{userName} AND password=#{password};")
    List<UserEntity> selectUserByUserNameAndPassword(
            /**
             * 通过用户名和密码查询用户
             */
            @Param("userName") String userName,
            @Param("password") String password
    );

    //UPDATE `meidusha_case`.`user` SET `user_token` = 'djm' WHERE `user_name`='djm';
    @Update(value = "UPDATE `meidusha_case`.`user` SET `user_token` = #{userToken} WHERE `user_name`=#{userName};")
    int updateUserToken(
            /**
             * 更新用户token
             */
            @Param("userToken") String userToken,
            @Param("userName") String userName
    );

    //SELECT * FROM `meidusha_case`.`user` WHERE `user_token` ='djm';
    @Select(value = "SELECT * FROM `meidusha_case`.`user` WHERE `user_token` =#{userToken};")
    List<UserEntity> selectUserByToken(
            /**
             *  通过token查询用户信息
             */
            @Param("userToken") String userToken
    );

    //UPDATE `meidusha_case`.`user` SET `state` = 1 WHERE `id`=1;
    @Update(value = "UPDATE `meidusha_case`.`user` SET `state` = #{state} WHERE `id`=#{id};")
    int fixLogOutState(
            /**
             *  修改退出状态
             */
            @Param("state") int state,
            @Param("id") Long id
    );

    //UPDATE `meidusha_case`.`user` SET `user_token` = null WHERE `id`=1;
    @Update(value = "UPDATE `meidusha_case`.`user` SET `user_token` = null WHERE `id`=#{id};")
    int delUserToken(
            /**
             *  删除用户token
             */
            @Param("id") Long id
    );

    //"UPDATE `meidusha_case`.`user` SET `state` = #{state} WHERE `user_name`=#{userName};
    @Update(value = "UPDATE `meidusha_case`.`user` SET `state` = #{state} WHERE `user_name`=#{userName};")
    int fixLoginState(
            /**
             * 修改登陆状态
             */
            @Param("state") int state,
            @Param("userName") String userName
    );

    //SELECT * FROM `user` WHERE user_token='djm123456';
    @Select(value = "SELECT * FROM `user` WHERE user_token=#{token};")
    Long selectIdByToken(
            /**
             * 通过token查询用户信息
             */
            @Param("token") String  token
    );

    @Select(value = "SELECT * FROM `user` WHERE id=#{id};")
    List<UserEntity> findUserByUserId(
            /**
             * 通过用户id查询用户信息
             */
            @Param("id") Long id
    );

    @Update(value = "UPDATE `meidusha_case`.`user` SET `image_path` = #{imagePath} WHERE `id`=#{id};")
    int updateUserHeader(
            /**
             * 更新用户头像
             */
            @Param("imagePath") String imagePath,
            @Param("id") Long id
    );

    @Select(value = " SELECT `us`.id,`us`.nick_name,us.`user_name` FROM `meidusha_case`.`user` us WHERE us.del_tag=0;")
    List<UserEntity> findAllUser(
            /**
             * 查询所有未删除用户
             */
    );

    @Select(value = "SELECT id,nick_name FROM `user` WHERE role_id=#{roleId} and del_tag=0;")
    List<UserEntity> findUserListByRoleId(
            /**
             * 通过角色id查询用户列表
             */
            @Param("roleId") Long roleId
    );

    @Insert(value = "INSERT INTO `meidusha_case`.`user` (`id`,`nick_name`,`user_name`,`password`,`role_id`,`ali_emails`,`ding_mobile`,`create_user`) " +
            "VALUES (#{id},#{nickName},#{userName},#{password},#{roleId},#{aliEmails},#{dingMobile},#{createUser});")
    int registerUser(UserEntity userEntity);

    @Select(value = "SELECT ali_emails FROM `meidusha_case`.`user`  WHERE del_tag=0 AND ali_emails <> \"NULL\"; ")
    List<UserEntity> findEmailsList();

    @Select(value = "SELECT * FROM user_role;")
    List<UserRoleEntity> fingAllRole();

    @Select(value = "SELECT title FROM lizhi_title WHERE id=#{id};")
    String findTitle(@Param("id") int id);
}
