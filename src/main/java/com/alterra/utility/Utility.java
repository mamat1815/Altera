package com.alterra.utility;

public class Utility {
    public static boolean isValidAcc(String email, String password) {
        boolean status  = false ;
        boolean at = false ;
        boolean dot = false;

        
        if (email.isEmpty()) {
            status = false;
            System.out.println(status);
            //AlertUtil.showAlert("Email Kosong", "Email Kosong Mohon Isi Dengan Benar");
        } else  {
            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    at = true;
                }

                if (email.charAt(i) == '.') {
                    dot = true;
                }
            }

            if (dot == true && at == true) {
                status = true;
            } else if (dot == false) {
                System.out.println(status);
                // AlertUtil.showAlert("Email Tidak Valid", "Periksa Email Anda Kemungkinan Kekurangan '@' ");
            } else if (at == false) {
                // AlertUtil.showAlert("Email Tidak Valid", "Periksa Email Anda Kemungkinan Kekurangan '.'");
                
            }
        }
        System.out.println(status);
        return status;
    }

}
