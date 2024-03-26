package com.zyh.javaim.logic.dao.mapper;

import com.zyh.javaim.logic.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table proj_users
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table proj_users
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int insert(User row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table proj_users
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int insertSelective(User row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table proj_users
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table proj_users
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int updateByPrimaryKeySelective(User row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table proj_users
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int updateByPrimaryKey(User row);

    User selectByUserName(String userName);
}