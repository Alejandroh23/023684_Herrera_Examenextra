package alex.league.database;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper
{




    String create_leagues = "CREATE TABLE leagues (id_register integer primary key autoincrement,id_league TEXT UNIQUE,league text, owner text);";

    String create_teams = "CREATE TABLE teams (id_register integer primary key autoincrement,team text UNIQUE, owner text,rank integer,id text,id_league text);";

    String create_players = "CREATE TABLE players (id_player integer primary key autoincrement,name text , position text,team TEXT,id_team text);";




    public Db(Context contexto, String nombre,
              CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de la tabla

        db.execSQL(create_leagues);

        db.execSQL(create_teams);
        db.execSQL(create_players);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {


        db.execSQL("DROP TABLE IF EXISTS leagues");
        db.execSQL("DROP TABLE IF EXISTS teams");
        db.execSQL("DROP TABLE IF EXISTS players");



        db.execSQL(create_leagues);
        db.execSQL(create_teams);
        db.execSQL(create_players);


    }
}


