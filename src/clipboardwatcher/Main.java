/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clipboardwatcher;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author Rui
 */
public class Main implements Observer{
    private final Writer writer;
    private final Watcher watcher;

    public Main() throws IOException {
        this.writer = new Writer();
        this.watcher = new Watcher(writer, this);
    }
    
    public void run() {
        Thread t = new Thread(watcher);
        t.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        String content = (String) arg;
        System.out.println("Detected: " + content);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.run();
    }
}
