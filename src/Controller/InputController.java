package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import Model.Database;
import Model.Items;
import Model.OrderItem;
import Model.Order;
import Validators.CapacityValidator;
import Validators.QuantityValidator;
import Validators.Validator;

public class InputController {
    private Database database = Database.getInstance();
    private Order currentOrder = new Order();
    private FileHelper fileHelper;
    private ArrayList<String> output = new ArrayList<>();
    private ArrayList<OrderItem> items = new ArrayList<>();
    private HashSet<String> creditCards = database.getCardsSet();
    private double total = 0;

    public InputController() {}

    public InputController(String filePath){
        fileHelper = new FileHelper(filePath);
    }

    public void addToOutput(String str){
        output.add(str);
    }

    public boolean startOrder() {
        try{
            fileHelper.fileReader(true);
        }catch (Exception e){
            return false;
        }
        getItems(fileHelper.getContentFile());
        return true;
    }
    public boolean checkOrder() {
        checkItemStock();
        return output.size()==0;
    }

    public void calculateTotal() {
        for(OrderItem item: items){
            total += item.getQuantity()*database.getItemsMap().get(item.getName()).getPrice();
        }
        currentOrder.setTotalPrice(total);
    }

    public double getTotal() {
    return currentOrder.getTotalPrice();
    }

    public void checkoutOrder() {
        for(OrderItem orderItem: items){
            Items item = database.getItemsMap().get(orderItem.getName());
            item.setQuantity(item.getQuantity()-orderItem.getQuantity());
        }
        for(String credit:creditCards){
            if(!database.getCardsSet().contains(credit)){
                database.getCardsSet().add(credit);
            }
        }
        generateOutputFile();
    }

    public void printMessage() {
        for(String line: output){
            System.out.println(line);
        }
    }

    public void getItems(ArrayList<String> fileContent){
        System.out.println("Reading cards");
        for(String line: fileContent){
            String[] item = line.split(",");
            if(database.getItemsMap().containsKey(item[0])){
                items.add(new OrderItem(item[0],Integer.parseInt(item[1]),item[2].replaceAll("\\s+","")));
            }else{
                output.add(item[0]+" not found");
            }
        }
        if(!output.isEmpty()){
            items.clear();
        }
    }

    public boolean checkItemStock() {
        boolean validate;
        StringBuilder sb = new StringBuilder();
        database.getOrdersList().add(currentOrder);
        Validator itemStock = new QuantityValidator();
        Validator itemCategory = new CapacityValidator();
        itemStock.nextValidator(itemCategory);
        if(!itemStock.validate(items)){
            validate = true;
        }else if(!itemCategory.validate(items)){
            output.add("Limit has exceeded for one of the categories");
        }
        for(OrderItem orderItem: items){
            Items item = database.getItemsMap().get(orderItem.getName());
            if(item.getQuantity()<orderItem.getQuantity()){
                if(sb.length()>0)
                    sb.append(",");
                sb.append(orderItem.getName()+"("+item.getQuantity()+")");
            }else{
                if(!creditCards.contains(orderItem.getCardDetails()))
                    creditCards.add(orderItem.getCardDetails());
            }
        }
        if(sb.length()>0){
            output.add("Please correct item quantities");
            output.add(sb.toString());
        }
        return sb.length()==0;
    }

    public void generateOutputFile(){
        if(output.size()==0){
            output.add("Amount Paid");
            output.add(Double.toString((currentOrder.getTotalPrice())));
            try{
                fileHelper.writeOuput(output,false);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                fileHelper.writeOuput(output,true);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
