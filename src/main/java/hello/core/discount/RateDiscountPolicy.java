package hello.core.discount;

import hello.core.Member.Grade;
import hello.core.Member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    final double discountRate = 0.1;

    int discountAmount = 0;
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            discountAmount = (int)(price * discountRate);
        }
        return discountAmount;
    }
}
