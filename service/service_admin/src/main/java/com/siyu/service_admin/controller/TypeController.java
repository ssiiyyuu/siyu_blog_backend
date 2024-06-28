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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.siyu.common_static.enums.OperatorLoggerEnum;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_admin.annotation.OperatorLogger;
import com.siyu.service_admin.entity.Type;
import com.siyu.service_admin.entity.dto.TypeDto;
import com.siyu.service_admin.service.TypeService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="分类信息管理")
@RestController
@RequestMapping("/service_admin/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private RedisUtil redisUtil;
    
    @PostMapping
    @OperatorLogger(OperatorLoggerEnum.EDIT_TYPE)
    public Result saveOrUpdateType(@RequestBody Type type) {
        boolean flag = typeService.saveOrUpdate(type);
        redisUtil.clearTypeCache();
        return flag ? Result.ok().data("id", type.getId()) : Result.fail();
    }

    @DeleteMapping("/{id}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_TYPE)
    public Result deleteTypeById(@PathVariable String id) {
        boolean flag = typeService.removeById(id);
        redisUtil.clearTypeCache();
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @DeleteMapping("/list/{ids}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_TYPE)
    public Result deleteTypeByIds(@PathVariable String ids) {
        boolean flag = typeService.removeByIds(Arrays.asList(ids.split(",")));
        redisUtil.clearTypeCache();
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @GetMapping("/list/all")
    public Result getTagList() {
        List<Type> types = typeService.list();
        return Result.ok().data("records", types);
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getTypePage(@PathVariable int pageNum, @PathVariable int pageSize) {
        PageInfo<TypeDto> typePageInfo = typeService.getTypePageInfo(pageNum, pageSize);
        return Result.ok().data("curPage", typePageInfo.getPageNum())
                          .data("total", typePageInfo.getTotal())
                          .data("size", typePageInfo.getPageSize())
                          .data("records", typePageInfo.getList());
    }
    
    @GetMapping("/list/query/{pageNum}/{pageSize}")
    public Result getTypePageByQuery (@RequestParam String query, @PathVariable int pageNum, @PathVariable int pageSize) {
        PageInfo<TypeDto> typePageInfo = typeService.getTypePageInfoByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", typePageInfo.getPageNum())
                          .data("total", typePageInfo.getTotal())
                          .data("size",  typePageInfo.getPageSize())
                          .data("records", typePageInfo.getList());
    }

    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/excel")
    public void download(HttpServletResponse response) throws IOException {
        List<Type> excel = typeService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("TYPE", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Type.class)
                 .sheet("分类列表")
                 .doWrite(excel);
    }
}
