package com.common.simpale.designpattern.factory.factorymethod;

import com.common.simpale.designpattern.factory.IPay;

public class BFactory implements FactoryMethod {

    @Override
    public IPay createPay(String payType) {
        return null;
    }

}
