package com.common.simpale.designpattern.factory.factorymethod;

import com.common.simpale.designpattern.factory.IPay;

public class PayService {

    public void pay(String payType) {
        FactoryMethod aFactory = new AFactory();
        IPay pay = aFactory.createPay(payType);

    }

}
