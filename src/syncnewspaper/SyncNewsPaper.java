package syncnewspaper;

class NewsPaper {
    String todayNews;
    boolean isTodayNews = false;

    public String getTodayNews() {
        if (!isTodayNews) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return todayNews;
    }

    public void setTodayNews(String todayNews) {
        this.todayNews = todayNews;
        isTodayNews = true;

        synchronized (this) {
            notifyAll();
        }
    }
}

class NewsWriter extends Thread {
    NewsPaper paper;

    public NewsWriter(NewsPaper paper) {
        this.paper = paper;
    }

    @Override
    public void run() {
        paper.setTodayNews("자바의 열기가 뜨겁습니다.");
    }
}

class NewsReader extends Thread {
    NewsPaper paper;

    public NewsReader(NewsPaper paper) {
        this.paper = paper;
    }

    @Override
    public void run() {
        System.out.println("오늘의 뉴스: " + paper.getTodayNews());
    }
}

class SyncNewsPaper {
    public static void main(String[] args) {
        NewsPaper paper = new NewsPaper();
        NewsReader reader1 = new NewsReader(paper);
        NewsReader reader2 = new NewsReader(paper);
        NewsWriter writer = new NewsWriter(paper);

        try {
            reader1.start();
            reader2.start();

            Thread.sleep(1000);
            writer.start();

            reader1.join();
            reader2.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
