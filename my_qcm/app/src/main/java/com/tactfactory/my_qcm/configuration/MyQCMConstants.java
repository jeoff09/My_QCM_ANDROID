package com.tactfactory.my_qcm.configuration;

import java.net.URL;

/**
 * Created by jeoffrey on 19/05/2016.
 */
public class MyQCMConstants {

    public final static String CONST_URL = "http://192.168.1.16";
    public final static String CONST_URL_LOGIN = CONST_URL+"/MY_QCM/web/app_dev.php/api/userauths";
    public final static String CONST_URL_GET_CATEGORIES =  CONST_URL+"/MY_QCM/web/app_dev.php/api/categoriesusers";
    public final static String CONST_URL_GET_MCQs =  CONST_URL+"/MY_QCM/web/app_dev.php/api/mcqsusers";
    public final static String CONST_URL_SEND_RESULT =  CONST_URL + "/MY_QCM/web/app_dev.php/api/results";
    public final static String CONST_VALUE_LOGIN = "login";
    public final static String CONST_USERNAME = "username";
    public final static String CONST_VALUE_PWD = "password";
    public final static String CONST_VALUE_ID_USER = "user_id";
    public final static String CONST_VALUE_ID_CATEG = "categ_id";
    public final static String CONST_VALUE_RESULT = "result";

    public final static String CONST_MESS_UPDATEDB = "Mise à jours de votre profil";
    public final static String CONST_MESS_UPDATEDBERROR = "Une erreur est survenue lors de la mise à jours de votre profil";
    public final static String CONST_MESS_DELETEDB = "Utilisateur supprimé.";
    public final static String CONST_MESS_CREATEDB = "Bienvenue dans votre espace MyQCM ";
    public final static String CONST_MESS_CREATEDBERROR = "Erreur dans la création de votre profil. Veuillez rééssayer.";

    public static final String APP_DB_NAME = "myqcm";
    public static final String APP_DB_EXTENSION = ".sqlite";

    public final static String CONST_PROFIL_USERNAME = "Identifiant : ";
    public final static String CONST_PROFIL_EMAIL = "Email : ";

    public static final int CONST_CONNECT_TIMEOUT = 60000;
    public static final int CONST_SET_TIMEOUT = 600000;
}
