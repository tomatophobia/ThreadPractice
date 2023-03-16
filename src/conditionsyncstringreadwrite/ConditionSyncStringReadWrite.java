package conditionsyncstringreadwrite;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class StringComm {
    String newString;
    boolean isNewString = false;

    private final ReentrantLock entLock = new ReentrantLock();
    private final Condition readCond = entLock.newCondition();
    private final Condition writeCond = entLock.newCondition();

    public void setNewString(String newString) {
        entLock.lock();
        try {
            if (isNewString) writeCond.await();

            this.newString = newString;
            isNewString = true;
            readCond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            entLock.unlock();
        }
    }

    public String getNewString() {
        String resStr = null;
        entLock.lock();
        try {
            if (!isNewString) readCond.await();

            resStr = newString;
            isNewString = false;
            writeCond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            entLock.unlock();
        }
        return resStr;
    }
}

class StringReader extends Thread {
    StringComm comm;

    public StringReader(StringComm comm) {
        this.comm = comm;
    }

    @Override
    public void run() {
        Scanner keyboard = new Scanner(System.in);
        String readStr;

        for (int i = 0; i < 5; i++) {
            readStr = keyboard.nextLine();
            comm.setNewString(readStr);
        }
    }
}

class StringWriter extends Thread {
    StringComm comm;

    public StringWriter(StringComm comm) {
        this.comm = comm;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("read string: " + comm.getNewString());
        }
    }
}

class ConditionSyncStringReadWrite {
    public static void main(String[] args) {
        StringComm stringComm = new StringComm();
        StringReader sr = new StringReader(stringComm);
        StringWriter sw = new StringWriter(stringComm);

        System.out.println("입출력 쓰레드의 실행...");
        sr.start();
        sw.start();
    }
}
