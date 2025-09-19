package br.com.wmtrading.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    public String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd:pds){
            Object scrValue = src.getPropertyValue(pd.getName());
        }
    }
}
