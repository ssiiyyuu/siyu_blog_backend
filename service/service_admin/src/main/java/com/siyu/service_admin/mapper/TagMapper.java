package com.siyu.service_admin.mapper;

import com.siyu.service_admin.entity.Tag;
import com.siyu.service_admin.entity.dto.TagDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-19 02:29:03
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    
    public List<TagDto> selectTagList();

    public List<TagDto> selectTagListByQuery(@Param("query") String query);
}
