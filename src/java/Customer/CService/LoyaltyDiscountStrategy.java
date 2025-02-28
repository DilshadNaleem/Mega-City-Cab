package Customer.CService;

import Customer.Interface.DiscountStrategy;

public class LoyaltyDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalFare) {
        return totalFare * 0.90; // 10% discount
    }
}
