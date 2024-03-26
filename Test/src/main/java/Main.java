import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        HashedWheelTimer timer = new HashedWheelTimer();

        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("Task executed at: " + new Date());
            }
        };

        // 定时任务，延迟2秒执行
        Timeout timeout1 = timer.newTimeout(task, 2, TimeUnit.SECONDS);
        Thread.sleep(1000);
        Timeout timeout2 = timer.newTimeout(task, 2, TimeUnit.SECONDS);
        Thread.sleep(1000);
        Timeout timeout3 = timer.newTimeout(task, 2, TimeUnit.SECONDS);
        Thread.sleep(1000);
        Timeout timeout4 = timer.newTimeout(task, 2, TimeUnit.SECONDS);
        // 等待一段时间后停止定时器
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Thread.sleep(3000);

        timer.stop();
    }
}
