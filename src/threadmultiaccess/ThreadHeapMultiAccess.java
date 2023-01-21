package threadmultiaccess;

class Sum {
    int num;
    public Sum() {
        num = 0;
    }
    public void addNum(int n) {
        num += n;
    }
    public int getNum() {
        return num;
    }
}

class AdderThread implements Runnable {
    int start, end;
    Sum sumInst;

    public AdderThread(Sum sum, int s, int e) {
        sumInst = sum;
        start = s;
        end = e;
    }

    public void run() {
        for (int i = start; i <= end; i++)
            sumInst.addNum(i);
    }
}

public class ThreadHeapMultiAccess {
    public static void main(String[] args) {
        Sum s = new Sum();
        AdderThread at1 = new AdderThread(s, 1, 50);
        AdderThread at2 = new AdderThread(s, 1, 100);
        Thread tr1 = new Thread(at1);
        Thread tr2 = new Thread(at2);
        tr1.start();
        tr2.start();
        try {
            tr1.join();
            tr2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(s.getNum());
    }
}
