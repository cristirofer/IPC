/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package extra;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Club;
import model.ClubDAOException;
import model.Member;
/**
 *
 * @author cesar
 */

public class Utils {
    private final int EQUALS = 0;  

    public static Boolean checkEmail(String email){
        if(email == null) return false;
        String regex = "^([\\w\\d][^\\s!#$%&'*+/=?`{|}~^-]{2,15})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static Boolean checkPassword(String password){     
        if (password == null) return false;  
        String regex =  "^[A-Za-z0-9]{6,15}$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    }
    
    public static Boolean checkPhone(String phone){     
        if (phone == null) return false;  
        String regex =  "^[0-9]{9}$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(phone); 
        return matcher.matches();
    }
    
    public static Boolean checkCreditCard(String CreditCard){     
        if (CreditCard.equals("")) return true;  
        String regexCr =  "^[0-9]{16}$"; 
        Pattern pattern1 = Pattern.compile(regexCr); 
        Matcher matcher1 = pattern1.matcher(CreditCard); 
        return matcher1.matches();
    }
    
    public static Boolean checkCSC(String CSC){     
        if (CSC.equals("")) return true;  
        String regexC =  "^[0-9]{3}$"; 
        Pattern pattern2 = Pattern.compile(regexC); 
        Matcher matcher2 = pattern2.matcher(CSC); 
        return matcher2.matches();
    }
    
    public static Boolean checkName(String name){     
        if (name == null) return false;  
        String regexC =  "^[a-zA-Z]{1,}$"; 
        Pattern pattern2 = Pattern.compile(regexC); 
        Matcher matcher2 = pattern2.matcher(name); 
        return matcher2.matches();
    }
    
    public static Boolean checkSurname(String surname){     
        if (surname == null) return false;  
        String regexC =  "^[a-zA-Z]{1,}$"; 
        Pattern pattern2 = Pattern.compile(regexC); 
        Matcher matcher2 = pattern2.matcher(surname); 
        return matcher2.matches();
    }
    
    public static int checkLogInUser(String nickname, String password) throws ClubDAOException, IOException{
        //if not a valid nickname, returns 0
        //if not valid password, returns 1
        //if all ok, returns 2
        int res = 0;
        if(nickname == null || password == null) return 0;
        List<Member> members = Club.getInstance().getMembers();
        for (Member member : members) {
            if(member.getNickName().equals(nickname)){
                res = 1;
                break;
            }
        }
        if(res == 1) {
            Member myMember = Club.getInstance().getMemberByCredentials(nickname, password);
            if (myMember != null) {
              return res+1;
            }else {
                return res;
            }
        }
        return res;
    }
    public static boolean isEven(int value){
        if ((value % 2) == 0) {
            return true;
        } else {
            return false;
        }   
    }
    
}
