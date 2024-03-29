
package com.management;
import java.sql.*;
import java.util.Scanner;

public class Hospital_management {
  private static final String url="jdbc:mysql://localhost:3306/hospital";
private static final String username="root";
private static final String password="chilman23";
public static void main(String [] args){
       Scanner  sc=new Scanner(System.in);

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,username,password);
        Patient p=new Patient(con,sc);
        Doctor d=new Doctor(con);
        while(true){
        System.out.println("Hospital anagement System");
                System.out.println("1.Add Patient");
        System.out.println("2.view Patients");
        System.out.println("3.view Doctors");
        System.out.println("4.Book appointment");
                System.out.println("5.Exit");
        System.out.println("Enter your choice");
       int choice=sc.nextInt();
       switch(choice){
           case 1:
               p.add_patient();
               System.out.println();
               break;
           case 2:
               p.viewPatients();
                              System.out.println();
                  break;
           case 3:
               d.viewDoctor();
                              System.out.println();
                   break;
           case 4:
               bookAppointment(p,d,con,sc);
                              System.out.println();
                          break;
           case 5:
               System.out.println("Thank you for using Hospital management System");
               return;
           default:
               System.out.println("Enter valid choice");
               break;
       }

    }
    }
    catch(Exception e){
        e.printStackTrace();
    }
    
}
public static void bookAppointment(Patient p,Doctor d,Connection con,Scanner sc){
    System.out.println("enter Patients id : ");
    int patientId=sc.nextInt();
        System.out.println("enter Doctor id : ");
        int doctorId=sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD) : ");
        String AppointmentDate=sc.next();
      //  if(p.checkPatientByID(patientId) && d.checkDoctorByID(doctorId)){
          if(checkDoctorAvailability(doctorId,AppointmentDate,con)){
              String query="insert into appointments(patient_id,doctor_id,appointment_date) values (?,?,?)";
              try{
                                PreparedStatement pstmt   = con.prepareStatement(query);
                               pstmt.setInt(1, patientId);
                               pstmt.setInt(2, doctorId);
                               pstmt.setString(3, AppointmentDate);
                               int affectedrows=pstmt.executeUpdate();
                               if(affectedrows>0){
                                   System.out.println("Appointment booked");
                               }
                               else{
                                   System.out.println("Failed to book appointment");
                               }
              }
              catch(Exception e){
                  e.printStackTrace();
              }
          }  
          else{
              System.out.println("Doctor is not available on this date");
          }
      //
//}
        //else{
       //     System.out.println("Either doctor or PAtient doesn't exist!!!");
      //  }
}

public static boolean checkDoctorAvailability(int doctorId,String AppointmentDate,Connection con){
    String qu="select count(*) from appointments where doctor_id=? and appointment_date=?";
    try{
                     PreparedStatement pstmt   = con.prepareStatement(qu);
                     pstmt.setInt(1, doctorId);
                     pstmt.setString(2, AppointmentDate);
                     ResultSet rs=pstmt.executeQuery();
                     if(rs.next()){
                         int count=rs.getInt(1);
                         if(count==0){
                             return true;
                         }
                         else{
                             return false;
                         }
                     }
    }
                     catch(Exception e){
                         e.printStackTrace();
                     }
                     
                 return false;            
                             
}
       

}
