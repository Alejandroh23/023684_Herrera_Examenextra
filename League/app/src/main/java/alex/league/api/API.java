package alex.league.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import alex.league.Leagues;
import alex.league.R;
import alex.league.database.Db;
import alex.league.utils.ApplicationContanst;
import alex.league.utils.JSONfunctions;

public class API
{

    Db usdbh;

    SQLiteDatabase db;
    Context ctx;
    Leagues objleagues;
    ProgressDialog pDialog;

    JSONObject jsonobject;
    JSONArray jsonarray,teams,players;
    String league,id_team;
    public API(Context ctx)
    {
       this.ctx = ctx;
        usdbh = new Db(ctx, "LEAGUES", null, 1);

        db = usdbh.getWritableDatabase();
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







            try {
                jsonarray = JSONfunctions.getJSONfromURL(ApplicationContanst.URL_API +"leagues");
                System.out.println("valor "+jsonobject);

                if (jsonarray != null)
                {





                    for (int i = 0; i < jsonarray.length(); i++)
                    {
                        jsonobject = jsonarray.getJSONObject(i);

                        //insertamos en la base de datos sqlite
                        league = jsonobject.getString("league");
                        if(db != null) {
                            try
                            {


                                db.execSQL("insert into leagues(id_league,league,owner) values('"+
                                        jsonobject.getString("id")+"','"+league+"','"+jsonobject.getString("owner")+"') ");
                            }catch (SQLException e)
                            {
                              System.out.println("error no encontro");
                            }

                        }



                        league = jsonobject.getString("id");

                        //guardamos los teams
                        teams = jsonobject.getJSONArray("teams");

                        for (int j = 0; j < teams.length(); j++) {
                            jsonobject = teams.getJSONObject(j);

                            if(db != null) {
                                try
                                {
                                    System.out.println("team "+jsonobject.getString("team"));
                                    System.out.println("owner "+jsonobject.getString("owner"));
                                    System.out.println("rank "+jsonobject.getString("rank"));
                                    System.out.println("id "+jsonobject.getString("id"));
                                    id_team = jsonobject.getString("id");
                                    db.execSQL("insert into teams(team,owner,rank,id,id_league) values('"+jsonobject.getString("team")+"','"+jsonobject.getString("owner")+"',"+jsonobject.getString("rank")+",'"+jsonobject.getString("id")+"','"+league+"') ");


                                    System.out.println("jsonobject "+jsonobject);


                                    if(jsonobject.has("players"))
                                    {
                                        System.out.println("si existe");
                                        players = jsonobject.getJSONArray("players");

                                        if(players != null)
                                        {
                                            for (int h = 0; h < players.length(); h++) {
                                                jsonobject = players.getJSONObject(h);

                                                System.out.println("name "+jsonobject.getString("name"));
                                                System.out.println("position "+jsonobject.getString("position"));
                                                System.out.println("team "+jsonobject.getString("team"));
                                                System.out.println("id_team "+id_team);
                                                try
                                                {
                                                    db.execSQL("insert into players(name,position,team,id_team) values('"+jsonobject.getString("name")+"','"+jsonobject.getString("position")+"','"+jsonobject.getString("team")+"','"+id_team+"')");
                                                }catch (SQLException e)
                                                {
                                                    System.out.print("fallo al insertar "+e.getMessage());
                                                }


                                            }
                                        }

                                    }
                                    else
                                    {
                                        System.out.println("NO existe");
                                    }


                                }catch (SQLException e)
                                {

                                }

                            }

                        }


                    }





                }
            }catch(JSONException e){
                e.printStackTrace();
            }








            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(jsonobject != null)
            {

                    objleagues.get_leagues();

            }
            else {
                Toast.makeText(ctx,"Error en la API",Toast.LENGTH_LONG).show();
            }



        }
    }



}
