package alex.league.database;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import alex.league.Leagues;
import alex.league.Players;
import alex.league.R;
import alex.league.Teams;

public class Querys
{
    Context ctx;
    Leagues objleagues;
    Teams objteam;
    Players objplayer;
    Db usdbh;

    SQLiteDatabase db;

    ProgressDialog pDialog;
public Querys(Context ctx)
{
    this.ctx = ctx;
    usdbh = new Db(ctx, "LEAGUES", null, 1);

    db = usdbh.getWritableDatabase();
}

public Boolean insert_league(String id,String league,String owner)
{
    boolean response = false;

    if(db != null)
    {
        try
        {
            db.execSQL("insert into leagues(id_league,league,owner) values('"+id+"','"+league+"','"+owner+"') ");
            response = true;
        }catch (SQLException e)
        {

        }
    }
    return response;

}


    public Boolean insert_team(String team,String owner,String rank,String id,String id_league)
    {
        boolean response = false;

        if(db != null)
        {
            try
            {
                db.execSQL("insert into teams(team,owner,rank,id,id_league) values('"+team+"','"+owner+"',"+rank+",'"+id+"','"+id_league+"') ");
                response = true;
            }catch (SQLException e)
            {

            }
        }
        return response;

    }

    public Boolean insert_player(String name,String position,String team,String id_team)
    {
        boolean response = false;

        if(db != null)
        {
            try
            {
                db.execSQL("insert into players(name,position,team,id_team) values('"+name+"','"+position+"','"+team+"','"+id_team+"') ");
                response = true;
            }catch (SQLException e)
            {

            }
        }
        return response;

    }


    public Boolean delete_league(String id)
    {
        boolean response = false;

        if(db != null)
        {
            try
            {
                db.execSQL("delete from leagues where id = '"+id+"'");
                response = true;
            }catch (SQLException e)
            {

            }
        }
        return response;

    }

    public Boolean delete_team(String id)
    {
        boolean response = false;

        if(db != null)
        {
            try
            {
                db.execSQL("delete from teams where id = '"+id+"'");
                response = true;
            }catch (SQLException e)
            {

            }
        }
        return response;

    }


    public Boolean delete_player(String id_player)
    {
        boolean response = false;

        if(db != null)
        {
            try
            {
                db.execSQL("delete from players where id_player = '"+id_player+"'");
                response = true;
            }catch (SQLException e)
            {

            }
        }
        return response;

    }



    public class get_teams extends AsyncTask<Void, Void, Void>
    {

        String id_league = "";
        public get_teams(String id_league)
        {
            this.id_league = id_league;
        }
        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);

            pDialog.setTitle(ctx.getResources().getString(R.string.app_name));
            pDialog.setMessage("Cargando Equipos");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setIcon(R.mipmap.ic_launcher);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            objteam = (Teams) ctx;
            objteam.teams = new ArrayList<HashMap<String, String>>();







            if(db != null)
            {


                Cursor c = db.rawQuery("select team,owner,rank,id from teams where id_league = '"+id_league+"'", null);



                if(c.getCount()>0)
                {


                    if (c.moveToFirst()) {
                        do {
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("team", c.getString(0));
                            map.put("owner", c.getString(1));
                            map.put("rank",c.getString(2));
                            map.put("id",c.getString(3));


                            objteam.teams.add(map);

                        } while(c.moveToNext());
                    }




                }

            }








            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            pDialog.dismiss();

            if(objteam.teams.size()>0)
            {

                objteam.populate_teams();
            }
            else
            {
                Toast.makeText(ctx,"No hay Equipos",Toast.LENGTH_LONG).show();
            }


        }
    }


    public class get_leagues extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);

            pDialog.setTitle(ctx.getResources().getString(R.string.app_name));
            pDialog.setMessage("Cargando Ligas");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setIcon(R.mipmap.ic_launcher);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            objleagues = (Leagues) ctx;
            objleagues.leagues = new ArrayList<HashMap<String, String>>();







            if(db != null)
            {


                Cursor c = db.rawQuery("select id_league,league,owner from leagues", null);



                if(c.getCount()>0)
                {


                    if (c.moveToFirst()) {
                        do {
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("id_league", c.getString(0));
                            map.put("league", c.getString(1));
                            map.put("owner",c.getString(2));



                            objleagues.leagues.add(map);

                        } while(c.moveToNext());
                    }




                }
                else
                {
                    System.out.println("ligas no hay ");
                }

            }








            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            pDialog.dismiss();

            if(objleagues.leagues.size()>0)
            {

                objleagues.populate_leagues();
            }
            else
            {
                Toast.makeText(ctx,"No hay Leagues",Toast.LENGTH_LONG).show();
            }


        }
    }


    public class get_players extends AsyncTask<Void, Void, Void>
    {

        String id_team = "";
        public get_players(String id_team)
        {
            this.id_team = id_team;
        }
        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);

            pDialog.setTitle(ctx.getResources().getString(R.string.app_name));
            pDialog.setMessage("Cargando Jugadores");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setIcon(R.mipmap.ic_launcher);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            objplayer = (Players) ctx;
            objplayer.players = new ArrayList<HashMap<String, String>>();







            if(db != null)
            {


                Cursor c = db.rawQuery("select id_player,name,position,team from players where id_team = '"+id_team+"'", null);



                if(c.getCount()>0)
                {


                    if (c.moveToFirst()) {
                        do {
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("id_player", Integer.toString(c.getInt(0)));
                            map.put("name", c.getString(1));
                            map.put("position",c.getString(2));
                            map.put("team",c.getString(3));


                            objplayer.players.add(map);

                        } while(c.moveToNext());
                    }




                }

                else
                {
                    System.out.println("no hay jugadores");
                }

            }








            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            pDialog.dismiss();

            if(objplayer.players.size()>0)
            {

                objplayer.populate_players();
            }
            else
            {
                Toast.makeText(ctx,"No hay Jugadores en este equipo",Toast.LENGTH_LONG).show();
            }


        }
    }


}
