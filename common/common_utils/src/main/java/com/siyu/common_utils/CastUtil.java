package com.siyu.common_utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public class CastUtil {
    public static <D, V> PageInfo<V> dto2vo(PageInfo<D> source, Class<V> clazz) {
        Page<V> destPage = new Page<>(source.getPageNum(), source.getPageSize());
        destPage.setTotal(source.getTotal());
        source.getList().stream()
                        .forEach(
                            dto -> {
                                try {
                                    V vo =  clazz.getDeclaredConstructor().newInstance();
                                    BeanUtils.copyProperties(dto, vo);
                                    destPage.add(vo);
                                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                                    e.printStackTrace();
                                }
                            }
                        );
        return new PageInfo<V>(destPage);
    }

    public static <T>List<T> object2List(Object object, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if(object instanceof List<?>) {
            for (Object o : (List<?>) object) {
                list.add(clazz.cast(o));
            }
        }
        return list;
    }
}
