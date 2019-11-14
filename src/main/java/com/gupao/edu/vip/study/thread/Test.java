package com.gupao.edu.vip.study.thread;

/**
 * @author yiran
 */
public class Test {
    PreProcessor processor;

    Test(){
        FinalProcessor finalProcessor = new FinalProcessor();
        new Thread(finalProcessor,"final").start();
        PrintProcessor printProcessor = new PrintProcessor(finalProcessor);
        new Thread(printProcessor,"print").start();
        processor = new PreProcessor(printProcessor);
        new Thread(processor, "pre").start();
    }

    public static void main(String[] args) {
        Test t = new Test();
        for (int i=1;i<5;i++) {
            Request r = new Request();
            r.setName(i+"");
            t.processor.doProcessor(r);
        }

    }
}
