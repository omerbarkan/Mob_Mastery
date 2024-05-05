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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class EquipmentAdapter extends ArrayAdapter<Equipment> {

    Context context;
    List<Equipment> equipmentList;

    TextView tvGold;

    public static HashMap<String, Bitmap> equipmentImages = new HashMap<>();


    public EquipmentAdapter(Context context,int resource, int textViewResourceId, ArrayList<Equipment> equipmentList, TextView tvGold) {
        super(context, resource, textViewResourceId, equipmentList);
        this.context = context;
        this.equipmentList = equipmentList;
        this.tvGold = tvGold;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.equipment,parent,false);

        ImageView ivEquipment = (ImageView) view.findViewById(R.id.ivEquipment);
        TextView tvLvl = (TextView) view.findViewById(R.id.tvLvl);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        Button btUpgrade = (Button) view.findViewById(R.id.btUpgrade);


        Equipment temp = equipmentList.get(position);

        ivEquipment.setImageBitmap(equipmentImages.get(temp.getName()));
        tvLvl.setText("Lvl: " + temp.getLevel());
        tvDescription.setText("Strength: " + temp.getPerLevelStrength() * temp.getLevel());
        double itemLevel = (double)temp.getLevel();
        btUpgrade.setText("+1 LVL\nCost: " + (5 + (int)Math.pow(itemLevel*5, 1.25*Math.sqrt(itemLevel/15))));

        btUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = DataManager.getPlayers().get(DataManager.currentPlayer);
                double upgradeCost = 5 + Math.pow(itemLevel*5, 1.25*Math.sqrt(itemLevel/15));
                int upgradeCostInt = (int) upgradeCost;
                if(player.getGold() >= upgradeCostInt)
                {
                    player.setGold(player.getGold() - upgradeCostInt);
                    temp.setLevel(temp.getLevel()+1);
                    tvGold.setText("Gold : " + player.getGold());
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }

}
