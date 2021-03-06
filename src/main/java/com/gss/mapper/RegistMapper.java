package com.gss.mapper;

import com.gss.entity.Regist;
import com.gss.entity.RegistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegistMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int countByExample(RegistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int deleteByExample(RegistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int deleteByPrimaryKey(Integer registId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int insert(Regist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int insertSelective(Regist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    List<Regist> selectByExample(RegistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    Regist selectByPrimaryKey(Integer registId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int updateByExampleSelective(@Param("record") Regist record, @Param("example") RegistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int updateByExample(@Param("record") Regist record, @Param("example") RegistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int updateByPrimaryKeySelective(Regist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table regist
     *
     * @mbggenerated Tue Apr 09 21:38:01 CST 2019
     */
    int updateByPrimaryKey(Regist record);
}