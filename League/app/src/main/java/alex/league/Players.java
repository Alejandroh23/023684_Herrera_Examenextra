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
import alex.league.adapters.AdapterPlayers;
import alex.league.adapters.AdapterTeams;
import alex.league.api.API;
import alex.league.database.Querys;

import static alex.league.utils.MGUtilities.hasConnection;
public class Players extends AppCompatActivity {

    public ArrayList<HashMap<String, String>> players;
    ListView list_players;
    Querys querys;
    Handler handler;
    EditText edtname,edtposition,edtteam;
    Button agregar,cancelar;
    AdapterPlayers adapter;
    ImageView add;
    Bundle extras;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);






        init();

        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        load_players();

        add.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position


                LayoutInflater layoutInflater = LayoutInflater.from(Players.this);
                View promptView = layoutInflater.inflate(R.layout.create_player, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Players.this);
                alertDialogBuilder.setView(promptView);



                agregar = (Button) promptView.findViewById(R.id.agregar);
                cancelar = (Button) promptView.findViewById(R.id.cancelar);
                edtname = (EditText) promptView.findViewById(R.id.edtname);
                edtposition = (EditText) promptView.findViewById(R.id.edtposition);
                edtteam = (EditText) promptView.findViewById(R.id.edtteam);





                // create an alert dialog
                final AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(true);



                agregar.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View arg0)
                    {
                        if(querys.insert_player(edtname.getText().toString(), edtposition.getText().toString(), edtteam.getText().toString(),extras.getString("id_team")))
                        {
                            Toast.makeText(Players.this,"Jugador creado",Toast.LENGTH_LONG).show();
                            load_players();
                        }
                        else
                        {
                            Toast.makeText(Players.this,"Error al agregar",Toast.LENGTH_LONG).show();
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

    public void load_players()
    {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() { // This thread runs in the UI
                    @Override
                    public void run() {
                        System.out.println("id_team_recibido "+extras.getString("id_team"));
                        querys. new get_players(extras.getString("id_team")).execute();
                    }
                });
            }
        };
        new Thread(runnable).start();


    }

    public void init()
    {
        list_players = (ListView) findViewById(R.id.list_players);
        add = (ImageView) findViewById(R.id.add);
        handler = new Handler();
        querys = new Querys(Players.this);
        extras = getIntent().getExtras();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public void populate_players()
    {
        adapter = new AdapterPlayers(Players.this,players);
        list_players.setAdapter(adapter);
    }
}
