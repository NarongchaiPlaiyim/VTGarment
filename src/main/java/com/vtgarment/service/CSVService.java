package com.vtgarment.service;

import com.vtgarment.model.view.ImportProductionPlanCSVView;
import com.vtgarment.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CSVService extends Service{
    private final String EMPTY = "";
    private final String UTF_8 = "UTF-8";
    private final String MS874 = "MS874";

    public List<ImportProductionPlanCSVView> CSVImport(final InputStream inputStream) throws Exception{
        log.debug("-- CSVImport()");
        ICsvBeanReader beanReader = null;
        ImportProductionPlanCSVView csvModel = null;
        List<ImportProductionPlanCSVView> csvModelList = null;
        try {
            beanReader = new CsvBeanReader(new InputStreamReader(inputStream, MS874), CsvPreference.STANDARD_PREFERENCE);
            final String[] header = beanReader.getHeader(true);
            csvModelList = new ArrayList<ImportProductionPlanCSVView>();
            while( (csvModel = beanReader.read(ImportProductionPlanCSVView.class, header)) != null ) {
                csvModelList.add(csvModel);
            }
            return csvModelList;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        } finally {
            if(!Utils.isNull(beanReader)){
                try {
                    beanReader.close();
                } catch (Exception e) {
                    log.error("",e);
                }
            }
            if(!Utils.isNull(inputStream)){
                inputStream.close();
            }
        }
    }
//
//    public void CSVExport(final String fullPath, final List<User> userList) throws Exception{
//        log.debug("-- CSVExport(fullPath : {} List<User>.size() {})", fullPath, userList.size());
//        ICsvBeanWriter beanWriter = null;
//        try {
//            File fileDir = new File(fullPath);
//            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), MS874));
//            beanWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
////            final String[] header = new String[] {
////                    "userId", "userName", "active", "role",
////                    "team", "department", "division", "region",
////                    "title", "status" };
//
//            final String[] header = new String[] {
//                    "Seq", "EmployeeID", "EmployeeName", "TeamID",
//                    "TeamName", "CreateDate", "LastSignOnDate", "Status",
//                    "NumberOfDays"};
//
//            beanWriter.writeHeader(header);
//            List<CSVModel> csvModelList = covertModelToCSV(userList);
//            for (final CSVModel csvModel : csvModelList) {
//                beanWriter.write(csvModel, header/*, getProcessors()*/);
//            }
//            beanWriter.flush();
//        } catch (Exception e) {
//            log.error("", e);
//            throw e;
//        } finally {
//            if(!Util.isNull(beanWriter)){
//                try {
//                    beanWriter.close();
//                } catch (Exception e) {
//                    log.error("",e);
//                }
//            }
//        }
//    }
//
//    public void CSVExport(final String fullPath, final List<ResultModel> resultModelList, final String test) throws Exception{
//        log.debug("-- CSVExport(fullPath : {} List<ResultModel>.size() {})", fullPath, resultModelList.size());
//        ICsvBeanWriter beanWriter = null;
//        try {
//            File fileDir = new File(fullPath);
//            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), UTF_8));
//            beanWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
//            final String[] header = new String[] {
//                    "command", "id", "result", "detail"};
//            beanWriter.writeHeader(header);
//            for (final ResultModel resultModel : resultModelList) {
//                beanWriter.write(resultModel, header, getProcessor());
//            }
//            beanWriter.flush();
//        } catch (Exception e) {
//            log.error("", e);
//            throw e;
//        } finally {
//            if(!Util.isNull(beanWriter)){
//                try {
//                    beanWriter.close();
//                } catch (Exception e) {
//                    log.error("",e);
//                }
//            }
//        }
//    }
//
//    private List<CSVModel> covertModelToCSV(final List<User> userList) throws Exception{
//        List<CSVModel> csvModelList = null;
//        CSVModel csvModel = null;
//        if(!Util.isZero(userList.size())){
//            csvModelList = new ArrayList<CSVModel>();
//            int round = 1;
//            for(final User model : userList){
//                csvModel = new CSVModel();
//
//                csvModel.setSeq(String.valueOf(round++));
//                csvModel.setEmployeeID(model.getId());
//                csvModel.setEmployeeName(model.getUserName());
//
//                if(!Util.isNull(model.getTeam())){
//                    csvModel.setTeamID(model.getTeam().getTeam_code());
//                    csvModel.setTeamName(model.getTeam().getTeam_name());
//                } else {
//                    csvModel.setTeamID(EMPTY);
//                    csvModel.setTeamName(EMPTY);
//                }
//
//                if(!Util.isNull(model.getCreateDate())){
//                    csvModel.setCreateDate(DateTimeUtil.convertToStringDDMMYYYY(model.getCreateDate()));
//                } else {
//                    csvModel.setCreateDate(EMPTY);
//                }
//
//                if(!Util.isNull(model.getLastLogon())){
//                    csvModel.setLastSignOnDate(DateTimeUtil.convertToStringDDMMYYYY(model.getLastLogon()));
//                } else {
//                    csvModel.setLastSignOnDate(EMPTY);
//                }
//
//                if(!Util.isNull(model.getUserStatus())){
//                    csvModel.setStatus(model.getUserStatus().name());
//                } else {
//                    csvModel.setStatus(EMPTY);
//                }
//
//                if(!Util.isNull(model.getLastLogon())){
//                    csvModel.setNumberOfDays(calNumberOfDays(model.getLastLogon()));
//                } else if(!Util.isNull(model.getCreateDate())){
//                    csvModel.setNumberOfDays(calNumberOfDays(model.getCreateDate()));
//                } else {
//                    csvModel.setNumberOfDays(EMPTY);
//                }
//
//
//                csvModelList.add(csvModel);
//            }
//        }
//        return csvModelList;
//    }
//
//    private String calNumberOfDays(final Date date){
//        int day = 0;
//        try {
//            day = (int) ((DateTime.now().toDate().getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
//        } catch (Exception e){
//
//        }
//        return String.valueOf(day);
//    }
//
//    private CellProcessor[] getProcessor() {
//        final CellProcessor[] processors = new CellProcessor[] {
//                null, // commandType
//                null, // id
//                null, // result
//                null, // detail
//        };
//        return processors;
//    }
//    private CellProcessor[] getProcessors() {
//        final CellProcessor[] processors = new CellProcessor[] {
//                new UniqueHashCode(), // userId (must be unique)
//                null, // userName
//                null, // active
//                null, // role
//                null, // department
//                null, // division
//                null, // region
//                null, // team
//                null, // title
//                null, // status
//        };
//        return processors;
//    }
//
//    private CellProcessor[] getProcessorsss() {
//        final CellProcessor[] processors = new CellProcessor[] {
//                null, // commandType
//                null, // userID
//                null, // userName
//                null, // active
//                null, // role
//                null, // department
//                null, // division
//                null, // region
//                null, // team
//                null, // title
//                null, // status
//        };
//        return processors;
//    }
}