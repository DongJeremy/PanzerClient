package org.cloud.core.rx;

public enum ThreadMode {
    /**
     * current thread
     */
    CURRENT_THREAD,

    /**
     * android main thread
     */
    MAIN,

    /**
     * new thread
     */
    NEW_THREAD
}
