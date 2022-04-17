package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.AdMapper;
import com.littlekingkong.community.model.Ad;
import com.littlekingkong.community.service.AdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/16 17:09*@since 1.0.0
 */
@Service
public class AdServiceImpl implements AdService {

    @Resource
    private AdMapper adMapper;
    @Override
    public List<Ad> list() {
        Long time = System.currentTimeMillis();
        System.out.println(adMapper.selectByStatus(1,time));
//        return adMapper.selectByStatus(1,System.currentTimeMillis(),System.currentTimeMillis());
        return adMapper.selectByStatus(1,time);
    }
}
