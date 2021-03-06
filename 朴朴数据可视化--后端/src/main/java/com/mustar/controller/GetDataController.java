package com.mustar.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mustar.common.lang.Result;
import com.mustar.entity.Datas;
import com.mustar.entity.Items;
import com.mustar.entity.Product;
import com.mustar.mapper.DatasMapper;
import com.mustar.mapper.ItemsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/getData")
public class GetDataController {
    @Autowired
    private DatasMapper datasMapper;
    @Autowired
    private ItemsMapper itemsMapper;
    //获取数据并保存到数据库
    @PostMapping("/saveData")
    public Result saveData(@RequestBody Product product){
        //判断数据库中是否存在该商品
        if (itemsMapper.selectOne(Wrappers.<Items>lambdaQuery().eq(Items::getUuid, product.getUuid()))!=null){
            //存在查看是否有当天数据
            if (datasMapper.selectOne(Wrappers.<Datas>lambdaQuery()
                    .eq(Datas::getUuid, product.getUuid())
                    .eq(Datas::getQuerytime, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))))!=null){
                return Result.error(-1,"该商品已有当日数据，不予以更新");
            }
            //存在则添加价格表
            Datas datas=new Datas();
            datas.setUuid(product.getUuid());
            datas.setPrice(product.getPrice());
            datas.setQuerytime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            if(datasMapper.insert(datas)!=0){
                return Result.success("该商品已存在数据库中，价格更新成功");
            }else {
                return Result.error(-1,"该商品已存在数据库中，价格更新失败");
            }
        }else{
            //不存在则添加商品表并且添加价格表
            Items items=new Items();
            items.setUuid(product.getUuid());
            items.setName(product.getName());
            items.setSubtitle(product.getSubTitle());
            items.setOrigin(product.getOrigin());
            items.setImg(product.getImg());
            if (itemsMapper.insert(items)!=0){
                Datas datas=new Datas();
                datas.setUuid(product.getUuid());
                datas.setPrice(product.getPrice());
                datas.setQuerytime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                if(datasMapper.insert(datas)!=0){
                    return Result.success("该商品已添加到数据库中，且价格更新成功");
                }else {
                    return Result.error(-1,"该商品已添加到数据库中，价格更新失败");
                }
            }else {
                return Result.error(-1, "该商品添加到数据库失败");
            }
        }
    }
    @PostMapping("/save")
    public Result saveOneData(@RequestBody Datas datas){
        Datas data=new Datas();
        data.setUuid(datas.getUuid());
        data.setPrice(datas.getPrice());
        data.setQuerytime(datas.getQuerytime());
        if (datasMapper.insert(data)!=0){
            return Result.success("数据存储成功");
        }
        return Result.error(-1,"数据存储失败");
    }
    @GetMapping("/getPrice")
    public Result getOneData(@RequestParam String uuid){
        List<Datas> dataList = datasMapper.selectList(Wrappers.<Datas>lambdaQuery().eq(Datas::getUuid, uuid));
        return Result.success(dataList);
    }
    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam String uuid){
        Items items = itemsMapper.selectOne(Wrappers.<Items>lambdaQuery().eq(Items::getUuid, uuid));
        Product productInfo = datasMapper.getProductInfo(uuid);
        return Result.success(MapUtil.builder()
                .put("name", items.getName())
                .put("subtitle", items.getSubtitle())
                .put("origin", items.getOrigin())
                .put("img", items.getImg())
                .put("price", productInfo.getPrice())
                .map());
    }
    @GetMapping("/getList")
    public Result getListData(){
        List<Product> list=datasMapper.getProduct();
        return Result.success(list);
    }
    @PostMapping("/delete")
    public Result deleteData(@RequestBody String uuid){
        if (datasMapper.delete(Wrappers.<Datas>lambdaQuery().eq(Datas::getUuid,uuid))!=0||
                itemsMapper.delete(Wrappers.<Items>lambdaQuery().eq(Items::getUuid,uuid))!=0){
            return Result.success("删除成功");
        }
        return Result.success("删除失败");
    }
    @GetMapping("/search")
    public Result search(@RequestParam String name){
        List<Product> list=datasMapper.getProductByName(name);
        return Result.success(list);
    }






   
}
