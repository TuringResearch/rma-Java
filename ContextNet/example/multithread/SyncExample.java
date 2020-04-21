package multithread;

public class SyncExample {
    int saldo = 1000;

    public static void main(String[] args) {
        SyncExample syncExample = new SyncExample();
        new Thread(() -> {
            while (true) {
                synchronized (syncExample) {
                    syncExample.operate(-1000);
                    syncExample.operate(1000);
                    try {
                        syncExample.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                synchronized (syncExample) {
                    System.out.println(syncExample.saldo);
                    delay(1000);
                    syncExample.notify();
                }
            }
        }).start();
    }

    private static void delay(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void operate(int value) {
        saldo += value;
    }
}
