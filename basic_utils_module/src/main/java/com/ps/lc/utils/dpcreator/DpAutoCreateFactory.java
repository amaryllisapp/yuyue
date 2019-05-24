package com.ps.lc.utils.dpcreator;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 类名：DpAutoCreateFactory
 * 描述：此类为执行入口，需要通过Main方法进行执行。
 * 目前只支持sw模式，其他模式根据实际情况进行增加
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/2/14 17:13
 */
public class DpAutoCreateFactory {
    /**
     * 基准DP
     */
    private static double baseDP = Constant.BASE_DP;

    /**
     * 生成的values目录格式(代码中替换XXX字符串)
     */
    public static String LETTER_REPLACE = "XXX";

    /**
     * values-sw410dp可能会存在问题
     */
    private static String VALUES_NEW_FOLDER = "values-swXXXdp" + File.separator;



    /**
     * 命令行入口
     *
     * @param args 命令行参数[注意，命令行是以空格分割的]
     */
    public static void main(String[] args) {
        //获取当前目录的绝对路径
        try {
            String resFolderPath = new File("./" + Constant.MATCH_MODULE + "/src/main/res/").getAbsolutePath() + File.separator;
            start(Constant.IS_MATCH_FONT_SP, baseDP + "", null, Constant.IGNORE_DP, resFolderPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 适配文件调用入口
     *
     * @param isFontMatch   字体是否也适配(是否与dp尺寸一样等比缩放)
     * @param tempBaseDP    基准dp值
     * @param needMatchs    待适配宽度dp值
     * @param ignoreMatchs  待忽略宽度dp值
     * @param resFolderPath base dimens.xml 文件的res目录
     * @return 返回消息
     */
    public static String start(boolean isFontMatch, String tempBaseDP, String[] needMatchs, String[] ignoreMatchs, String resFolderPath) throws Exception {
        // 获取基准DP
        baseDP = Tools.getBaseDp(tempBaseDP);
        // 创建机型适配数据
        HashSet<Double> dataSet = Tools.createData(needMatchs);
        // 删除需要忽略的机型适配数据
        Tools.ignoreData(dataSet, ignoreMatchs);

        System.out.println("基准宽度dp值：[ " + Tools.cutLastZero(baseDP) + " dp ]");
        System.out.println("本次待适配的宽度dp值: [ " + Tools.getOrderedString(dataSet) + " ]");
        // 获取指定默认资源内容
        ArrayList<DimenItem> list = getDimensList(resFolderPath);
        // 删除以前生成的老资源目录及文件（此做法如果存在其他兼容问题，需要谨慎操作）
        deleteOlderResFolder(resFolderPath);
        // 执行生成操作
        return execute(dataSet.iterator(), list, resFolderPath, isFontMatch);
    }

    /**
     * 获取基准Dimens文件
     *
     * @param resFolderPath
     * @return
     */
    private static ArrayList<DimenItem> getDimensList(String resFolderPath) throws Exception {
        //获取基准的dimens.xml文件
        String baseDimenFilePath = resFolderPath + File.separator + "values" + File.separator + Constant.DEFAULT_FILE_NAME;
        File file = new File(baseDimenFilePath);
        //判断基准文件是否存在
        if (!file.exists()) {
            System.out.println("DK WARNING:  \"./res/values/lay_x.xml\" 路径下的文件找不到!");
            throw new Exception("对应Module \"./res/values/lay_x.xml\" 路径下的文件找不到!");
        }

        /**
         * 读取基准线Dimens文件
         */
        ArrayList<DimenItem> list = XmlIO.readDimenFile(baseDimenFilePath);
        if (list == null || list.size() <= 0) {
            System.out.println("DK WARNING:  \"./res/values/lay_x.xml\" 文件无数据!");
            throw new Exception("对应Module \"./res/values/lay_x.xml\" 路径下的文件找不到!");
        }
        System.out.println("OK \"./res/values/lay_x.xml\" 基准dimens文件解析成功!");
        return list;
    }

    /***
     * 格式化就旧内容（包含和适配内容相关的文件和文件夹）
     */
    private static void deleteOlderResFolder(String resFolderPath) {
        File file = new File(resFolderPath);
        File[] fileList = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.contains("values-w") || name.contains("values-sw")){
                    return true;
                }
                return false;
            }
        });
        for(File xx : fileList){
            String deletePath = resFolderPath + xx.getName();
            Tools.removeOldFolder(deletePath, Constant.ENABLE_FORMAT_DIR);
        }
    }

    /**
     * 执行创建目录、文件以及生成文件内容
     *
     * @param iterator      适配尺寸
     * @param list          原型数据（默认dimens）
     * @param resFolderPath Res资源目录
     * @param isFontMatch   是否匹配字体
     * @return
     */
    private static String execute(Iterator<Double> iterator, ArrayList<DimenItem> list, String resFolderPath, boolean isFontMatch) {
        /**
         * 解析源dimens.xml文件
         */
        try {
            // 循环指定的dp参数，生成对应的dimens-swXXXdp.xml文件
            while (iterator.hasNext()) {
                double item = iterator.next();
                // 获取当前dp除以baseDP后的倍数
                double multiple = item / baseDP;
                // 输入目录
                String outFolder = resFolderPath + VALUES_NEW_FOLDER.replace(LETTER_REPLACE, String.valueOf((int) item));
                // 生成新的目录values-swXXXdp
                new File(outFolder).mkdirs();
                // 生成的dimens文件的路径
                String outPutFile = outFolder + Constant.FILE_NAME;
                // 生成目标文件dimens.xml输出目录
                XmlIO.createDestinationDimens(isFontMatch, list, multiple, outPutFile);
            }
            System.out.println("OK ALL OVER，全部生成完毕！");
            // 适配完成
            return "Over, adapt successful";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}