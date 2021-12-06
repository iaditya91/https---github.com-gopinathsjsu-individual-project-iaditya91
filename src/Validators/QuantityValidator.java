package Validators;

import java.util.ArrayList;
import Model.Database;
import Model.OrderItem;

public class QuantityValidator implements Validator{
    private Validator next= null;
    @Override
    public boolean validate(ArrayList<OrderItem> items) {
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            if(database.getItemsMap().get(orderItem.getName()).getQuantity()<orderItem.getQuantity()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void nextValidator(Validator next) {
        this.next = next ;
    }
}
