package com.controller.base;

import com.qhgctech.common.exception.BaseException;
import com.qhgctech.common.ret.Result;
import com.qhgctech.core.domain.BaseDomain;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController<T extends BaseDomain> {
    private static Logger log = Logger.getLogger(BaseController.class);
    public static final String DEFAULT_ERROR_VIEW = "error";

    // 异常信息拦截，统一处理返回，为了兼容IE8，用流方式输出
    @ExceptionHandler(Exception.class) // 在Controller类中添加该注解方法即可(注意：添加到某个controller，只针对该controller起作用)
    public ModelAndView exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request) {
        // ResultDataDto resultDataDto = new ResultDataDto(ex);
        // response.setContentType("text/html;charset=utf-8");
        // response.getWriter().write(DataUtils.getGson().toJson(resultDataDto));
        log.info("异常信息拦截，统一处理返回", ex);
        ModelAndView mav = new ModelAndView();
        if (ex instanceof BaseException) {
            mav.addObject("exception", ex);
        } else {
            mav.addObject("exception", new BaseException("999999", "访问系统异常，请联系系统管理员", ex));
        }
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result<String> jsonErrorHandler(HttpServletRequest req, Exception e) {
        log.info("异常信息拦截，统一处理返回", e);
        Result<String> resultDataDto = new Result<String>();
        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            resultDataDto.setMsg(be.getMessage());
            resultDataDto.setCode(be.getCode());
            resultDataDto.setData("No Data");
        } else {
            resultDataDto.setMsg("访问系统异常，请联系系统管理员");
            resultDataDto.setCode("999999");
            resultDataDto.setData("No Data");
        }
//		resultDataDto.setUrl(req.getRequestURL().toString());
        return resultDataDto;
    }

}
