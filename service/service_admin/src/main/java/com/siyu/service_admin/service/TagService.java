package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.Tag;
import com.siyu.service_admin.entity.dto.TagDto;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-19 02:29:03
 */
public interface TagService extends IService<Tag> {

    public Boolean deleteTagById(String id);

    public Boolean deleteTagByIds(List<String> ids);

    public PageInfo<TagDto> getTagPageInfo(int pageNum, int pageSize);

    public PageInfo<TagDto> getTagPageInfoByQuery(String query, int pageNum, int pageSize);

}
