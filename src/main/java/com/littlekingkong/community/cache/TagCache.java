package com.littlekingkong.community.cache;

import com.littlekingkong.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/10 16:22*@since 1.0.0
 */

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("后端");
        program.setTags(Arrays.asList("php","java","node.js","python","golang","c++","c", "后端","spring","springboot","django","flask","swoole","ruby","asp.net","ruby-on-rails","scala","rust","lavarel", "爬虫"));
        tagDTOS.add(program);

        TagDTO web = new TagDTO();
        web.setCategoryName("前端");
        web.setTags(Arrays.asList("javascript","前端","vue.js","css","html","html5","node.js","react.js","jquery","css3","es6","typescript","chrome","npm","bootstrap","sass","less","chrome-devtools","angular","firefox","coffeescript","safari","yarn","postman","postcss","fiddler","webkit","firebug","edge"));
        tagDTOS.add(web);

        TagDTO sqlName = new TagDTO();
        sqlName.setCategoryName("数据库");
        sqlName.setTags(Arrays.asList("mysql","redis","数据库","sql","mongodb","json","elasticsearch","nosql","memcached","postgresql","sqlite ","mariadb"));
        tagDTOS.add(sqlName);

        TagDTO operation = new TagDTO();
        operation.setCategoryName("运维");
        operation.setTags(Arrays.asList("linux","nginx","docker","运维","apache","centos","ubuntu","服务器","负载均衡","ssh","容器","jenkins","vagrant","devops","debian","fabric"));
        tagDTOS.add(operation);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","mac","os","visual-studio-code","windows","vim","sublime-text","intellij-idea","phpstorm","eclipse","编辑器","webstorm","svn","visual-studio","pycharme","macs"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        // 拿到出错的集合
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}


