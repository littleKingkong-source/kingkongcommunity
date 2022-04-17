package com.littlekingkong.community.dao;

import com.littlekingkong.community.model.Ad;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/16 17:11*@since 1.0.0
 */

public interface AdMapper {

    //List<Ad> selectByStatus(Integer status, Long gmt_start, Long gmt_end);
    List<Ad> selectById(Integer id);

    List<Ad> selectByStatus(Integer status,@Param("time") Long time);
}
