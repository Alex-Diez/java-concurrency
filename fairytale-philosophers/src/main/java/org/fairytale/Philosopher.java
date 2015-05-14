package org.fairytale;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Philosopher
        implements Runnable {

    private static final int FEED_SECOND_RATE = 1000;

    private long eatingLastTime;
    private final Lock leftChopStick;
    private final Lock rightChopStick;
    private final CountDownLatch countDown;
    private final int number;

    public Philosopher(int number, Lock leftChopStick, Lock rightChopStick, CountDownLatch countDown) {
        this.number = number;
        this.leftChopStick = leftChopStick;
        this.rightChopStick = rightChopStick;
        this.countDown = countDown;
        eatingLastTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            countDown.await();
            while (true) {
                boolean isLeftChopStickTaken = leftChopStick.tryLock(100, TimeUnit.MILLISECONDS);
                try {
                    if (isLeftChopStickTaken) {
                        System.out.printf("Philosopher N %d takes left chop stick\n", number);
                        System.out.println();
                        boolean isRightChopStickTaken = rightChopStick.tryLock(100, TimeUnit.MILLISECONDS);
                        if (isRightChopStickTaken) {
                            System.out.printf("Philosopher N %d takes right chop stick\n", number);
                            System.out.println();
                            long currentTimeStamp = System.currentTimeMillis();
                            long timeToEat = (currentTimeStamp - eatingLastTime) / 1000 * FEED_SECOND_RATE;
                            System.out.printf("Philosopher N %d takes starts eating\n", number);
                            System.out.println();
                            Thread.sleep(timeToEat);
                            System.out.printf("Philosopher N %d takes stops eating", number);
                            System.out.println();
                            eatingLastTime = System.currentTimeMillis();
                        }
                    }
                }
                finally {
                    leftChopStick.unlock();
                    rightChopStick.unlock();
                }
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
