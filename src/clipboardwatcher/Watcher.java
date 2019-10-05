/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clipboardwatcher;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
/**
 *
 * @author Rui
 */
public class Watcher extends Observable implements Runnable{
    private final Writer writer;
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();;
    private volatile boolean running;
    private static final String EXIT = "EXIT";
    
    private Observer obs;
    
    public Watcher(Writer writer, Observer obs) {
        this.writer = writer;
        this.obs = obs;
        running = true;
    }
    
    private void terminate() throws IOException {
        running = false;
        deleteObservers();
        writer.close();
    }
    
    @Override
    public void run() {
        if(clipboard == null) {
            throw new NullPointerException("Clipboard is null.");
        } else {
            addObserver(obs);
        }
        
        String lastContent = "";
        while(running) {
            try{
                Thread.sleep(150);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            
            try {
                List<DataFlavor> flavors = Arrays.asList(clipboard.getAvailableDataFlavors());
                if(flavors.contains(DataFlavor.stringFlavor)) {
                    String data = (String)clipboard.getData(DataFlavor.stringFlavor);
                    if(!data.equals(lastContent)) {
                        if(lastContent.isEmpty()) {
                            lastContent = data;
                            continue;
                        }
                        
                        lastContent = data;
                        setChanged();
                        notifyObservers(data);
                        
                        if(data.toUpperCase().equals(EXIT)) {
                            terminate();
                            continue;
                        }
                        
                        writer.write(lastContent);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
