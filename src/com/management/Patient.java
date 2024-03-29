
package com.management;
import java.sql.*;
import java.util.Scanner;


public class Patient {
    private Connection con;
    private Scanner sc;
    
    public Patient(Connection con,Scanner sc){
        this.con=con;
        this.sc=sc;
    }
    public void add_patient(){
        System.out.println("Enter Patient name");
        String name=sc.next();
         System.out.println("Enter the age");
        int age=sc.nextInt();
         System.out.println("Enter th gender");
        String gender=sc.next();
        try{
           String q="insert into patients (name,age,gender) values (?,?,?)";
      PreparedStatement pstmt   = con.prepareStatement(q);
           pstmt.setString(1, name);
           pstmt.setInt(2, age);
           pstmt.setString(3, gender);
           int affectedRows=pstmt.executeUpdate();
           if(affectedRows>0){
               System.out.println("patient added successfully");
           }
           else{
               System.out.println("failed to add patient");
           }
        }
        catch(Exception e){
           e.printStackTrace();
        }
        
        
    }
    public void viewPatients(){
        String q="select * from patients";
        try{
              PreparedStatement pstmt   = con.prepareStatement(q);
              ResultSet rs=pstmt.executeQuery();
              System.out.println("Patients: ");
              System.out.println("+------------+--------------+-------+-------------+");
              System.out.println("| Patient Id | Name         | Age   | Gender      |");
              System.out.println("+------------+--------------+-------+-------------+");
              while(rs.next()){
                   int id=rs.getInt("id");
                   String name=rs.getString("name");
                   int age=rs.getInt("age");
                     String gender=rs.getString("gender");
                     System.out.printf("|%-12s|%-14s|%-7s|%-13s|\n",id,name,age,gender);
                     System.out.println("+------------+--------------+-------+-------------+");
              }
        }
    
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean checkPatientByID(int id){
           String q="select * from patients where id=?";
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
