package com.ps.lc.utils.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface Printer {

    void addAdapter(@NonNull LogAdapter adapter);

    Printer t(@Nullable String tag);

    void d(@NonNull String message, @Nullable Object... args);

    void d(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void d(@Nullable Object object);

    void d(@NonNull String tag, @Nullable Object object);

    void e(@NonNull String message, @Nullable Object... args);

    void e(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void e(@NonNull String tag, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void w(@NonNull String message, @Nullable Object... args);

    void w(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void i(@NonNull String message, @Nullable Object... args);

    void i(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void v(@NonNull String message, @Nullable Object... args);

    void v(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void wtf(@NonNull String message, @Nullable Object... args);

    void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(@Nullable String json);

    void json(@NonNull String tag, @Nullable String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(@Nullable String xml);

    void xml(@NonNull String tag, @Nullable String xml);

    void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void clearLogAdapters();
}
