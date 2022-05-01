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
   
}
