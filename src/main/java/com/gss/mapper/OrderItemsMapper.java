package com.gss.mapper;

import com.gss.entity.OrderItems;
import com.gss.entity.OrderItemsAndProduct;
import com.gss.entity.OrderItemsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderItemsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int countByExample(OrderItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int deleteByExample(OrderItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int deleteByPrimaryKey(Long itemsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int insert(OrderItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int insertSelective(OrderItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    List<OrderItems> selectByExample(OrderItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    OrderItems selectByPrimaryKey(Long itemsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int updateByExampleSelective(@Param("record") OrderItems record, @Param("example") OrderItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int updateByExample(@Param("record") OrderItems record, @Param("example") OrderItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int updateByPrimaryKeySelective(OrderItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_items
     *
     * @mbggenerated Fri Apr 19 14:58:20 CST 2019
     */
    int updateByPrimaryKey(OrderItems record);

    OrderItemsAndProduct findOrderAndPro(OrderItems orderItems);
}