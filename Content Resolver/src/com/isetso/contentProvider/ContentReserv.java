package com.isetso.contentProvider;

import android.net.Uri;

public class ContentReserv {
	
	public static final String AUTHORITY = "com.isetso.provider.etudiantProv";
    public static final String PATH = "etudiant";
    public static final Uri ETUDENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(PATH).build();
    
 // champs de la base
 	 public static final String ID = "id";
 	 public static final String NOM = "nom";
 	 public static final String PRENOM = "prenom";
 	 public static final String CLASS = "class";
}
