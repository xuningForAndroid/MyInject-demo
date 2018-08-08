package com.xn.dlib;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class XnViewInject {
    /**
     * 保存传入的activity
     */
    private  static Class<?> activityClass;

    /**
     * 你需要初始化的Activity
     * @param object
     */
    public static void inject(Object object){
        activityClass=object.getClass();
        injectContentView(object);
        injectView(object);
        injectClickView(object);
    }

    private static void injectClickView(Object activityOrFragment){
        Method[] methods=null;
        methods=activityClass.getMethods();
        for (Method method:methods){//遍历所有的activity下的方法
            XnClickView fmyClickView = method.getAnnotation(XnClickView.class);
            //如果存在此注解
            if (fmyClickView!=null){
                int[] ids = fmyClickView.value();
                MInvocationHandler mInvocationHandler = new MInvocationHandler(activityOrFragment, method);
                Object newProxyInstance = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                        new Class<?>[]{View.OnClickListener.class},mInvocationHandler);
                for (int i:ids){
                    Object view=null;
                    try {
                        view = activityClass.getMethod("findViewById", int.class).invoke(activityOrFragment, i);
                        if (view!=null){
                            Method method2 = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                            method2.invoke(view,newProxyInstance);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectView(Object activityOrFragment) {
        Field[] declaredFields=null;
        if (activityClass!=null){
            declaredFields=activityClass.getDeclaredFields();
        }
        if (declaredFields!=null){
            for (Field field:declaredFields){
                XnFindView fmyFindView = field.getAnnotation(XnFindView.class);
                if (fmyFindView!=null){
                    int viewId = fmyFindView.value();
                    Object obj=null;
                    try {
                        obj= activityClass.getMethod("findViewById",int.class)
                                .invoke(activityOrFragment,viewId);
                        if (Modifier.PUBLIC !=field.getModifiers()){
                            field.setAccessible(true);//打破封装性
                        }
                        field.set(activityOrFragment,obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 初始化activity的布局文件，让其不用调用setCOntentView
     */
    private static void injectContentView(Object object){
        XnContentView annotation=activityClass.getAnnotation(XnContentView.class);
        if (annotation!=null){
            int id = annotation.value();
            try {
                Method setContentViewMethod = activityClass.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(object,id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //利用代理处理点击逻辑
    static class MInvocationHandler implements InvocationHandler{
        //传入的activity
        private Object target;
        //用户自定义view的点击事件方法
        private Method method;

        public MInvocationHandler(Object target,Method method){
            super();
            this.method=method;
            this.target=target;
        }
        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            return this.method.invoke(target,objects);
        }

    }

}
