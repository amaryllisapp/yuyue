package com.ps.lc.utils.log;

import android.os.Environment;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

public class LogHelper {

    private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

    private static boolean init = false;

    /**
     * 仅输出debug调试，建议开发人员在开发过程中只使用这种类型的日志
     *
     * @param object 内容
     */
    public static void d(@Nullable Object object) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.d(object);
    }

    public static void d(@NonNull String tag, @Nullable Object object) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.d(tag, object);
    }

    /**
     * error错误,这里仅显示错误信息,这些错误就需要我们认真的分析,查看栈的信息,可以在catch异常之后使用
     *
     * @param message 内容
     */
    public static void e(@NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Throwable throwable = null;
        Logger.e(throwable, message);
    }

    public static void e(@NonNull String tag, @NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Throwable throwable = null;
        Logger.e(tag, throwable, message);
    }


    /**
     * 一般提示性的消息information,它不会输出Log.v和Log.d的信息,但会显示i、w和e的信息
     *
     * @param message 内容
     */
    public static void i(@NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.i(message);
    }

    public static void i(@NonNull String tag, @NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.i(tag, message);
    }

    /**
     * 任何消息都会输出,这里的v代表verbose啰嗦的意思
     *
     * @param message 内容
     */
    public static void v(@NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.v(message);
    }

    public static void v(@NonNull String tag, @NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.v(message);
    }

    /**
     * warning警告,一般需要我们注意优化Android代码,同时选择它后还会输出Log.e的信息
     *
     * @param message 内容
     */
    public static void w(@NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.w(message);
    }

    public static void w(@NonNull String tag, @NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.w(tag, message);
    }

    /**
     * 使用于特殊情况的日志，尤其是一些意料不到的异常情况,wtf -> What The Fuck ???
     *
     * @param message 内容
     */
    public static void wtf(@NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.wtf(message);
    }

    public static void wtf(@NonNull String tag, @NonNull String message) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.wtf(tag, message);
    }

    /**
     * 打印出格式化的json字符串,可以使用在网络请求拦截器里面
     */
    public static void json(@Nullable String json) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.json(json);
    }

    public static void json(@NonNull String tag, @Nullable String json) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.json(tag, json);
    }

    /**
     * 打印出格式化的xml字符串
     */
    public static void xml(@Nullable String xml) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.xml(xml);
    }

    public static void xml(@NonNull String tag, @Nullable String xml) {
        if (!init){
            throw new IllegalStateException("LogHelper 未初始化");
        }
        Logger.xml(tag, xml);
    }

    /**
     * 初始化日志,无缓存功能
     *
     * @param isDebug 是否为debug版本
     * @param tag     日志tag
     */
    public static void init(final boolean isDebug, @NonNull String tag) {
        FormatStrategy prettyFormatStrategy = PrettyFormatStrategy.newBuilder()
                //  (可选) 是否显示thead的信息. 默认 true
                .showThreadInfo(true)
                //  (可选) 显示日志调用所在的方法的层数. 默认 2
                .methodCount(0)
                // (可选) 隐藏内部方法调用到偏移量. 默认 5
                .methodOffset(7)
                // (可选) log的输出策略. 默认 LogcatLogStrategy
                .logStrategy(new LogcatLogStrategy())
                // (可选) 全局日志标志. 默认 PRETTY_LOGGER
                .tag(tag)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(prettyFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });

        init = true;
    }

    /**
     * 初始化日志,且缓存日志到本地文件
     *
     * @param isDebug  是否为debug版本
     * @param tag      日志tag
     * @param filePath 保存本地路径
     * @param fileName 日志文件名称
     */
    public static void init(boolean isDebug, @NonNull String tag, String filePath, String fileName) {
        //初始化
        init(isDebug, tag);

        //日志存储本地
        if (TextUtils.isEmpty(filePath)){
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        if (TextUtils.isEmpty(fileName)){
            fileName = "recycling2blogger";
        }
        String folder = filePath + File.separatorChar + fileName;

        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        DiskLogStrategy diskLogStrategy = new DiskLogStrategy(new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, fileName, MAX_BYTES));
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .logStrategy(diskLogStrategy)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }


}
