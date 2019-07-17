package timeout;

import java.util.concurrent.*;

/**
 * @author 彭方林
 * @version 1.0
 * @date 2019/7/17 13:42
 **/
public class ThreadTimeout {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = () -> {
            Thread.sleep(1000 * 3);
            return "线程执行完成";
        };
        try {
            Future<String> future = executor.submit(callable);
            String obj = future.get(4000, TimeUnit.MILLISECONDS);
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException e) {
            System.out.println("执行超时");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("执行失败");
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
