package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.NotificationDTO;
import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.model.User;

public interface NotificationService {

    PaginationDTO list(Long id, Integer page, Integer size);

    Long unReadCount(Long id);

    NotificationDTO read(Long id, User user);
}
