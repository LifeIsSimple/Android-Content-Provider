package com.isetso.contentProvider;

import java.util.HashMap;

import com.isetso.dataBaseHandler.EtudiantDataBaseHandler;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class EtudiantProvider extends ContentProvider
{
	 // champs de content provider
	 public static final String PROVIDER_NOM = "com.isetso.provider.etudiantProv";
	 public static final String URL = "content://" + PROVIDER_NOM + "/etudiant";
	 public static final Uri CONTENT_URI = Uri.parse(URL);
	   
	 // champs de la base
	 public static final String ID = "id";
	 public static final String NOM = "nom";
	 public static final String PRENOM = "prenom";
	 public static final String CLASS = "class";
	 
	 // les valeurs utiliser par URI
	 public static final int ETUDIANTS = 1;
	 public static final int ETUDIANTS_ID = 2;
	 
	 private EtudiantDataBaseHandler dbHelper;
	   
	 // Map pour la selection
	 private static HashMap<String, String> etudiantMap;
	 
	 // URI "patterns"
	 static final UriMatcher uriMatcher;
	   static{
	      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	      uriMatcher.addURI(PROVIDER_NOM, "etudiant", ETUDIANTS);
	      uriMatcher.addURI(PROVIDER_NOM, "etudiant/#", ETUDIANTS_ID);
	   }
	   
	   // Declaration de la base
	   private SQLiteDatabase database;
	   
	   public static final String DATABASE_NAME = "etudiantManager";
	   public static final String TABLE_NAME = "etudiantTable";
	   public static final int DATABASE_VERSION = 4;
	   public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
	      											" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	      											NOM +" TEXT NOT NULL, " +
	     		 									PRENOM + " TEXT NOT NULL, " +
	      											CLASS + " TEXT NOT NULL);";
	   
	@Override
	public boolean onCreate()
	{
		Context context = getContext();
		dbHelper = new EtudiantDataBaseHandler(context);
		database = dbHelper.getWritableDatabase();

	    if(database == null)
	    	return false;
	    else
	    	return true;	
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		// TABLE_NAME selectionne
		queryBuilder.setTables(TABLE_NAME);

		// mappe tous les NOMs de colonnes de base de donn�es
	    switch (uriMatcher.match(uri))
		{
			case ETUDIANTS:	queryBuilder.setProjectionMap(etudiantMap);
	         				break;
	      case ETUDIANTS_ID: queryBuilder.appendWhere( ID + "=" + uri.getLastPathSegment());
	         				break;
	      default:			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (sortOrder == null || sortOrder == "")
		{
			// trier sur les NOMs
			sortOrder = NOM;
		}

		Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
	    /**
	       * Inscrivez-vous pour regarder un contenu URI pour des changements
		 **/

		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		long row = database.insert(TABLE_NAME, "", values);
	      
		// Si enregistrement est ajout� avec succ�s
		if(row > 0)
		{
	         Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
	         getContext().getContentResolver().notifyChange(newUri, null);
	         return newUri;
		}

		throw new SQLException("Fail to add a new record into " + uri);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		int count = 0;

		switch (uriMatcher.match(uri))
		{
			case ETUDIANTS:		count = database.update(TABLE_NAME, values, selection, selectionArgs);
								break;
	      	case ETUDIANTS_ID:	count = database.update(TABLE_NAME, values, ID +
	                 			" = " + uri.getLastPathSegment() +
	                 			(!TextUtils.isEmpty(selection) ? " AND (" +
	                 			selection + ')' : ""), selectionArgs);
	         					break;
	      	default:			throw new IllegalArgumentException("Unsupported URI " + uri );
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		int count = 0;
		
		switch (uriMatcher.match(uri))
		{
			case ETUDIANTS:		// supprimer tous les enregistrements de la table
	    	  					count = database.delete(TABLE_NAME, selection, selectionArgs);
	    	  					break;
	      	case ETUDIANTS_ID:	String id = uri.getLastPathSegment();	//ID
	          					count = database.delete( TABLE_NAME, ID +  " = " + id +
	                			(!TextUtils.isEmpty(selection) ? " AND (" +
	                			selection + ')' : ""), selectionArgs);
								break;
			default:			throw new IllegalArgumentException("Unsupported URI " + uri);
		}
	      
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri)
	{
		switch (uriMatcher.match(uri))
		{
			case ETUDIANTS:		return "vnd.android.cursor.dir/vnd.example.ETUDIANTS";
	      	case ETUDIANTS_ID:	return "vnd.android.cursor.item/vnd.example.etudiantS";
	      	default:			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
}
