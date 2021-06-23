package edu.tamu.aser.tests.examples.consisitency;

public class Consisitency  implements Runnable{

    public static int a = 0;
    public static int b = 0;
    private int num;

    public Consisitency(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        a = num;
        b = num;
    }
}
