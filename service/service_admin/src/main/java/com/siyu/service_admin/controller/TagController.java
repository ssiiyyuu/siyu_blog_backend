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
import com.siyu.service_admin.entity.Tag;
import com.siyu.service_admin.entity.dto.TagDto;
import com.siyu.service_admin.service.TagService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="标签信息管理")
@RestController
@RequestMapping("/service_admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping
    @OperatorLogger(OperatorLoggerEnum.EDIT_TAG)
    public Result saveOrUpdateTag(@RequestBody Tag tag) {
        boolean flag = tagService.saveOrUpdate(tag);
        redisUtil.clearTagCache();
        return flag ? Result.ok().data("id", tag.getId()) : Result.fail();
    }

    @DeleteMapping("/{id}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_TAG)
    public Result deleteTagById(@PathVariable String id) {
        boolean flag = tagService.deleteTagById(id);
        redisUtil.clearTagCache();
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @DeleteMapping("/list/{ids}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_TAG)
    public Result deleteTagByIds(@PathVariable String ids) {
        boolean flag = tagService.deleteTagByIds(Arrays.asList(ids.split(",")));
        redisUtil.clearTagCache();
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @GetMapping("/list/all")
    public Result getTagList() {
        List<Tag> tags = tagService.list();
        return Result.ok().data("records", tags);
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getTagPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        PageInfo<TagDto> tagPageInfo = tagService.getTagPageInfo(pageNum, pageSize);
        return Result.ok().data("curPage", tagPageInfo.getPageNum())
                          .data("total", tagPageInfo.getTotal())
                          .data("size", tagPageInfo.getPageSize())
                          .data("records", tagPageInfo.getList());
    }
    @GetMapping("/list/query/{pageNum}/{pageSize}")
    public Result getTagPageByQuery (@RequestParam String query, @PathVariable int pageNum, @PathVariable int pageSize) {
        PageInfo<TagDto> tagPageInfo = tagService.getTagPageInfoByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", tagPageInfo.getPageNum())
                          .data("total", tagPageInfo.getTotal())
                          .data("size",  tagPageInfo.getPageSize())
                          .data("records", tagPageInfo.getList());
    }

    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/excel")
    public void download(HttpServletResponse response) throws IOException {
        List<Tag> excel = tagService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("TAG", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Tag.class)
                 .sheet("标签列表")
                 .doWrite(excel);
    }
}
