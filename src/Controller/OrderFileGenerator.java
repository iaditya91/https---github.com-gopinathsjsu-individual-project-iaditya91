package Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderFileGenerator implements OutputFileGenerator{
    private ArrayList<String> data;

    @Override
    public void saveToFile(ArrayList<String> data, Path path) throws IOException {
        this.data = data;
        FileWriter checkoutFile = new FileWriter(path.getParent().toString()+"/OrderFile_"+new SimpleDateFormat("MMdd").format(new Date())+".csv");
        for(String line:data)
            checkoutFile.write(line+"\n");
        System.out.println("Order output file generated!");
        checkoutFile.close();
    }
}
