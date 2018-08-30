package com.obm.hy.export.utils;

import java.lang.reflect.Field;

/**
 * Created by and on 2017-08-16.
 */

public class ReflectUtil {

    /**
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     * @MethodName : getFieldByName
     * @Description : 根据字段名获取字段
     */
    public static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类所有的字段
        Field[] selfFields = clazz.getDeclaredFields();

        // 如果本类中存放该字段，则返回
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }

        // 如果本来和父类都没有该字段，则返回null
        return null;
    }

    /**
     * @param fieldName 字段名
     * @param o         对象
     * @return 字段值
     * @MethodName : getFieldValueByName
     * @Description : 根据字段名获取字段值
     */
    public static Object getFieldValueByName(String fieldName, Object o) throws Exception {
        Object value = null;
        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null) {
            field.setAccessible(true);
            value = field.get(o);
        } else {

        }
        if(value==null){
            value="";
        }
        return value;
    }

}
