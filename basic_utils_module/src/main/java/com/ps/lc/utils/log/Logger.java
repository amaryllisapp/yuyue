package com.ps.lc.utils.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.ps.lc.utils.log.Utils.checkNotNull;


/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ LOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 * <p>
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   Logger.addLogAdapter(new AndroidLogAdapter());
 * </code></pre>
 * <p>
 * And use the appropriate static Logger methods.
 * <p>
 * <pre><code>
 *   Logger.d("debug");
 *   Logger.e("error");
 *   Logger.w("warning");
 *   Logger.v("verbose");
 *   Logger.i("information");
 *   Logger.wtf("What a Terrible Failure");
 * </code></pre>
 * <p>
 * <h3>String format arguments are supported</h3>
 * <pre><code>
 *   Logger.d("hello %s", "world");
 * </code></pre>
 * <p>
 * <h3>Collections are support ed(only available for debug logs)</h3>
 * <pre><code>
 *   Logger.d(MAP);
 *   Logger.d(SET);
 *   Logger.d(LIST);
 *   Logger.d(ARRAY);
 * </code></pre>
 * <p>
 * <h3>Json and Xml support (output will be in debug level)</h3>
 * <pre><code>
 *   Logger.json(JSON_CONTENT);
 *   Logger.xml(XML_CONTENT);
 * </code></pre>
 * <p>
 * <h3>Customize Logger</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 * <li>Different {@link com.ps.lc.utils.log.LogAdapter}</li>
 * <li>Different {@link com.ps.lc.utils.log.FormatStrategy}</li>
 * <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see com.ps.lc.utils.log.LogAdapter
 * @see FormatStrategy
 * @see LogStrategy
 */
final class Logger {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    @NonNull
    private static Printer printer = new LoggerPrinter();

    private Logger() {
        //no instance
    }

    public static void printer(@NonNull Printer printer) {
        Logger.printer = checkNotNull(printer);
    }

    public static void addLogAdapter(@NonNull LogAdapter adapter) {
        printer.addAdapter(checkNotNull(adapter));
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    /**
     * Given tag will be used as tag only once for this method call regardless of the tag that's been
     * set during initialization. After this invocation, the general tag that's been set will
     * be used for the subsequent log calls
     */
    public static Printer t(@Nullable String tag) {
        return printer.t(tag);
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

    public static void d(@NonNull String tag, @Nullable Object object) {
        printer.d(tag, object);
    }

    public static void d(@Nullable Object object) {
        printer.d(object);
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        Throwable throwable = null;
        printer.e(throwable, message, args);
    }

    public static void e(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Throwable throwable = null;
        printer.e(tag, throwable, message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(throwable, message, args);
    }

    public static void e(@NonNull String tag, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(tag, throwable, message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        printer.i(message, args);
    }

    public static void i(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.i(tag, message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        printer.v(message, args);
    }

    public static void v(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.v(tag, message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        printer.w(message, args);
    }

    public static void w(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.w(tag, message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String message, @Nullable Object... args) {
        printer.wtf(message, args);
    }

    public static void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.wtf(tag, message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@Nullable String json) {
        printer.json(json);
    }

    public static void json(@NonNull String tag, @Nullable String json) {
        printer.json(tag, json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@Nullable String xml) {
        printer.xml(xml);
    }

    public static void xml(@NonNull String tag, @Nullable String xml) {
        printer.xml(tag, xml);
    }

}
