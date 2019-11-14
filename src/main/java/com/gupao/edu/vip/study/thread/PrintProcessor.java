package com.gupao.edu.vip.study.thread;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yiran
 */
public class PrintProcessor implements IRequestProcessor, Runnable {

    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    private IRequestProcessor nextProcessor;

    private volatile boolean isFinish =  false;

    PrintProcessor(IRequestProcessor  processor) {
        nextProcessor = processor;
    }

    @Override
    public void doProcessor(Request request) {
        queue.add(request);
    }

    public void shutdown() {
        isFinish = true;
    }

    @Override
    public void run() {
        while(!isFinish) {
            try {
                Request request = queue.take();
                System.out.println("PrintProcessor doSomething" + request);
                nextProcessor.doProcessor(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
