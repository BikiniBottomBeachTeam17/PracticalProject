package com.mustar.mapper;

import com.mustar.entity.Datas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mustar.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mustar
 * @since 2022-04-29
 */
public interface DatasMapper extends BaseMapper<Datas> {
    @Select("SELECT items.*,datas.price,datas.querytime\n" +
            "FROM items LEFT JOIN datas\n" +
            "ON items.uuid=datas.uuid\n" +
            "RIGHT JOIN (\n" +
            "SELECT items.uuid,MAX(datas.querytime) time \n" +
            "FROM items Left JOIN datas \n" +
            "ON datas.uuid=items.uuid \n" +
            "GROUP BY items.uuid) new\n" +
            "ON items.uuid=new.uuid\n" +
            "WHERE new.time=datas.querytime")
    List<Product> getProduct();
    @Select("SELECT items.*,datas.price,datas.querytime\n" +
            "FROM items LEFT JOIN datas\n" +
            "ON items.uuid=datas.uuid\n" +
            "RIGHT JOIN (\n" +
            "SELECT items.uuid,MAX(datas.querytime) time \n" +
            "FROM items Left JOIN datas \n" +
            "ON datas.uuid=items.uuid \n" +
            "GROUP BY items.uuid) new\n" +
            "ON items.uuid=new.uuid\n" +
            "WHERE new.time=datas.querytime\n" +
            "AND items.uuid='${uuid}'")
    Product getProductInfo(@Param("uuid")String uuid);

}
