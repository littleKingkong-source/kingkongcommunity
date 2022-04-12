package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.NotificationMapper;
import com.littlekingkong.community.dto.NotificationDTO;
import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.enums.NotificationStatusEnum;
import com.littlekingkong.community.enums.NotificationTypeEnum;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import com.littlekingkong.community.model.Notification;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/11 21:10*@since 1.0.0
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;


    @Override
    public NotificationDTO read(Long id, User user) {
        // 先获取通知
        Notification notification = notificationMapper.selectById(id);
        if (notification.getReceiver() == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver() != user.getId()) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updataStatus(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    // 未读通知数
    @Override
    public Long unReadCount(Long id) {

        return notificationMapper.unReadCount(id);
    }

    @Override
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        // 分页
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();

        // 获取用户被通知的数目
        Integer count = notificationMapper.countById(userId);

        paginationDTO.setPagination(count, page, size);

        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        // 展示的页数，等于 （页数 - 1） * 每页数目
        Integer offset = (page - 1) * size;
        if(offset < 0) {
            offset = 0;
        }
        // 查询指定条数的通知
        List<Notification> notifications = notificationMapper.listUserQuestion(userId,offset, size);
        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);

        return paginationDTO;
    }
}
