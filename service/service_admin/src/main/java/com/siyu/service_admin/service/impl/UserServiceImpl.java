package com.siyu.service_admin.service.impl;

import com.siyu.common_static.constant.ResultCodeConstant;
import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.common_utils.JwtUtil;
import com.siyu.service_admin.entity.User;
import com.siyu.service_admin.entity.dto.UserQueryDto;
import com.siyu.service_admin.mapper.UserMapper;
import com.siyu.service_admin.service.UserService;
import com.siyu.service_base.exceptionHandler.GlobalException;

import io.jsonwebtoken.ExpiredJwtException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-17 04:26:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<User> getUserPage(int pageNum, int pageSize) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        userMapper.selectPage(userPage, null);
        userPage.getRecords().forEach(
            user -> {
                user.setPassword("");
            }
        );
        return userPage;
    }

    @Override
    public Page<User> getUserPageByQuery(UserQueryDto query, int pageNum, int pageSize) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        String name = query.getName();
        String role = query.getRole();
        Boolean status = query.getStatus();
        LocalDateTime begin = query.getBegin();
        LocalDateTime end = query.getEnd();

        if(!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(role)) {
            wrapper.eq("role", role);
        }
        if(status != null) {
            wrapper.eq("status", status);
        }
        if(begin != null && end != null) {
            wrapper.between("gmt_create", begin, end);
        }
        userMapper.selectPage(userPage, wrapper);
        userPage.getRecords().forEach(
            user -> {
                user.setPassword("");
            }
        );
        return userPage;
    }

    @Override
    public String login(String username, String password) {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new GlobalException(ExceptionEnum.ERROR);
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
        
        if(user == null) {
            throw new GlobalException("用户名或密码错误", ResultCodeConstant.FAILURE);
        } 
        //md5加密
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            throw new GlobalException("用户名或密码错误", ResultCodeConstant.FAILURE);
        }
        if(!user.getStatus()) {
            throw new GlobalException("账户已禁用", ResultCodeConstant.FAILURE);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("avatar", user.getAvatar());
        map.put("roles", Arrays.asList(user.getRole()));

        String token = JwtUtil.getToken(map);
        return token;
    }


    @Override
    public boolean saveOrUpdateUser(User user) {
        String id = user.getId();
        String name = user.getName();

        String password = user.getPassword();
        if(StringUtils.isEmpty(id)) { //insert        
            if(userMapper.exists(new QueryWrapper<User>().eq("name", name))) {
                throw new GlobalException(ExceptionEnum.ERROR, "用户名已存在");
            }
            user.setPassword(
                DigestUtils.md5DigestAsHex(password.getBytes())
            );
            return userMapper.insert(user) > 0;
        } else {
            try {
                int res = userMapper.update(null, 
                        new UpdateWrapper<User>().eq("id", id)
                                                .set("name", name)
                                                .set("role", user.getRole())
                                                .set("avatar", user.getAvatar())
                                                .set("status", user.getStatus()));
                return res > 0;      
            } catch(Exception e) {
                throw new GlobalException(ExceptionEnum.ERROR, "用户名已存在");
            }      
        }
    }

    @Override
    public Map<String, Object> info(String token) {
        Map<String, Object> map =  new HashMap<>();
        try {
            map = JwtUtil.parseToken(token);
        } catch(ExpiredJwtException e) {
            e.printStackTrace();
            throw new GlobalException(ExceptionEnum.EXPIRE_TOKEN);
        }
        if(map.isEmpty()) {
            throw new GlobalException(ExceptionEnum.ILLEGAL_TOKEN);
        }
        return map;
    }


}
