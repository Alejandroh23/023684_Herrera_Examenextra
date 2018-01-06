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
import alex.league.Teams;
import alex.league.database.Querys;

public class AdapterTeams extends BaseAdapter
{
    Querys query;
    Context ctx;
    TextView team,owner,rank,id;
    RatingBar feedback;
    ImageView edit,delete;

    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;


    HashMap<String, String> resultp = new HashMap<String, String>();


    public AdapterTeams(Context ctx,ArrayList<HashMap<String, String>> arraylist)
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

        View itemView = inflater.inflate(R.layout.item_team, parent, false);


        // Get the position
        resultp = data.get(position);

        team = (TextView) itemView.findViewById(R.id.team);
        owner = (TextView) itemView.findViewById(R.id.owner);
        rank = (TextView) itemView.findViewById(R.id.rank);
        id = (TextView) itemView.findViewById(R.id.id);
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


                if(query.delete_team(resultp.get("id")))
                {
                    ((Teams)ctx).load_teams();
                }


            }
        });



        team.setText("Team: "+resultp.get("team"));
        owner.setText("Owner: "+resultp.get("owner"));
        rank.setText("Rank: "+resultp.get("rank"));
        id.setText("ID: "+resultp.get("id"));







        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position
                resultp = data.get(position);

                Intent act = new Intent(ctx, Players.class);
                act.putExtra("id_team",resultp.get("id"));
                ctx.startActivity(act);





            }
        });
        return itemView;
    }


}
