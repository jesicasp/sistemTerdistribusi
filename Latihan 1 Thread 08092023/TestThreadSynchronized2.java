class TwoStrings {
    static void print(String str1, String str2) {
    System.out.print(str1);
    try {
    Thread.sleep(500);
    } catch (InterruptedException ie) {
    }
    System.out.println(str2);
    }
}
class PrintStringsThread implements Runnable {
    Thread thread;
    String str1, str2;
    TwoStrings ts;
    PrintStringsThread(String str1, String str2, TwoStrings ts)
    {
        this.str1 = str1;
    this.str2 = str2;
    this.ts = ts; 
    thread = new Thread(this);
    thread.start();
    }
    public void run() {
        synchronized (ts) {
        ts.print(str1, str2);
    }
    }
}
class TestThreadSynchronized2 {
 public static void main(String args[]) {
    TwoStrings ts = new TwoStrings();
    new PrintStringsThread("Hello ", "there.", ts);
    new PrintStringsThread("How are ", "you?", ts);
    new PrintStringsThread("Thank you ", "very much!", ts);
    }
}
