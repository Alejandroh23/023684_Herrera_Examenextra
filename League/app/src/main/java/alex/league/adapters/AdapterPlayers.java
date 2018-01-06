package alex.league.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import alex.league.Players;
import alex.league.R;
import alex.league.database.Querys;

public class AdapterPlayers extends BaseAdapter
{
    Querys query;
    Context ctx;
    TextView name,txtposition,team;

    ImageView edit,delete;

    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;


    HashMap<String, String> resultp = new HashMap<String, String>();


    public AdapterPlayers(Context ctx,ArrayList<HashMap<String, String>> arraylist)
    {
        this.ctx = ctx;
        data = arraylist;
         query = new Querys(ctx);


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_player, parent, false);


        // Get the position
        resultp = data.get(position);

        name = (TextView) itemView.findViewById(R.id.name);
        txtposition = (TextView) itemView.findViewById(R.id.position);
        team = (TextView) itemView.findViewById(R.id.team);

        edit = (ImageView) itemView.findViewById(R.id.edit);
        delete = (ImageView) itemView.findViewById(R.id.delete);



        edit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position
                resultp = data.get(position);





            }
        });




        delete.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position
                resultp = data.get(position);


                if(query.delete_player(resultp.get("id_player")))
                {
                    ((Players)ctx).load_players();
                }


            }
        });



        name.setText("Name: "+resultp.get("name"));
        txtposition.setText("Position: "+resultp.get("position"));
        team.setText("Team: "+resultp.get("team"));








        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position
                resultp = data.get(position);




            }
        });
        return itemView;
    }


}

