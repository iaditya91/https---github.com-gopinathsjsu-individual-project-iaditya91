package Model;

public class Card {

    private String cardNumber;

    public Card(String card){
        this.cardNumber = card;
    }

    public String getCardNumbers() {
        return cardNumber;
    }

    public void setCardNumbers(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
