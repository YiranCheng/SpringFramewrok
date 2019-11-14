package com.gupao.edu.vip.study.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yiran
 */
public class PreProcessor implements IRequestProcessor, Runnable {

    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    private IRequestProcessor nextProcessor;

    private volatile boolean isFinish =  false;

    PreProcessor(IRequestProcessor  processor) {
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
                String name = request.getName();
                if (name.equals("3")){
                    TimeUnit.SECONDS.sleep(3);
                }
                System.out.println("PreProcessor doSomething" + request);
                nextProcessor.doProcessor(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
