package com.carloswimmer.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.lang.NonNull;

public class ObjectMerger {

    public static void merge(@NonNull Object source, @NonNull Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    @NonNull
    private static String[] getNullPropertyNames(@NonNull Object source) {

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

        String[] result = emptyNames.toArray(new String[0]);

        if (result == null) {
            return new String[0];
        }

        return result;
    }
}
