package com.littlekingkong.community.schedule;

import com.littlekingkong.community.cache.HotTagCache;
import com.littlekingkong.community.dao.QuestionMapper;
import com.littlekingkong.community.model.Question;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/15 22:13*@since 1.0.0
 */
@Component
@Slf4j
public class HotTagTasks {

    @Resource
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;
    //@Scheduled(cron = "0 59 23 ? * *" ) //使用cron属性可按照指定时间执行，本例指的是每天20点07分执行；
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)//通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间执行
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();

        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.listQuestion2(offset, limit);
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    priorities.put(tag,priorities.getOrDefault(tag,0) + 5 + question.getComment_count() + question.getView_count());
                }
            }
            offset += limit;
        }


        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule stop {}", new Date());
    }



}
