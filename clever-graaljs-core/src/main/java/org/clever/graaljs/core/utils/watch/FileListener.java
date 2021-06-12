package org.clever.graaljs.core.utils.watch;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 16:15 <br/>
 */
public class FileListener implements FileAlterationListener {
    private final FileFilter fileFilter;
    private final Consumer<File> listener;

    public FileListener(FileFilter fileFilter, Consumer<File> listener) {
        this.fileFilter = fileFilter;
        this.listener = listener;
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
    }

    @Override
    public void onDirectoryCreate(File directory) {
    }

    @Override
    public void onDirectoryChange(File directory) {
    }

    @Override
    public void onDirectoryDelete(File directory) {
    }

    @Override
    public void onFileCreate(File file) {
        if (fileFilter != null && !fileFilter.accept(file)) {
            return;
        }
        listener.accept(file);
    }

    @Override
    public void onFileChange(File file) {
        if (fileFilter != null && !fileFilter.accept(file)) {
            return;
        }
        listener.accept(file);
    }

    @Override
    public void onFileDelete(File file) {
        if (fileFilter != null && !fileFilter.accept(file)) {
            return;
        }
        listener.accept(file);
    }
}
