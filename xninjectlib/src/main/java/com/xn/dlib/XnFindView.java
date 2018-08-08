package com.xn.dlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//用于属性
@Retention(RetentionPolicy.RUNTIME)
public @interface XnFindView {
    int  value();//控件的id
}
