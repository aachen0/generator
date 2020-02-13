package com.ahjrlc.generator.mybatis;

import org.mybatis.generator.api.ProgressCallback;

/**
 * @author aachen0
 * @date 2019/12/13 17:09
 */
public class ProgressCallbackAachenImpl implements ProgressCallback {
    @Override
    public void introspectionStarted(int i) {

    }

    @Override
    public void generationStarted(int i) {

    }

    @Override
    public void saveStarted(int i) {

    }

    @Override
    public void startTask(String s) {
        System.out.println("generator:-->" + s);
    }

    @Override
    public void done() {
        System.out.println("逆向生成完毕");
    }

    @Override
    public void checkCancel() throws InterruptedException {

    }
}