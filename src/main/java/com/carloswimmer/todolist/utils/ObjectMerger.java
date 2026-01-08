package com.carloswimmer.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ObjectMerger {

    public static void merge(Object source, Object target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target cannot be null");
        }

        var nullPropertyNames = getNullPropertyNames(source);

        if (nullPropertyNames != null && nullPropertyNames.length > 0) {
            BeanUtils.copyProperties(source, target, nullPropertyNames);
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        if (source == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] properties = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();

        for (PropertyDescriptor property : properties) {
            String propertyName = property.getName();

            if (propertyName == null) {
                continue;
            }

            Object srcValue = src.getPropertyValue(propertyName);

            if (srcValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames.toArray(new String[0]);
    }
}
