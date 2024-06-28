package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.User;
import com.siyu.service_admin.entity.dto.UserQueryDto;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-17 04:26:42
 */
public interface UserService extends IService<User> {

    public String login(String username, String password);

    public Map<String, Object> info(String token);

    public Page<User> getUserPage(int pageNum, int pageSize);

    public Page<User> getUserPageByQuery(UserQueryDto query, int pageNum, int pageSize);

    public boolean saveOrUpdateUser(User user);
}
