package com.jack.myexperience.tools;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jack.myexperience.annnotation.AutoBind;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class AnnotationUtils {
    public static void bind(Activity activity){
            Class temp=activity.getClass();
            Annotation annotation=temp.getAnnotation(AutoBind.class);
        try {
            Method tempMethods=annotation.getClass().getDeclaredMethod("field");
            int [] ids= (int[]) tempMethods.invoke(annotation,new Object[0]);
            if(null!=ids){
                for(int i=0;i<ids.length;i++){
                    View view = activity.findViewById(ids[i]);
                    String className=view.getClass().getName();
                    Class viewClass=Class.forName(className);
                    viewClass.cast(view);
                    if(className.contains("TextView")){
                        ((TextView)view).setText(className);
                    }
                    if(className.contains("Button")){
                        ((Button)view).setText(className);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
