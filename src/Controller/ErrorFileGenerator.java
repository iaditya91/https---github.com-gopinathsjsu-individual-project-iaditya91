package Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ErrorFileGenerator implements OutputFileGenerator{
    private ArrayList<String> data;

    @Override
    public void saveToFile(ArrayList<String> data, Path path) throws IOException {
        this.data = data;
        FileWriter errorFile = new FileWriter(path.getParent().toString()+"/Error_"+new SimpleDateFormat("MMddHHmm").format(new Date())+".txt");
        for(String line:data)
            errorFile.write(line+"\n");
        errorFile.close();
        System.out.println("Error file generated!");
    }
}
