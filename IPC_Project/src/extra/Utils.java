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
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static Boolean checkPassword(String password){     
        if (password == null) return false;  
        String regex =  "^[A-Za-z0-9]{8,15}$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    }
    public static int checkLogInUser(String nickname, String password) throws ClubDAOException, IOException{
        //if not a valid nickname, returns 0
        //if not valid password, returns 1
        //if all ok, returns 2
        int res = 0;
        if(nickname == null || password == null) return 0;
        List<Member> members = Club.getInstance().getMembers();
        for (Member member : members) {
            if(member.getNickName() == nickname){
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
