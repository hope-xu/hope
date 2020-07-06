package com.controller;

import com.utils.logutil.EagleEye;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hope
 * Date： 2020/07/03  16:12
 * 描述：
 */
@RestController
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @EagleEye(desc = "hello接口测试")
    @RequestMapping(value = "/hello")
    public String testHello(){

        String requestURI = "12345678";
        return "hello:"+requestURI;
    }

}
