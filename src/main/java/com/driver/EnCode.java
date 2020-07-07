package com.driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义加密注释
 * @author lijun
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnCode {

	/**
	 * 默认加密字段
	 */
	String[] values() default {"id","countryId","customerId","exportTransportId","exportOrderId",
		"exportPnrregId","fitId","fiaId","pnrregId","fciId","fusId","fliId","filesId","fdiId",
		"fltId","importOrderId","importPnrregId","createBy","updateBy","dictionnaryTypeId","dictionnaryItemId","printTemplateId","awbId","generalWarehouseId",
		"driverId","userId","simId","fbwId","fbwoId","fbwpId","bondedWarehouseId","typeId","costModelId","confirmBy","bondedWarehouseId","sPortCodeId","ePortCodeId",
		"consignorCustomerAddressId","consigneeCustomerAddressId","agentPortId","exchangeRateId","ORDERID","ID","orderid","orderId","BONDED_ID","BONDED_PNRREG_ID","fipId","fioId"
		,"planDetailId","mainAwbId"};
}
