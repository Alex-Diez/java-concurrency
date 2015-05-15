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
                boolean isLeftChopStickTaken = leftChopStick.tryLock(3, TimeUnit.SECONDS);
                try {
                    if (isLeftChopStickTaken) {
                        System.out.printf("Philosopher N %d takes %s which is left for him", number, leftChopStick);
                        System.out.println();
                        boolean isRightChopStickTaken = rightChopStick.tryLock(3, TimeUnit.SECONDS);
                        if (isRightChopStickTaken) {
                            System.out.printf("Philosopher N %d takes %s which is right for him", number, rightChopStick);
                            System.out.println();
                            long currentTimeStamp = System.currentTimeMillis();
                            long timeToEat = (currentTimeStamp - eatingLastTime) / 1000 * FEED_SECOND_RATE;
                            System.out.printf("Philosopher N %d starts eating", number);
                            System.out.println();
                            Thread.sleep(timeToEat);
                            System.out.printf("Philosopher N %d stops eating", number);
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
