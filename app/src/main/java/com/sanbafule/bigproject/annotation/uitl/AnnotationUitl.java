package com.sanbafule.bigproject.annotation.uitl;

import android.app.Activity;
import android.view.View;

import com.sanbafule.bigproject.annotation.ContentView;
import com.sanbafule.bigproject.annotation.ContentWidget;
import com.sanbafule.bigproject.annotation.DynamicHandler;
import com.sanbafule.bigproject.annotation.EventBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/8/1.
 */
public class AnnotationUitl {

    public static void injectObject(Object object, Activity activity) {

        Class<?> classType = object.getClass();

// 该类是否存在ContentView类型的注解
        if (classType.isAnnotationPresent(ContentView.class)) {
// 返回存在的ContentView类型的注解
            ContentView annotation = classType.getAnnotation(ContentView.class);


            try {


// 返回一个 Method 对象，它反映此 Class 对象所表示的类或接口的指定公共成员方法。
                Method method = classType
                        .getMethod("setContentView", int.class);
                method.setAccessible(true);
                int resId = annotation.value();
                method.invoke(object, resId);


            } catch (NoSuchMethodException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        /**
         *
         *
         * 在此使用for循环和反射 降低效率
         *
         */

// 返回 Field 对象的一个数组，这些对象反映此 Class 对象表示的类或接口声明的成员变量，
// 包括公共、保护、默认（包）访问和私有成员变量，但不包括继承的成员变量。
        Field[] fields = classType.getDeclaredFields();


        if (null != fields && fields.length > 0) {


            for (Field field : fields) {
// 该成员变量是否存在ContentWidget类型的注解
                if (field.isAnnotationPresent(ContentWidget.class)) {


                    ContentWidget annotation = field
                            .getAnnotation(ContentWidget.class);
                    int viewId = annotation.value();
                    View view = activity.findViewById(viewId);
                    if (null != view) {
                        try {
                            field.setAccessible(true);
                            field.set(object, view);
                        } catch (IllegalArgumentException e) {
// TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
// TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }


                }


            }


        }


    }


    private static void injectEvents(Activity activity)
    {

        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        //遍历所有的方法
        for (Method method : methods)
        {
            Annotation[] annotations = method.getAnnotations();
            //拿到方法上的所有的注解
            for (Annotation annotation : annotations)
            {
                Class<? extends Annotation> annotationType = annotation
                        .annotationType();
                //拿到注解上的注解
                EventBase eventBaseAnnotation = annotationType
                        .getAnnotation(EventBase.class);
                //如果设置为EventBase
                if (eventBaseAnnotation != null)
                {
                    //取出设置监听器的名称，监听器的类型，调用的方法名
                    String listenerSetter = eventBaseAnnotation
                            .listenerSetter();
                    Class<?> listenerType = eventBaseAnnotation.listenerType();
                    String methodName = eventBaseAnnotation.methodName();

                    try
                    {
                        //拿到Onclick注解中的value方法
                        Method aMethod = annotationType
                                .getDeclaredMethod("value");
                        //取出所有的viewId
                        int[] viewIds = (int[]) aMethod
                                .invoke(annotation, null);
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(
                                listenerType.getClassLoader(),
                                new Class<?>[]{listenerType}, handler);
                        //遍历所有的View，设置事件
                        for (int viewId : viewIds)
                        {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass()
                                    .getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }

    }



}
