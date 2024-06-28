package com.siyu.common_utils.RedisUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.siyu.common_static.constant.RedisConstant;


@Service
public class RedisUtil{
    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    /** -------------------value相关操作--------------------- */

    /**
     * value存数据
     * 
     * @param key
     * @param value
     */
    public void saveToValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * value数据根据key自增increment
     * 
     * @param key
     * @param increment
     */
    public void incrementToValue(String key, Integer increment) {
       redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 从value根据key取数据
     * 
     * @param key
     * @return
     */
    public Object getFromValue(String key) {
       return redisTemplate.opsForValue().get(key);
    }

    /** -------------------set相关操作--------------------- */

    /**
     * set存数据
     * 
     * @param key
     * @param value
     */
    public void saveToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 判断set中是否有value
     * 
     * @param key
     * @param value
     * @return
     */
    public Boolean isInSet(String key, Object value) {
       return redisTemplate.opsForSet().isMember(key, value);
    }

    /** -------------------list相关操作--------------------- */
    
    /**
     * list数据结构存list
     * 
     * @param <T>
     * @param key
     * @param list
     */
    public <T> void saveToList(String key, List<T> list) {
        redisTemplate.opsForList().rightPushAll(key, list.toArray());
    }

    /**
     * 从list根据key取所有数据
     * 
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public <T> List<T> getFromList(String key, Class<T> clazz) {
        List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
        if(list == null || list.size() == 0) {
            return null;
        }
        List<T> res = list.stream().map(item -> clazz.cast(item)).collect(Collectors.toList());
        return res;
    }
    
    /** -------------------hash相关操作--------------------- */

    /**
     * 根据key向hash的hashkey存object
     * 
     * @param key
     * @param hashKey
     * @param object
     */
    public void saveToHashByHashKey(String key, Object hashKey, Object object) {
        redisTemplate.opsForHash().put(key, hashKey, object);
    }

    /**
     * 根据key向hash的hashkey自增increment
     * 
     * @param key
     * @param hashKey
     * @param increment
     */
    public void incrementToHashByHashKey(String key, String hashKey, Integer increment) {
        redisTemplate.opsForHash().increment(key, hashKey, increment);
    }

    /**
     * 从hash根据key 取出hashKey为pageNum的page
     * 
     * @param <T>
     * @param key
     * @param pageNum
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> PageInfo<T> getPageInfoFromHash(String key, int pageNum) {
        Object object = redisTemplate.opsForHash().get(key, pageNum);
        if(object == null ){
            return null;
        }
        PageInfo<T> pageInfo = (PageInfo<T>) object;
        return pageInfo;
    }

    /**
     * 从hash根据key 取出所有数据
     * 
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public <T>Map<String, List<T>> getFromHash(String key, Class<T> clazz) {
        Map<String, List<T>> res = new HashMap<>();
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        if(map.size() == 0) {
            return null;
        }
        map.entrySet().forEach(
            entry -> {
                String k = (String) entry.getKey();
                List<T> v = new ArrayList<>();
                Object object = entry.getValue();
                if(v instanceof List<?>) {
                    for (Object o : (List<?>) object) {
                        v.add(clazz.cast(o));
                    }
                }
                res.put(k, v);
            }
        );
        return res;
    }

    public Map<Object, Object> getValueFromHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /** -------------------key相关操作--------------------- */

    /**
     * 设置key的过期时间(seconds)
     * 
     * @param key
     * @param time
     */
    public void expire(String key, int time) {
       redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    
    /** -------------------删除首页缓存相关操作--------------------- */
    public void clearBlogCache() {
        redisTemplate.delete(RedisConstant.HOME_PAGE_HASH_KEY);
        redisTemplate.delete(RedisConstant.NEW_BLOG_LIST_KEY);
    }

    public void clearVisitorCache() {
        redisTemplate.delete(RedisConstant.VISITOR_LIST_KEY);
    }
    
    public void clearTagCache() {
        redisTemplate.delete(RedisConstant.TAG_LIST_KEY);
    }
    
    public void clearTypeCache() {
        redisTemplate.delete(RedisConstant.TYPE_LIST_KEY);
    }
    
    public void clearIdentifierCache() {
        redisTemplate.delete(RedisConstant.IDENTIFIER_SET_KEY);
    }

    public void clearBlogViewCache() {
        redisTemplate.delete(RedisConstant.BLOG_VIEW_HASH_KEY);
    }
}
