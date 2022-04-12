package com.littlekingkong.community.dao;

import com.littlekingkong.community.model.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {

    Integer countById(Long id);

    List<Notification> listUserQuestion(Long userId, Integer offset, Integer size);

    void insert(Notification notification);


    Long unReadCount(Long id);

    Notification selectById(Long id);

    void updataStatus(Notification notification);
}
