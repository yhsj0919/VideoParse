package xyz.yhsj.mvpro.helper;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


/**
 * 泛型帮助类
 */
public class ParameterizedTypeUtil {

    private ParameterizedTypeUtil() {
    }

    /**
     * @param ownerType     自己传入的类
     * @param declaredClass 父类
     * @param paramIndex    参数索引
     * @return
     */
    public static Type getParameterizedType(
            final Type ownerType,
            final Class<?> declaredClass,
            int paramIndex) {

        Class<?> clazz = null;
        ParameterizedType pt = null;
        Type[] ats = null;
        TypeVariable<?>[] tps = null;
        if (ownerType instanceof ParameterizedType) {
            pt = (ParameterizedType) ownerType;
            clazz = (Class<?>) pt.getRawType();
            ats = pt.getActualTypeArguments();
            tps = clazz.getTypeParameters();
        } else {
            clazz = (Class<?>) ownerType;
        }
        if (declaredClass == clazz) {
            if (ats != null) {
                return ats[paramIndex];
            }
            return Object.class;
        }

        Type[] types = clazz.getGenericInterfaces();
        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                Type t = types[i];
                if (t instanceof ParameterizedType) {
                    Class<?> cls = (Class<?>) ((ParameterizedType) t).getRawType();
                    if (declaredClass.isAssignableFrom(cls)) {
                        try {
                            return getTrueType(getParameterizedType(t, declaredClass, paramIndex), tps, ats);
                        } catch (Throwable ignored) {
                        }
                    }
                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            if (declaredClass.isAssignableFrom(superClass)) {
                return getTrueType(getParameterizedType(clazz.getGenericSuperclass(), declaredClass, paramIndex), tps, ats);
            }
        }
        throw new IllegalArgumentException("FindGenericType:" + ownerType + ", declaredClass: " + declaredClass + ", index: " + paramIndex);
    }


    private static Type getTrueType(

            Type type,
            TypeVariable<?>[] typeVariables,
            Type[] actualTypes) {

        if (type instanceof TypeVariable<?>) {
            TypeVariable<?> tv = (TypeVariable<?>) type;
            String name = tv.getName();
            if (actualTypes != null) {
                for (int i = 0; i < typeVariables.length; i++) {
                    if (name.equals(typeVariables[i].getName())) {
                        return actualTypes[i];
                    }
                }
            }
            return tv;
            // }else if (type instanceof Class<?>) {
            // return type;
        } else if (type instanceof GenericArrayType) {
            Type ct = ((GenericArrayType) type).getGenericComponentType();
            if (ct instanceof Class<?>) {
                return Array.newInstance((Class<?>) ct, 0).getClass();
            }
        }
        return type;
    }


    public static <T> Class<T> getViewClass(Class<?> klass) {
        Type type = klass.getGenericSuperclass();
        if (type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        if (types == null || types.length == 0) return null;
        return (Class<T>) types[0];
    }

}
