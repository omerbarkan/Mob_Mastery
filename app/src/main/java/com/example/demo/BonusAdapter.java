package com.example.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BonusAdapter extends ArrayAdapter<Bonus> {
    Context context;
    List<Bonus> bonusList;

    public static HashMap<String, Bitmap> bonusImages = new HashMap<>();


    public BonusAdapter(Context context,int resource, int textViewResourceId, ArrayList<Bonus> bonusList) {
        super(context, resource, textViewResourceId, bonusList);
        this.context = context;
        this.bonusList = bonusList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.bonus,parent,false);

        ImageView ivBonus = (ImageView) view.findViewById(R.id.ivBonus);
        TextView tvLvl = (TextView) view.findViewById(R.id.tvLvlB);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescriptionB);
        Button btUpgrade = (Button) view.findViewById(R.id.btUpgradeB);


        Bonus temp = bonusList.get(position);

        ivBonus.setImageBitmap(bonusImages.get(temp.getName()));
        tvLvl.setText("Lvl: " + temp.getLevel());
        btUpgrade.setText("+1 LVL");

        btUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.setLevel(temp.getLevel()+1);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
