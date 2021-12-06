package Validators;

import java.util.ArrayList;

import Model.OrderItem;

public interface Validator {
    boolean validate(ArrayList<OrderItem> items);
    void nextValidator(Validator next);
}
