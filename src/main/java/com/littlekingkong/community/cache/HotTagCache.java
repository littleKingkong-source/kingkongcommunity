package com.littlekingkong.community.cache;

import com.littlekingkong.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/15 23:29*@since 1.0.0
 */
@Data
@Component
public class HotTagCache {

    private List<String> hots = new ArrayList<>();

    // 使用优先队列进行 hottag排序
    public void updateTags(Map<String, Integer> tags) {
        int max = 8;
        // 维护一个最小堆
        PriorityQueue<HotTagDTO> queue = new PriorityQueue<>(max);
        tags.forEach((name, priority)->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (queue.size() < max) {
                queue.add(hotTagDTO);
            } else {
                HotTagDTO minHot = queue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    queue.poll();
                    queue.add(hotTagDTO);
                }
            }
        });
        List<String> sortedTags = new ArrayList<>();
        HotTagDTO poll = queue.poll();
        while (poll != null) {
            sortedTags.add(0, poll.getName());
            poll = queue.poll();
        }
        hots = sortedTags;
    }
}
