package rs.fon.miltek.utility;

import android.util.Patterns;

public class DataValidation {

    public static Boolean isValidPassword(String password){
        Boolean valid = false;
        if(password.trim().length() >= 6){
            valid = true;
        }

        return valid;
    }

    public static Boolean isValidRetypePassword(String password, String retypePassword){
        Boolean valid = false;
        if(password.trim().equals(retypePassword.trim())){
            valid = true;
        }

        return valid;
    }

    public static Boolean isValidEmail(String email){
        Boolean valid = false;
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid = true;
        }
        return valid;
    }
}
