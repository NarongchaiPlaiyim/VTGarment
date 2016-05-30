package com.vtgarment.service;

import com.vtgarment.model.dao.LineDAO;
import com.vtgarment.model.dao.ProductionPlanDAO;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.db.ProductionPlanModel;
import com.vtgarment.model.view.ImportProductionPlanCSVView;
import com.vtgarment.model.view.System.ImportProductionPlanView;
import com.vtgarment.transform.ImportProductionPlanTransform;
import com.vtgarment.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakor on 27/05/2016.
 */
@Component
@Transactional
public class ImportProductionPlanService extends Service{
    @Resource private CSVService csvService;
    @Resource private ProductionPlanDAO productionPlanDAO;
    @Resource private ImportProductionPlanTransform importProductionPlanTransform;
    @Resource private LineDAO lineDAO;

//    @Resource private ProductionPlanDAOERP productionPlanDAOERP;

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private final String SCHEMA = "/vt_line?";
    private final String CLASSNAME = "com.mysql.jdbc.Driver";
    private final String JDBC = "jdbc:mysql://";
    private final String AND = "&";


    private final String SQL_INSERT = "INSERT INTO vt_line.production_plan (co, line, qty, shipment, style) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_DELETE = "DELETE FROM vt_line.production_plan";

    public ArrayList<ImportProductionPlanView> findAll() throws Exception{
        return importProductionPlanTransform.transformModelListToViewList(productionPlanDAO.findAll());
    }

    public ArrayList<ImportProductionPlanView> importProcess(final InputStream inputStream) throws Exception{
        log.debug("-- importProcess()");
        List<ImportProductionPlanCSVView> importProductionPlanCSVViews = Utils.safetyList(csvService.CSVImport(inputStream));
        return importProductionPlanTransform.transformViewListToViewList(importProductionPlanCSVViews);
    }

    public void deploy(ArrayList<ImportProductionPlanView> viewList) throws Exception{
        log.debug("--deploy() : {}",viewList);

        productionPlanDAO.delete(productionPlanDAO.findAll());
        ArrayList<ProductionPlanModel> modelList = importProductionPlanTransform.transformViewListToModelList(viewList);
        productionPlanDAO.persist(modelList);

        List<LineModel> lineModelList = lineDAO.findAll();

        if (Utils.isSafetyList(lineModelList)){
            for (LineModel model : lineModelList){
                insertToMySQL(model, modelList);
            }
        }

//        statement.close();
//        connect.close();
    }

    public void insertToMySQL(LineModel lineModel, ArrayList<ProductionPlanModel> modelList) throws Exception {

        Class.forName(CLASSNAME);


        StringBuilder connection = new StringBuilder();

        if (!Utils.isNull(lineModel.getIpAddress()) && !Utils.isEmpty(lineModel.getIpAddress())){

            String user = "user=" + lineModel.getUserName();
            String password = "password=" + lineModel.getPassword();

            connection.append(JDBC).append(lineModel.getIpAddress()).append(SCHEMA).append(user).append(AND).append(password);

            connect = DriverManager.getConnection(connection.toString());

            statement = connect.createStatement();

            deleteToMySQL();

//            Insert
            preparedStatement = connect.prepareStatement(SQL_INSERT);

            for (ProductionPlanModel model : modelList){
                preparedStatement.setString(1, model.getCo());
                preparedStatement.setString(2, model.getLine());
                preparedStatement.setInt(3, model.getQty());
                preparedStatement.setDate(4, new java.sql.Date(model.getShipment().getTime()));
                preparedStatement.setString(5, model.getStyle());

                preparedStatement.executeUpdate();
            }
        }
    }

    public void deleteToMySQL() throws Exception{
        Class.forName(CLASSNAME);
        statement.executeUpdate(SQL_DELETE);
    }
}
