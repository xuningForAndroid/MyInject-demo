package com.xn.dlib;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//只能在类中使用此注解
@Retention(RetentionPolicy.RUNTIME)//注解可以在运行时通过反射获取一些信息
@Documented
public @interface XnContentView {
    /**
     * 保存布局文件的id R.layout.main
     * @return 返回布局id
     */
    int value();

}

