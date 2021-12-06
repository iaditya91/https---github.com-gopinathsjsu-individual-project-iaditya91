package Controller;
import java.io.IOException;
import java.util.ArrayList;

import Model.Database;
import Model.Items;

public class DatasetReader {
    private Database database = Database.getInstance();
    private FileHelper fileHelper;
    public DatasetReader(String path) throws IOException {
        fileHelper = new FileHelper(path);
    }

    public void datasetCreation() {
        try{
            fileHelper.fileReader(true);
        }catch (Exception e){
            System.out.println("Please enter a correct Dataset file path");
            System.exit(0);
        }
        readItems(fileHelper.getContentFile());

    }

    private void readItems(ArrayList<String> contentOfFile){
        System.out.println("Reading Database");
        for(int i=0;i<contentOfFile.size();i++){
            String[] splitItem = contentOfFile.get(i).split(",");
            database.getItemsMap().put(splitItem[1], new Items(splitItem[0],splitItem[1],Integer.parseInt(splitItem[2]),Double.parseDouble(splitItem[3])));
        }
        System.out.println("Database read");
    }
}
