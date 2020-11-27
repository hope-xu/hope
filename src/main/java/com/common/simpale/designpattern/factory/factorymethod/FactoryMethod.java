package com.common.simpale.designpattern.factory.factorymethod;

import com.common.simpale.designpattern.factory.IPay;

public interface FactoryMethod {

    IPay createPay(String payType);

}
