package learning;

public class Learning01 {
    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().isDaemon());
        }, "A");

        // 设置守护线程
        // 当JVM中只有守护线程，没有用户线程时，JVM会停止。
        a.setDaemon(true);
    }
}
