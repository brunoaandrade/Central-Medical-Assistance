/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.ActionType;
import com.maps.entities.DrugTakePrescripted;
import com.maps.entities.PrescriptedDrugAction;
import com.maps.services.GetPatientListResource;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class DrugTakePrescriptedManager {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static DrugTakePrescripted getPatientDrug(int id){
        DrugTakePrescripted drugTP = new DrugTakePrescripted();
        ActionType actionType = ActionType.Drugtake;
        
        try {
            /**
             * listActiondetails(in idAction, in actiontype, out result)
             * Add latter after update
             * listActiondetails(?,?,?);
             */
            CallableStatement cStmt = myCon.getCon().prepareCall("listActiondetails(?,?,?)");
            cStmt.setInt(1, id);
            cStmt.setString(2,actionType.toString());
            cStmt.registerOutParameter(3, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();

            while (rs.next())
            {
                drugTP.setIdTake(rs.getInt("idTake"));
                drugTP.setIdAction(rs.getInt("idAction"));
                drugTP.setTakeTime(rs.getTimestamp("idTake"));
                drugTP.setDrugName(rs.getString("drugName"));
                drugTP.setDescription(rs.getString("description"));
                //drugTP.setTakeTimePrescripted(rs.getTimestamp("takeTimePrescripted"));
                    
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        return drugTP;
    }
    
   public static boolean addPrescriptedDrugActionManager(PrescriptedDrugAction prescriptedDrugAction)
   {
          try {
            if(prescriptedDrugAction==null)
            {
                System.out.println("Null prescriptedDrugAction");
                return false;
            }
                // Filling the class up with Prescripted drug take info, using insertPrescriptedDrug
               //  Procedure with following prototype insertPrescriptedDrug (?,?,?,?,?,?,?,?,?)
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertPrescriptedDrug);
            cStmt.setInt(1, prescriptedDrugAction.getPatientAction().getIdPatient());
            cStmt.setInt(2, prescriptedDrugAction.getPrescriptedAction().getIdHealthCare());
            cStmt.setTimestamp(3, prescriptedDrugAction.getPrescriptedAction().getTimeToStart());
            cStmt.setInt(4, prescriptedDrugAction.getPrescriptedAction().getPeriod());
            cStmt.setInt(5, prescriptedDrugAction.getPrescriptedAction().getNumberreps());
            cStmt.setString(6, prescriptedDrugAction.getDrugTakePrescripted().getDrugName());
            cStmt.setString(7, prescriptedDrugAction.getDrugTakePrescripted().getDescription());
            cStmt.registerOutParameter(8, Types.INTEGER);
            cStmt.registerOutParameter(9, Types.INTEGER);
            cStmt.execute();
            
            prescriptedDrugAction.getDrugTakePrescripted().setIdTake(cStmt.getInt(9));
            System.out.println("ASDASJDLKAJSDKLJASDKLJALKSJ\n-----"+cStmt.getInt(8));
            return cStmt.getInt(8)==0;
                    
        } catch (SQLException ex) {
            Logger.getLogger(DrugTakePrescripted.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            ex.printStackTrace();
            return false;
        }
   }
}
