
package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Doctor {
   private Connection con;
    
    public Doctor(Connection con){
        this.con=con;
        
    }
    
    public void viewDoctor(){
        String q="select * from doctor";
        try{
              PreparedStatement pstmt   = con.prepareStatement(q);
              ResultSet rs=pstmt.executeQuery();
              System.out.println("Doctors: ");
              System.out.println("+-----------+--------------+-----------------------+");
              System.out.println("| Doctor Id | Name         | Specialization        |");
              System.out.println("+-----------+--------------+-----------------------+");
              while(rs.next()){
                   int id=rs.getInt("id");
                   String name=rs.getString("name");
                     String specialization=rs.getString("specialization");
                     System.out.printf("|%-12s|%-14s|%-23s|\n", id,name,specialization);
                     System.out.println("+-----------+--------------+-----------------------+");
              }
        }
    
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean checkDoctorByID(int id){
           String q="select * from doctor where id=?";
           try{
              PreparedStatement pstmt   = con.prepareStatement(q);
              pstmt.setInt(1, id);
              ResultSet rs=pstmt.executeQuery();
              if(rs.next()){
                  return false;
              }
              else{
                  return true;
              }
              
           }
           catch(Exception e){
             e.printStackTrace();
           }
        return false;
       
          }  
}
