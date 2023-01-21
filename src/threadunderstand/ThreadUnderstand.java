package threadunderstand;

class ShowThread extends Thread {
    String threadName;

    public ShowThread(String name) {
        threadName = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("안녕하세요. " + threadName + "입니다.");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadUnderstand {
    public static void main(String[] args) {
        ShowThread st1 = new ShowThread("멋진");
        ShowThread st2 = new ShowThread("예쁜");
        st1.start();
        st2.start();
    }
}
