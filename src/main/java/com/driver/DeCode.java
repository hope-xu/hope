package com.driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义解密注释
 * @author lijun
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeCode {
	
	/**
	 * 默认解密字段
	 * @return
	 */
   String[] values() default {"id","ids","customerId","exportTransportId","fileUploadRecordId","exportOrderId",
	   "exportPnrregId","awbId","orderId","fiaId","fitId","fciId","fusId","fliId","filesId","fdiId","fltId",
	   "importOrderId","importPnrregId","dictionnaryTypeId","dictionnaryItemId","printTemplateId","awbIds","generalWarehouseId",
	   "userId","simId","bondedWarehouseId","bondedOutputId","bondedOrderId","fbwId","fbwoId","fbwpId","orderIds","costModelId","typeId","planCostId",
	    "planBillIds","financePrepayIds","financeCostId","checkInfoIds","planInvoiceIds","sPortCodeId","ePortCodeId","consignorCustomerAddressId","consigneeCustomerAddressId",
	    "agentPortId","exchangeRateId","pnrregId","ORDERID","bondedId","fipId","fioId","planDetailId","billId","mainAwbId"};

}
