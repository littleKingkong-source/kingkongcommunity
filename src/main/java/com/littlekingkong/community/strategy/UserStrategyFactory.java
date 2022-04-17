package com.littlekingkong.community.strategy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/17 12:51*@since 1.0.0
 */
@Service
public class UserStrategyFactory {

    @Autowired
    private List<UserStrategy> strategies;

    public UserStrategy getStrategy(String type) {
        for (UserStrategy strategy : strategies) {
            if (StringUtils.equals(strategy.getSupportedType(),type)) {
                return strategy;
            }
        }
        return null;
    }

}
