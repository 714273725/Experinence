package ye.jian.ge.annotationHelper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ye.jian.ge.annotation.SaveStatue;
import ye.jian.ge.utils.LogUtils;

/**
 * Created by Administrator on 2016/8/8.
 */
public class StatueBinder {
    public static void saveStatue(Object target, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (target == null) {
            return;
        }
        Class targetClass = target.getClass();
        Field[] fields = targetClass.getFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field != null && !field.isAccessible()) {
                    SaveStatue statue = field.getAnnotation(SaveStatue.class);
                    if (statue != null) {
                        try {
                            Object data = field.get(target);
                            Class fieldClazz = field.getType();
                            //如果是基本基本数据类型
                            if (fieldClazz.isPrimitive()) {
                                if (data.getClass() == Byte.class) {
                                    LogUtils.d(field.getName() + " is byte");
                                    bundle.putByte(field.getName(), (Byte) data);
                                }
                                if (data.getClass() == Short.class) {
                                    LogUtils.d(field.getName() + " is short");
                                    bundle.putShort(field.getName(), (Short) data);
                                }
                                if (data.getClass() == Integer.class) {
                                    LogUtils.d(field.getName() + " is int");
                                    bundle.putInt(field.getName(), (Integer) data);
                                }
                                if (data.getClass() == Long.class) {
                                    LogUtils.d(field.getName() + " is long");
                                    bundle.putLong(field.getName(), (Long) data);
                                }
                                if (data.getClass() == Float.class) {
                                    LogUtils.d(field.getName() + " is float");
                                    bundle.putFloat(field.getName(), (Float) data);
                                }
                                if (data.getClass() == Double.class) {
                                    LogUtils.d(field.getName() + " is double");
                                    bundle.putDouble(field.getName(), (Double) data);
                                }

                                if (data.getClass() == Character.class) {
                                    LogUtils.d(field.getName() + " is char");
                                    bundle.putChar(field.getName(), (Character) data);
                                }
                                if (data.getClass() == Boolean.class) {
                                    LogUtils.d(field.getName() + " is boolean");
                                    bundle.putBoolean(field.getName(), (Boolean) data);
                                }
                                //不是基本数据类型
                            } else {
                                //String类型
                                if (data.getClass() == String.class) {
                                    LogUtils.d(field.getName() + " is String");
                                    bundle.putString(field.getName(), (String) data);
                                } else if (!fieldClazz.isArray() && isImplementTarget(fieldClazz, Serializable.class)) {
                                    LogUtils.d(field.getName() + " is Serializable");
                                    bundle.putSerializable(field.getName(), (Serializable) data);
                                } else if (!fieldClazz.isArray() && isImplementTarget(fieldClazz, Parcelable.class)) {
                                    LogUtils.d(field.getName() + " is Parcelable");
                                    bundle.putParcelable(field.getName(), (Parcelable) data);
                                } else if (data.getClass() == List.class) {
                                    Type fc = field.getGenericType();
                                    if (fc != null) {
                                        if (fc instanceof ParameterizedType) {
                                            ParameterizedType pt = (ParameterizedType) fc;
                                            Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                                            if (isImplementTarget(genericClazz, Parcelable.class)) {
                                                LogUtils.d(field.getName() + " is ParcelableList");
                                                bundle.putParcelableArrayList(field.getName(), (ArrayList<? extends Parcelable>) data);
                                            }
                                        }
                                    }
                                    //如果成员是数组
                                } else if (fieldClazz.isArray()) {
                                    if (isImplementTarget(fieldClazz.getComponentType(), Parcelable.class))
                                        LogUtils.d(field.getName() + " is Parcelable array");
                                    bundle.putParcelableArray(field.getName(), (Parcelable[]) data);
                                } else if (fieldClazz == SparseArray.class) {
                                    //判断SparseArray里的元素是否是Parcelable的
                                    boolean parcelable = false;
                                    if (data != null) {
                                        for (int i = 0; i < ((SparseArray) data).size(); i++) {
                                            if (((SparseArray) data).get(i) != null) {
                                                if (isImplementTarget(((SparseArray) data).get(i).getClass(), Parcelable.class)) {
                                                    parcelable = true;
                                                }
                                            }
                                        }
                                    }
                                    if (parcelable) {
                                        LogUtils.d(field.getName() + " is Parcelable sparseArray");
                                        bundle.putSparseParcelableArray(field.getName(), (SparseArray<? extends Parcelable>) data);
                                    }
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    throw new NullPointerException("不支持的修饰符");
                }
            }
        }
    }


    public static void bindStatue(Object target, Bundle source) {
        if (source == null) {
            return;
        }
        if (target == null) {
            return;
        }
        Class targetClass = target.getClass();
        Field[] fields = targetClass.getFields();
        if (fields != null) {
            for (Field field : fields) {
                SaveStatue statue = field.getAnnotation(SaveStatue.class);
                if (statue != null) {
                    Object data = source.get(field.getName());
                    try {
                        field.set(target,data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static boolean isImplementTarget(Class clz, Class target) {
        boolean flag = false;
        Class[] temp = clz.getInterfaces();
        if (temp != null) {
            for (Class clzs : temp) {
                if (clzs == target) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
