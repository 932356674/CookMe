package com.gss.mapper;

import com.gss.entity.Booktype;
import com.gss.entity.BooktypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BooktypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int countByExample(BooktypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int deleteByExample(BooktypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int insert(Booktype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int insertSelective(Booktype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    List<Booktype> selectByExample(BooktypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    Booktype selectByPrimaryKey(Integer typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int updateByExampleSelective(@Param("record") Booktype record, @Param("example") BooktypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int updateByExample(@Param("record") Booktype record, @Param("example") BooktypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int updateByPrimaryKeySelective(Booktype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booktype
     *
     * @mbggenerated Tue Apr 09 21:23:38 CST 2019
     */
    int updateByPrimaryKey(Booktype record);
}