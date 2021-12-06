package Controller;

import java.io.IOException;
import java.util.ArrayList;

import Model.Database;

public class CardReader {
    private Database database = Database.getInstance();
    private FileHelper fileHelper;
    public CardReader(String path) throws IOException {
        fileHelper = new FileHelper(path);
    }

    public void cardCreation() {
        try{
            fileHelper.fileReader(true);
        }catch (Exception e){
            System.out.println("Please enter a correct cards Dataset file path");
            System.exit(0);
        }
        readCards(fileHelper.getContentFile());
    }

    public void readCards(ArrayList<String> contentOfFile){
        System.out.println("Reading cards");
        for(int i=0;i<contentOfFile.size();i++){
            database.getCardsSet().add(contentOfFile.get(i));
        }
        System.out.println("Cards read");
    }
}
