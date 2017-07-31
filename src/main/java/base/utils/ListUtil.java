package base.utils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 列表工具类
 * @author Gonjan
 *
 */
public final class ListUtil {

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    private ListUtil() {
    }

    /**
     * 判断列表是否为空
     *
     * @param list
     * @return
     */
    public static <V extends Object> boolean isBlank(final List<V> list) {
        return (null == list || list.isEmpty());
    }

    /**
     * 判断列表是否不为空
     *
     * @param list
     * @return
     */
    public static <V extends Object> boolean isNotBlank(final List<V> list) {
        return !isBlank(list);
    }

    /**
     * 获得数组中的第一个值
     *
     * @param <T> 泛型类别
     * @param list 数组
     * @return
     */
    public static <T> T first(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获得数组中的最后一个值
     *
     * @param <T> 泛型类别
     * @param list 数组
     * @return
     */
    public static <T> T last(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 生成list
     *
     * @param <T>
     * @param objs
     * @return
     */
    public static <T> List<T> toList(T... objs) {
        List<T> list = new ArrayList<T>();
        if (objs != null && objs.length > 0) {
            list.addAll(Arrays.asList(objs));
        }
        return list;
    }

    /**
     * 将列表转换成一个数组
     *
     * @param list
     * @return
     */
    public static String[] toStrs(List<String> list) {
        if (isBlank(list)) {
            return null;
        }
        String str[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            str[i] = list.get(i);
        }
        return str;
    }

    /**
     * 判断是否list中包含t
     *
     * @param <T>
     * @param list
     * @param t
     * @return
     */
    public static <T> boolean contain(T[] list, T t) {
        if (list == null || list.length == 0) {
            return false;
        }
        if (t == null) {
            return false;
        }
        for (T temp : list) {
            if (temp.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回一个空的数组
     *
     * @param <T>
     * @return
     */
    public static <T> List<T> emptyList() {
        return new ArrayList<T>();
    }

    public static <T> List<T> defaultList(List<T> t) {
        return t == null ? new ArrayList<T>() : t;
    }

    /**
     * 
     * 
     * @param str
     */
    public static List<String> str2List(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }

        String[] strArr = StringUtils.split(str, ",");
        return ListUtil.toList(strArr);

    }

    /**
     * 列表转换
     * 
     * @param fromList
     * @param function
     * @return
     */
    public static <F, T> List<T> transform(List<F> fromList,
                                           Function<? super F, ? extends T> function) {

        if (CollectionUtils.isEmpty(fromList)) {
            return Collections.emptyList();
        }

        List<T> result = Lists.newArrayList();

        for (F f : fromList) {
            result.add(function.apply(f));
        }

        return result;

    }

    /**
     * 判断是否存在
     * 
     * @param fromList
     * @param predicate
     * @return
     */
    public static <T> boolean contains(List<T> fromList, Predicate<T> predicate) {

        if (CollectionUtils.isEmpty(fromList)) {
            return false;
        }

        for (T input : fromList) {
            if (predicate.apply(input)) {
                return true;
            }
        }

        return false;

    }
    /**
     * 列表查找
     * 
     * @param fromList
     * @param predicate
     * @return
     */
    public static <T> T findFirst(List<T> fromList, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(fromList)) {
            return null;
        }

        return Iterables.find(fromList, predicate, null);

    }

    public static <T> List<T> filter(List<T> fromList, Predicate<T> predicate) {

        if (CollectionUtils.isEmpty(fromList)) {
            return fromList;
        }

        Iterator<T> iter = Iterators.filter(fromList.iterator(), predicate);

        if (!iter.hasNext()) {
            return Collections.emptyList();
        }

        return Lists.newArrayList(iter);
    }

    public static <T> int size(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }

        return CollectionUtils.size(list);
    }

}
