package prioritytesttwo;

class MessageSendingThreadTwo extends Thread {
    String message;

    MessageSendingThreadTwo(String str, int prio) {
        message = str;
        setPriority(prio);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            System.out.println(message + "(" + getPriority() + ")");
        }
    }
}

public class PriorityTestTwo {
    public static void main(String[] args) {
        MessageSendingThreadTwo tr1 = new MessageSendingThreadTwo("First", Thread.MAX_PRIORITY);
        MessageSendingThreadTwo tr2 = new MessageSendingThreadTwo("Second", Thread.NORM_PRIORITY);
        MessageSendingThreadTwo tr3 = new MessageSendingThreadTwo("Third", Thread.MIN_PRIORITY);
        tr1.start();
        tr2.start();
        tr3.start();
    }
}
