package Validators;

import java.util.ArrayList;
import java.util.HashMap;
import Model.Database;
import Model.OrderItem;

public class CapacityValidator implements Validator{
    private Validator next= null;
    private final int luxCap = 3;
    private final int essCap = 5;
    private final int miscCap = 6;
    @Override
    public boolean validate(ArrayList<OrderItem> items) {
        HashMap<String,Integer> map = new HashMap<>();
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            map.put(database.getItemsMap().get(orderItem.getName()).getCategory(),map.getOrDefault(database.getItemsMap().get(orderItem.getName()).getCategory(),0)+orderItem.getQuantity());
        }
        if(map.getOrDefault("Luxury",0)>luxCap){
            return false;
        }else if(map.getOrDefault("Essential",0)>essCap){
            return false;
        }else if(map.getOrDefault("Misc",0)>miscCap){
            return false;
        }
        return true;
    }
    @Override
    public void nextValidator(Validator next) {
        this.next = next ;
    }
}
