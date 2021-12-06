import java.io.IOException;

import Controller.CardReader;
import Controller.DatasetReader;
import Controller.InputController;

public class OrderApplication {
    public static void main(String[] args) throws IOException {
        if(args.length==0){
            System.out.println("Please enter the filepath of dataset");
            System.exit(0);
        }
        processStart(args);
    }
    private static void processStart(String[] args) throws IOException {
        DatasetReader datasetController = new DatasetReader(args[0]);
        datasetController.datasetCreation();
        CardReader cardController = new CardReader(args[1]);
        cardController.cardCreation();
        startOrder(args[2]);
    }

    private static void startOrder(String path){
        InputController inputContoller = new InputController(path);
        if(inputContoller.startOrder()){
            if(inputContoller.checkOrder()){
                inputContoller.calculateTotal();
                if(inputContoller.getTotal()>0){
                    inputContoller.checkoutOrder();
                    System.out.println("Total amount is of order is $" + inputContoller.getTotal());
                }
            }else {
                System.out.println("Error file is generated");
                inputContoller.generateOutputFile();
            }
        }else {
            System.out.println("Order file not found");
        }
    }
}
