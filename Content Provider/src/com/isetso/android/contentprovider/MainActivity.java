package com.isetso.android.contentprovider;

import com.isetso.android.contentprovider.R;
import com.isetso.contentProvider.EtudiantProvider;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity
{

	private Button btnSupprimer ;
	private Button btnAjouter;
	private Button btnSuppChamps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnSupprimer = (Button) findViewById(R.id.getbtnSupp);
		btnSupprimer.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				String URL = "content://com.isetso.provider.StudentProv/student";
			    Uri student = Uri.parse(URL);
				int count = getContentResolver().delete(student, null, null);
				String countNum = "Etudiant: "+ count +" Suppression: SUCCESS.";
				Toast.makeText(getBaseContext(),countNum, Toast.LENGTH_LONG).show();
			}
		});

		btnAjouter = (Button) findViewById(R.id.getbtnAjouter);
		btnAjouter.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ContentValues values = new ContentValues();

				values.put(EtudiantProvider.NOM,((EditText)findViewById(R.id.getFirstName)).getText().toString());
				values.put(EtudiantProvider.PRENOM, ((EditText)findViewById(R.id.getLastName)).getText().toString());
				values.put(EtudiantProvider.CLASS, ((EditText)findViewById(R.id.getClass)).getText().toString());

				Uri uri = getContentResolver().insert(EtudiantProvider.CONTENT_URI, values);

				Toast.makeText(getBaseContext(),"Etudiant: " + uri.toString() + " INSERTION ", Toast.LENGTH_LONG).show();
			}
		});

		

		btnSuppChamps = (Button) findViewById(R.id.getbtnSuppChamps);
		btnSuppChamps.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				((EditText) findViewById(R.id.getFirstName)).setText("");
				((EditText) findViewById(R.id.getLastName)).setText("");
				((EditText) findViewById(R.id.getClass)).setText("");
				Toast.makeText(getBaseContext(),"les champs de formulaire est vide", Toast.LENGTH_LONG).show();
			}
		});
	}
}
