package cn.zxj.utils.excutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExample {
    public Integer demo1(){
        List<Object> list = new ArrayList<Object>();
        List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();

        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        try {
            for (Object o:list) {
                ExecutorDemo executorDemo = new ExecutorDemo(o,countDownLatch);
                Future<Integer> future = executorService.submit(executorDemo);
                resultList.add(future);
            }
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }finally {
            if (executorService!=null){
                executorService.shutdownNow();
            }
        }

        for (Future<Integer> integerFuture : resultList) {
            try {
                Integer result = integerFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    class ExecutorDemo implements Callable<Integer> {
        private Object o;
        private CountDownLatch countDownLatch;

        public ExecutorDemo(Object o,CountDownLatch countDownLatch) {
            this.o = o;
            this.countDownLatch = countDownLatch;
        }

        public Integer call() throws Exception {
            //do something
            countDownLatch.countDown();
            return null;
        }
    }

    public Integer demo2(){
        int size = 0; //记录结果
        List<Object> list = new ArrayList<Object>();
        List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        for (Object o:list) {
            ExecutorDemo2 executorDemo2 = new ExecutorDemo2(o);
            Future<Integer> future = executorService.submit(executorDemo2);
            resultList.add(future);
        }

        executorService.shutdown();
        while(true) {
            if (executorService.isTerminated()) {
                break;
            }
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            for (Future<Integer> num : resultList) {
                size += num.get();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    class ExecutorDemo2 implements Callable<Integer> {

        private Object o;

        public ExecutorDemo2(Object o) {
            this.o = o;
        }

        public Integer call() throws Exception {
            Integer result = 0;
            //do something

            return result;

        }

    }
}
