import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        OrderManager orderManager = new OrderManager();
        orderManager.placeOrder(1, BigDecimal.valueOf(30000000), 13, 5);
    }
}