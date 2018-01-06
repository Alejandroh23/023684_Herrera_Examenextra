package alex.league;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alex.league.adapters.AdapterLeagues;
import alex.league.adapters.AdapterTeams;
import alex.league.api.API;
import alex.league.database.Querys;

import static alex.league.utils.MGUtilities.hasConnection;
public class Teams extends AppCompatActivity {

    public ArrayList<HashMap<String, String>> teams;
    ListView list_teams;
    Querys querys;
    Handler handler;
    AdapterTeams adapter;
    ImageView add;
    Bundle extras;
    Toolbar toolbar;
    EditText edtteam,edtowner,edtrank,edtid;
    Button agregar,cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams);






        init();

        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        load_teams();

        add.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position


                LayoutInflater layoutInflater = LayoutInflater.from(Teams.this);
                View promptView = layoutInflater.inflate(R.layout.create_team, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Teams.this);
                alertDialogBuilder.setView(promptView);



                agregar = (Button) promptView.findViewById(R.id.agregar);
                cancelar = (Button) promptView.findViewById(R.id.cancelar);
                edtid = (EditText) promptView.findViewById(R.id.edtid);
                edtteam = (EditText) promptView.findViewById(R.id.edtteam);
                edtrank = (EditText) promptView.findViewById(R.id.edtrank);
                edtowner = (EditText) promptView.findViewById(R.id.edtowner);




                // create an alert dialog
                final AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(true);



                agregar.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View arg0)
                    {
                        if(querys.insert_team(edtteam.getText().toString(), edtowner.getText().toString(), edtrank.getText().toString(),edtid.getText().toString(),extras.getString("id_league")))
                        {
                            Toast.makeText(Teams.this,"Team creado",Toast.LENGTH_LONG).show();
                            load_teams();
                        }
                        else
                        {
                            Toast.makeText(Teams.this,"Error al agregar",Toast.LENGTH_LONG).show();
                        }

                        alert.cancel();

                    }});

                cancelar.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View arg0)
                    {

                        alert.cancel();
                    }});




            }
        });



    }

    public void load_teams()
    {


            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() { // This thread runs in the UI
                        @Override
                        public void run() {
                            System.out.println("id_league_recibido "+extras.getString("id_league"));
                            querys. new get_teams(extras.getString("id_league")).execute();
                        }
                    });
                }
            };
            new Thread(runnable).start();


    }

    public void init()
    {
        list_teams = (ListView) findViewById(R.id.list_teams);
        add = (ImageView) findViewById(R.id.add);
        handler = new Handler();
        querys = new Querys(Teams.this);
        extras = getIntent().getExtras();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public void populate_teams()
    {
        adapter = new AdapterTeams(Teams.this,teams);
        list_teams.setAdapter(adapter);
    }
}
