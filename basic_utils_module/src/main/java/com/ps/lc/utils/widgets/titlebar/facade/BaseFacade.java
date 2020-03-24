package com.ps.lc.utils.widgets.titlebar.facade;

import android.support.annotation.NonNull;

import com.ps.lc.utils.widgets.titlebar.TitleBar;
import com.ps.lc.utils.widgets.titlebar.TitleBarType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类名：com.ps.lc.utils.widgets.titlebar.facecade
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 17:58
 */
public class BaseFacade {

    private static Map<Integer, Class<? extends BaseFacade>> list = new LinkedHashMap<>();
    /**
     * 类型（对应不同的场景[facade]）
     */
    protected int mType;
    /**
     * 样式应用（白天、黑夜、等.）[默认为白天0,黑夜1, 透明：2]
     */
    protected int mode = 0;

    protected TitleBar mTitleBar;

    /**
     * 子类执行
     */
    protected void execute() {

    }

    public BaseFacade(@TitleBarType int type) {
        mType = type;
    }

    @NonNull
    public BaseFacade mode(int mode) {
        this.mode = mode;
        return this;
    }

    @NonNull
    public BaseFacade with(TitleBar titleBar) {
        this.mTitleBar = titleBar;
        return this;
    }

    @NonNull
    public void apply() {
        execute();
    }

    public void destroy() {
        if (mTitleBar != null) {
            mTitleBar = null;
        }
    }


    public static <T extends BaseFacade> T newInstance(int type) {
        try {
            Class<? extends BaseFacade> clazz = list.get(type);
            //获取指定参数的构造方法
            Constructor<?> cons = clazz.getConstructor(int.class);
            return (T) cons.newInstance(type);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    static {
        list.put(TitleBarType.NON, NONFacade.class);
        list.put(TitleBarType.LEFT_ONLY, LeftOnlyFacade.class);
        list.put(TitleBarType.LEFT_CENTER, LeftCenterFacade.class);
        list.put(TitleBarType.RIGHT_IMAGE, RightStringFacade.class);
        list.put(TitleBarType.RIGHT_STRING, RightStringFacade.class);
        list.put(TitleBarType.RIGHT_CUSTOMIZE, NONFacade.class);
        list.put(TitleBarType.MULTI_CUSTOMIZE, NONFacade.class);
    }
}
