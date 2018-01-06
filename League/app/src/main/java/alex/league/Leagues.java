package alex.league;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import alex.league.api.API;
import alex.league.database.Querys;

import static alex.league.utils.MGUtilities.hasConnection;
public class Leagues extends AppCompatActivity {

    public ArrayList<HashMap<String, String>> leagues;
    ListView list_leagues;
    API api;
    Button agregar,cancelar;
    EditText edtid,edtleague,edtowner;
    Handler handler;
    Querys query;
    AdapterLeagues adapter;
    ImageView add;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leagues);

        init();

        load_leagues();


        add.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position


                LayoutInflater layoutInflater = LayoutInflater.from(Leagues.this);
                View promptView = layoutInflater.inflate(R.layout.create_league, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Leagues.this);
                alertDialogBuilder.setView(promptView);



                agregar = (Button) promptView.findViewById(R.id.agregar);
                cancelar = (Button) promptView.findViewById(R.id.cancelar);
                edtid = (EditText) promptView.findViewById(R.id.edtid);
                edtleague = (EditText) promptView.findViewById(R.id.edtleague);
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
                        if(query.insert_league(edtid.getText().toString(),edtleague.getText().toString(),edtowner.getText().toString()))
                        {
                            Toast.makeText(Leagues.this,"Liga creada",Toast.LENGTH_LONG).show();
                            get_leagues();
                        }
                        else
                        {
                            Toast.makeText(Leagues.this,"Error al agregar",Toast.LENGTH_LONG).show();
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

    public void load_leagues()
    {

        if(hasConnection(Leagues.this))
        {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() { // This thread runs in the UI
                        @Override
                        public void run() {
                            api. new get_leagues().execute();
                        }
                    });
                }
            };
            new Thread(runnable).start();
        }
        else
        {
            Toast.makeText(Leagues.this,"No hay Internet, conectese antes a Internet",Toast.LENGTH_LONG).show();
        }

    }

    public void get_leagues()
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() { // This thread runs in the UI
                    @Override
                    public void run() {
                        query. new get_leagues().execute();
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    public void init()
    {
        list_leagues = (ListView) findViewById(R.id.list_leagues);
        add = (ImageView) findViewById(R.id.add);
        handler = new Handler();
        api = new API(Leagues.this);
        query = new Querys(Leagues.this);


    }

    public void populate_leagues()
    {
     adapter = new AdapterLeagues(Leagues.this,leagues);
     list_leagues.setAdapter(adapter);
    }
}
