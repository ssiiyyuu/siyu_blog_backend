package com.siyu.service_admin.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siyu.common_static.enums.OperatorLoggerEnum;
import com.siyu.service_admin.annotation.OperatorLogger;
import com.siyu.service_admin.entity.User;
import com.siyu.service_admin.entity.dto.UserQueryDto;
import com.siyu.service_admin.service.UserService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="用户信息管理")
@RestController
@RequestMapping("/service_admin/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping
    @OperatorLogger(OperatorLoggerEnum.EDIT_USER)
    public Result saveOrUpdateUser(@RequestBody User user) {
        boolean flag = userService.saveOrUpdateUser(user);
        return flag ? Result.ok().data("id", user.getId()) : Result.fail();
    }

    @DeleteMapping("/{id}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_USER)
    public Result deleteUserById(@PathVariable String id) {
        boolean flag = userService.removeById(id);
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @DeleteMapping("/list/{ids}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_USER)
    public Result deleteUserByIds(@PathVariable String ids) {
        boolean flag = userService.removeByIds(Arrays.asList(ids.split(",")));
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getUserPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        Page<User> userPage = userService.getUserPage(pageNum, pageSize);
        return Result.ok().data("curPage", userPage.getCurrent())
                          .data("total", userPage.getTotal())
                          .data("size", userPage.getSize())
                          .data("records", userPage.getRecords());
    }
    
    @GetMapping("/list/query/{pageNum}/{pageSize}")
    public Result getUserPageByQuery (UserQueryDto query, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<User> userPage = userService.getUserPageByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", userPage.getCurrent())
                          .data("total", userPage.getTotal())
                          .data("size", userPage.getSize())
                          .data("records", userPage.getRecords());
    }

    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/excel")
    public void download(HttpServletResponse response) throws IOException {
        List<User> excel = userService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("USER", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), User.class)
                 .sheet("用户列表")
                 .doWrite(excel);
    }
}
