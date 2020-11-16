package com.yehui.starter.sample;

import com.yehui.starter.starter.School;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yehui
 * @date 2020/11/16
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Resource
    private School school;

    @RequestMapping("/ding")
    public Boolean ding() {
        school.ding();
        return true;
    }

}
