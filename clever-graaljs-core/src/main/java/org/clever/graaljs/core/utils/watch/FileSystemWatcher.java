package org.clever.graaljs.core.utils.watch;

import lombok.SneakyThrows;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.clever.graaljs.core.utils.Assert;

import java.io.File;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 15:58 <br/>
 */
public class FileSystemWatcher {
    /**
     * 文件监听器
     */
    private final FileAlterationMonitor monitor;
    /**
     * 文件事件观察者
     */
    private final FileAlterationObserver observer;
    /**
     * Listener
     */
    private final FileAlterationListener listener;

    /**
     * @param absolutePath    监听文件绝对路径
     * @param listener        文件变化时的处理
     * @param include         包含的文件通配符(白名单)
     * @param exclude         排除的文件通配符(黑名单)
     * @param caseSensitivity 文件大小写敏感设置
     * @param interval        在两次文件系统检查之间等待的时间（以毫秒为单位）
     */
    public FileSystemWatcher(String absolutePath, Consumer<File> listener, String[] include, String[] exclude, IOCase caseSensitivity, long interval) {
        Assert.notNull(listener, "参数listener不能为空");
        this.observer = new FileAlterationObserver(absolutePath);
        this.monitor = new FileAlterationMonitor(interval);
        this.listener = new FileListener(new BlackWhiteFileFilter(include, exclude, caseSensitivity), listener);
        init();
    }

    /**
     * @param absolutePath 监听文件绝对路径
     * @param include      包含的文件通配符(白名单)
     * @param exclude      排除的文件通配符(黑名单)
     */
    public FileSystemWatcher(String absolutePath, Consumer<File> listener, String[] include, String[] exclude) {
        this(absolutePath, listener, include, exclude, null, 3000);
        init();
    }

    /**
     * @param absolutePath    监听文件绝对路径
     * @param listener        文件变化时的处理函数
     * @param include         包含的文件通配符(白名单)
     * @param exclude         排除的文件通配符(黑名单)
     * @param caseSensitivity 文件大小写敏感设置
     * @param interval        在两次文件系统检查之间等待的时间（以毫秒为单位）
     */
    public FileSystemWatcher(String absolutePath, Consumer<File> listener, Set<String> include, Set<String> exclude, IOCase caseSensitivity, long interval) {
        Assert.notNull(listener, "参数listener不能为空");
        this.observer = new FileAlterationObserver(absolutePath);
        this.monitor = new FileAlterationMonitor(interval);
        this.listener = new FileListener(new BlackWhiteFileFilter(include, exclude, caseSensitivity), listener);
        init();
    }

    /**
     * @param absolutePath 监听文件绝对路径
     * @param listener     文件变化时的处理函数
     * @param include      包含的文件通配符(白名单)
     * @param exclude      排除的文件通配符(黑名单)
     */
    public FileSystemWatcher(String absolutePath, Consumer<File> listener, Set<String> include, Set<String> exclude) {
        this(absolutePath, listener, include, exclude, null, 3000);
        init();
    }

    /**
     * 初始化监听配置
     */
    private void init() {
        observer.addListener(listener);
        monitor.addObserver(observer);
    }

    /**
     * 开始监听
     */
    @SneakyThrows
    public void start() {
        monitor.start();
    }

    /**
     * 停止监听
     */
    @SneakyThrows
    public void stop() {
        monitor.stop();
    }
}
