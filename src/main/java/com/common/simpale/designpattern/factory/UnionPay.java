package com.common.simpale.designpattern.factory;

public class UnionPay implements IPay{

    @Override
    public boolean pay() {
        // 支付相关逻辑...
        return true;
    }

}
