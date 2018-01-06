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

import alex.league.Leagues;
import alex.league.R;
import alex.league.Teams;
import alex.league.database.Querys;

public class AdapterLeagues extends BaseAdapter
{
    Querys query;
    Context ctx;
    TextView league,owner;
    RatingBar feedback;
    ImageView edit,delete;

    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;


    HashMap<String, String> resultp = new HashMap<String, String>();


    public AdapterLeagues(Context ctx,ArrayList<HashMap<String, String>> arraylist)
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

        View itemView = inflater.inflate(R.layout.item_league, parent, false);


        // Get the position
        resultp = data.get(position);

        league = (TextView) itemView.findViewById(R.id.league);
        owner = (TextView) itemView.findViewById(R.id.owner);
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


                if(query.delete_league(resultp.get("id")))
                {
                    ((Leagues)ctx).get_leagues();
                }


            }
        });


        league.setText("Team: "+resultp.get("league"));
        owner.setText("Owner: "+resultp.get("owner"));







        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // Get the position
                resultp = data.get(position);
                Intent act = new Intent(ctx,Teams.class);
                act.putExtra("id_league",resultp.get("id_league"));
                ctx.startActivity(act);




            }
        });
        return itemView;
    }


}
