package com.mustar.service.impl;

import com.mustar.entity.Items;
import com.mustar.mapper.ItemsMapper;
import com.mustar.service.IItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mustar
 * @since 2022-04-29
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements IItemsService {

}
