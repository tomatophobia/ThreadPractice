package newspaperstory;

class NewsPaper {
    String todayNews;

    public String getTodayNews() {
        return todayNews;
    }

    public void setTodayNews(String todayNews) {
        this.todayNews = todayNews;
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

class NewsPaperStory {
    public static void main(String[] args) {
        NewsPaper paper = new NewsPaper();
        NewsReader reader = new NewsReader(paper);
        NewsWriter writer = new NewsWriter(paper);

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
