/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clipboardwatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rui
 */
public class Writer {
    private final BufferedWriter writer;

    public Writer() throws IOException {
        File f = new File("content.txt");
        writer = new BufferedWriter(new FileWriter(f));
    }
    
    public void close() throws IOException {
        if(writer != null)
            writer.close();
    }
    
    public void write(String s) throws IOException{
        writer.write(s);
        writer.newLine();
        writer.flush();
    }
}
