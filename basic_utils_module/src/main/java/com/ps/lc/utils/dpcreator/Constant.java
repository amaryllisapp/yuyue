package com.ps.lc.utils.dpcreator;

/**
 *
 * 类名：Constant
 * 描述：常量类，用于做常用配置
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/2/14 17:53
 */
public final class Constant {
    /**
     * 默认基准线（不能动）
     */
    public final static double DEFAULT_DP = 360;

    /**
     * 默认文件名（不能动）
     */
    public final static String DEFAULT_FILE_NAME = "dimens.xml";
    /**
     * 默认支持列表（不能动）
     */
//    public static final String[] DEFAULT_DP_LIST = new String[]{"240", "320", "340", "360", "375", "384", "392", "400", "410", "411", "480", "520",  "533", "592", "600", "640", "662", "720", "768", "800", "811", "820", "960", "961", "1024", "1280", "1365"};
    /**
     * DP基准线(375（1dp = 1px）为苹果的2宽度的两倍，基于设计师的设计规则，如果是基于Android 360【1dp = 1px】来计算，则为360)
     */
    public final static double BASE_DP = 375;
    /**
     * 需要适配的DP机型像素尺寸
     */
    public final static String[] MATCH_DP = new String[]{"240", "320", "340", "360", "375", "400", "480", "520", "600", "720", "800", "820"};
    /**
     *
     * 忽略的机型像素尺寸
     */
    public final static String[] IGNORE_DP = new String[]{"820"};
    /**
     *
     * 忽略的模块名称
     */
//    public final static String[] IGNORE_MODULE_NAME = new String[]{};
    /**
     *
     * 新建的目录存放的模块
     */
    public final static String MATCH_MODULE = "third-party-module";
    /**
     *
     */
//    public final static boolean NOT_CREATE_DEFAULT_DIMENS = false;
    /**
     *  如果为false,内容依旧会被拷贝到相应的目录，但不会进行适配
     */
    public final static boolean IS_MATCH_FONT_SP = true;
    /**
     *
     */
//    public final static boolean CREATE_VALUES_SW_FOLDER = true;
    /**
     * 生成的文件名
     */
    public final static String FILE_NAME = "dimens.xml";
    /**
     * 格式化目录方式：
     *  1：全部清除，再重新创建（该目录中存在其他的内容也将会被清除）【谨慎使用】
     *  2: 合并清除，目录内如果存在其他文件，该目录则保存，文件将会被删除
     */
    public final static int FORMAT_DIR_TYPE = FormatDirType.MEGER;

    /**
     * 是否删除旧的目录格式(如果存在目录下存在非目录内容，需要保存该目录)
     */
    public static final boolean ENABLE_FORMAT_DIR = true;
    /**
     * 默认要大于等于0，等于0.则取整
     */
    public static final int DOT_NUMBER = 2;
}
