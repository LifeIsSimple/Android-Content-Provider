package com.isetso.android.contentResolver;


import com.isetso.contentProvider.ContentReserv;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TableLayout monLayout;
	private TableRow row;
	
	private TextView nom,prenom , classe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		monLayout = (TableLayout) findViewById(R.id.getLayout);//new TableLayout(this);
		
		
		row = new TableRow(this);
       
        
        

         prenom = new TextView(this);
        prenom.setText("Prenom");
        prenom.setTypeface(Typeface.SERIF, Typeface.BOLD);
        prenom.setGravity(Gravity.CENTER_HORIZONTAL);

         nom = new TextView(this);
        nom.setText("Nom");
        nom.setTypeface(Typeface.SERIF, Typeface.BOLD);
        nom.setGravity(Gravity.CENTER_HORIZONTAL);

         classe = new TextView(this);
        classe.setText("Class");
        classe.setTypeface(Typeface.SERIF, Typeface.BOLD);
        classe.setGravity(Gravity.CENTER_HORIZONTAL);

        
        row.addView(nom);
        row.addView(prenom);
        row.addView(classe);
        monLayout.addView(row, 0);
        Cursor c = getContentResolver().query(ContentReserv.ETUDENT_URI, null, null, null, "nom");
        if (!c.moveToFirst()) {
	    	  Toast.makeText(MainActivity.this, "aucune resultat existe!", Toast.LENGTH_SHORT).show();
	      }else{
	    	  int i = 1;
	    	  do{
				row = new TableRow(this);
	
		        prenom = new TextView(this);
		        prenom.setText( c.getString(c.getColumnIndex(ContentReserv.NOM)));
		        prenom.setGravity(Gravity.CENTER_HORIZONTAL);
	
		        nom = new TextView(this);
		        nom.setText(c.getString(c.getColumnIndex(ContentReserv.PRENOM)));
		        nom.setGravity(Gravity.CENTER_HORIZONTAL);
		        
		        
		        classe = new TextView(this);
		        classe.setText(c.getString(c.getColumnIndex(ContentReserv.CLASS)));
		        classe.setGravity(Gravity.CENTER_HORIZONTAL);
		        
		        
		        
		        row.addView(nom);
		        row.addView(prenom);
		        row.addView(classe);
		        monLayout.addView(row, i++);
	        } while (c.moveToNext());
	      }
	}
}
