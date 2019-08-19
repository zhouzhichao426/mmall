package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("shoppingService")
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;

    /**
     * 新建收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map resultMap = Maps.newHashMap();
            resultMap.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",resultMap);
        }
        return ServerResponse.createByErrorMsg("新建地址失败");
    }

    /**
     * 删除收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<String> delete(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        //
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMsg("删除地址失败");
    }

    /**
     * 更新收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        System.err.println(shipping.getId());
        int rowCount = shippingMapper.updateByShipping(shipping);
        System.err.println(rowCount);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMsg("更新地址失败");
    }

    /**
     * 查询收货地址详情
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if (shipping ==null) {
            return ServerResponse.createByErrorMsg("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("查询地址成功",shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, int page, int size) {
        PageHelper.startPage(page,size);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
