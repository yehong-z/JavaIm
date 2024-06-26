package com.zyh.javaim.logic.dao.mapper;

import com.zyh.javaim.logic.dao.entity.Friend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_friend
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_friend
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int insert(Friend row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_friend
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int insertSelective(Friend row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_friend
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    Friend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_friend
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int updateByPrimaryKeySelective(Friend row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table im_friend
     *
     * @mbg.generated Tue Mar 26 12:19:23 CST 2024
     */
    int updateByPrimaryKey(Friend row);
}