package com.ps.lc.utils.dpcreator;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：Tools
 * 描述：工具类
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/2/14 17:43
 */
public class Tools {

    private static final String dot = ".";

    /**
     * 乘以系数
     *
     * @param isFontMatch 字体是否也适配(是否与dp尺寸一样等比缩放)
     * @param sourceValue 原字符串 @dimen/dp_xxx 或 xxxdp
     * @param multiple    屏幕宽度dp基于360dp宽度的系数比值
     * @return 乘以系数后的缩放值字符串，且带单位
     */
    public static String countValue(boolean isFontMatch, String sourceValue, double multiple, String resultFormat) {
        if (sourceValue == null || "".equals(sourceValue.trim())) {
            //无效值，不执行计算
            return "errorValue";
        }
        //去除值两端空格，包括引用值
        sourceValue = sourceValue.trim();
        // @dimen/dp_xxx
        // @dimen/sp_xxx
        if (sourceValue.startsWith("@dimen/")) {
            //引用值，不执行计算
            return sourceValue;
        }
        //替换非引用值的单位dip为dp
        if (sourceValue.endsWith("dip")) {
            //我只确保最后的dip替换成dp，你非要写成39dipdip这种恶心的值，我也管不了
            sourceValue = sourceValue.replaceAll("dip", "dp");
        }
        // xxpx
        // xxpt
        // ...
        if (!sourceValue.endsWith("dp") && !sourceValue.endsWith("sp")) {
            //非dp或sp数据，不执行计算
            return sourceValue;
        }
        if (sourceValue.endsWith("sp")) {
            if (!isFontMatch) {
                //如果为false，不执行计算
                return sourceValue;
            }
        }
        if (sourceValue.length() < 3) {
            //只剩下单位dp或sp，不执行计算
            return sourceValue;
        }
        int length = sourceValue.length();
        //单位dp或sp
        String endValue = sourceValue.substring(length - 2, length);
        //数值
        String startValue = sourceValue.substring(0, length - 2);
        endValue = endValue.trim();
        startValue = startValue.trim();
        if ("".equals(endValue) || "".equals(startValue)) {
            return sourceValue;
        }
        //乘以系数
        double temp = 0;
        try {
            temp = Double.parseDouble(startValue) * multiple;
        } catch (Exception e) {
            return sourceValue;
        }
        //数据格式化对象
        DecimalFormat df = new DecimalFormat(resultFormat);
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(temp) + endValue;
    }

    /**
     * 把set集合数据转成字符串，并有序的返回
     *
     * @param set
     * @return
     */
    public static String getOrderedString(HashSet<Double> set) {
        if (set == null || set.size() <= 0) {
            return "";
        }
        ArrayList<Double> list = new ArrayList<>();
        list.addAll(set);
        Object[] arr = list.toArray();
        Arrays.sort(arr);
        StringBuilder stringBuffer = new StringBuilder();
        for (Object anArr : arr) {
            stringBuffer.append(cutLastZero(Double.parseDouble(anArr.toString()))).append(", ");
        }
        String result = stringBuffer.toString();
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    /**
     * 去除浮点型后面多余的0
     *
     * @param value
     * @return
     */
    public static String cutLastZero(double value) {
        if (value <= 0) {
            return "0";
        }
        String sourceValue = String.valueOf(value);
        String result = "";
        //带小数
        if (sourceValue.contains(dot)) {
            // 去除后面的0
            while (sourceValue.charAt(sourceValue.length() - 1) == '0') {
                sourceValue = sourceValue.substring(0, sourceValue.length() - 1);
            }
            //删除最后的点
            if (sourceValue.endsWith(dot)) {
                sourceValue = sourceValue.substring(0, sourceValue.length() - 1);
            }
            result = sourceValue;
        }
        return result;
    }

    /**
     * 递归删除目录
     *
     * @param file 待删除的文件或目录
     */
    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    // 递归删除
                    deleteFile(files[i]);
                }
            }
            try {
                // 删除当前空目录
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 是文件
            try {
                // 删除当前文件
                if(Constant.FORMAT_DIR_TYPE == FormatDirType.ALL){
                    file.delete();
                }else if(Constant.FORMAT_DIR_TYPE == FormatDirType.MEGER){
                    if (file.getName().equals(Constant.FILE_NAME)) {
                        file.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断当前文件目录名是否为 values-wXXXdp 格式，即以前的旧文件目录
     *
     * @param path           ..../res/values-wXXXdp
     * @param isUseNewFolder 是否使用新的目录格式 values-swXXXdp
     * @return 是否是指定格式的目录
     */
    public static boolean isOldFolder(String path, boolean isUseNewFolder) {
        if (path == null || path.trim().length() == 0) {
            return false;
        }

        String regEx = "";
        if (isUseNewFolder) {
            //即删除旧的目录 values-wXXXdp
            regEx = "^values-w[0-9]+dp$";
        } else {
            //删除新的目录格式 values-swXXXdp
            regEx = "^values-sw[0-9]+dp$";
        }
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }

    /**
     * 查找老数据（TODO:此方式只是简单实现，具体还需要推敲一下）
     *
     * @param path
     * @return
     */
    public static boolean isOldFolder(String path) {
        if (path == null || path.trim().length() == 0) {
            return false;
        }
        String regEx = "";
        if (path.contains("values-w") || path.contains("values-sw")) {
            return true;
        }
        return false;
    }

    /**
     * 获取基准DP
     *
     * @param baseDP
     * @return
     */
    public static double getBaseDp(String baseDP) {
        if (baseDP != null && !"".equals(baseDP.trim())) {
            try {
                double base = Double.parseDouble(baseDP.trim());
                if (base <= 0) {
                    return Constant.DEFAULT_DP;
                }
                return base;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return Constant.DEFAULT_DP;
            }
        } else {
            return Constant.DEFAULT_DP;
        }
    }

    /**
     * 添加数据到HashSet<Double>中
     *
     * @param needMatchs
     * @return
     */
    public static HashSet<Double> createData(String[] needMatchs) {
        HashSet<Double> dataSet = new HashSet<>();
        //添加默认的数据
        for (String dpArr : Constant.MATCH_DP) {
            if (dpArr == null || "".equals(dpArr.trim())) {
                continue;
            }
            try {
                dataSet.add(Double.parseDouble(dpArr.trim()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        /**
         * 额外新增的DP数据（目前接口不做支持，此部分代码留着备用）
         */
        if (needMatchs != null) {
            for (String needMatch : needMatchs) {
                if (needMatch == null || "".equals(needMatch.trim())) {
                    continue;
                }
                try {
                    double needMatchDouble = Double.parseDouble(needMatch.trim());
                    if (needMatchDouble > 0) {
                        dataSet.add(needMatchDouble);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataSet;
    }

    /**
     * 删除需要被过滤的机型适配数据
     *
     * @param data
     * @param ignoreMatchs
     */
    public static void ignoreData(HashSet<Double> data, String[] ignoreMatchs) {
        if (ignoreMatchs != null) {
            for (String ignoreMatch : ignoreMatchs) {
                if (ignoreMatch == null || "".equals(ignoreMatch.trim())) {
                    continue;
                }
                try {
                    data.remove(Double.parseDouble(ignoreMatch.trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除以前适配方式的目录values-wXXXdp
     *
     * @param delFolderPath
     * @param isDeleteAnotherFolder
     */
    public static void removeOldFolder(String delFolderPath, boolean isDeleteAnotherFolder) {
        /**
         * 删除以前适配方式的目录values-wXXXdp
         */
        if (isDeleteAnotherFolder) {
            File oldFile = new File(delFolderPath);
            if (oldFile.exists() && oldFile.isDirectory() && isOldFolder(oldFile.getName())) {
                //找出res目录下符合要求的values目录，然后递归删除values目录
                Tools.deleteFile(oldFile);
            }
        }
    }
}