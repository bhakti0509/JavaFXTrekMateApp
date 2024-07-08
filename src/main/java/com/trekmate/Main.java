package com.trekmate;

import javafx.application.Application;
import com.trekmate.view.auth.SignInPage;
import com.trekmate.view.dashboards.AdminPage;

public class Main {
    public static void main(String[] args){
        Application.launch(AdminPage.class,args);
    }
  
}