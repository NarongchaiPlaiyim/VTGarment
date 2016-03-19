package com.ese.service;

import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LoginService extends Service{
    private static final long serialVersionUID = 4112578634088874840L;

    public boolean isUserExist(final String userName, final String password){
        log.debug("-- isUserExist({}, {})", userName, password);
        boolean result = Utils.TRUE;
        try {
//            staffModel = staffDAO.findByUserNameAndPassword(userName, password);
//            if(Utils.isNull(staffModel)){
//                result = !result;
//            }
            return result;
        } catch (Exception e) {
            log.error("Exception while calling isUserExist()", e);
            return !result;
        }
    }





    public void test(String startBarcode, String finishBarcode){
        try {

            System.out.println("test");
//            System.out.println(warehouseDAO.findByStatus2().toString()+"");
//            System.out.println(locationItemsDAO.findLocationByItemId(58));
//            System.out.println(locationDAO.getLocationModelList());
//            barcodeRegisterDAO.getDataTable();
//            List<BarcodeRegisterModel> barcodeRegisterModelList = barcodeRegisterDAO.findByIsValid();
//            System.out.println(barcodeRegisterModelList.toString());
//            System.out.println(barcodeRegisterModelList.size());
//            System.out.println("Price is "+barcodeRegisterDAO.getPrice("I-0000100"));

//            System.out.println(barcodeRegisterDAO.checkBarcode2(startBarcode, finishBarcode));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
