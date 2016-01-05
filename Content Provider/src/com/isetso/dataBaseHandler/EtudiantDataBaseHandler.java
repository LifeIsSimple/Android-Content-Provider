package com.isetso.dataBaseHandler;

import com.isetso.contentProvider.EtudiantProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EtudiantDataBaseHandler extends SQLiteOpenHelper {

	public EtudiantDataBaseHandler(Context context) {
		super(context, EtudiantProvider.DATABASE_NAME, null, EtudiantProvider.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 db.execSQL(EtudiantProvider.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS " +  EtudiantProvider.TABLE_NAME);
        onCreate(db);
	}
}
