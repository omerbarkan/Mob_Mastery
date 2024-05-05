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

public class SkillsAdapter extends ArrayAdapter<Skill> {

    Context context;

    List<Skill> skillsList;

    OnEquipButtonClickListener listener;

    public static HashMap<String, Bitmap> skillsImages = new HashMap<>();

    public SkillsAdapter(Context context, int resource, int textViewResourceId, ArrayList<Skill> skillsList) {
        super(context, resource, textViewResourceId, skillsList);
        this.context = context;
        this.skillsList = skillsList;
    }

    public interface OnEquipButtonClickListener {
        void onEquipButtonClick(Skill skill);
    }

    public void setOnEquipButtonClickListener(OnEquipButtonClickListener listener) {
        this.listener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.skill,parent,false);

        ImageView ivSkill = view.findViewById(R.id.ivSkill);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        Button btEquip = view.findViewById(R.id.btEquip);

        Skill temp = skillsList.get(position);

        ivSkill.setImageBitmap(skillsImages.get(temp.getName()));
        tvName.setText(temp.getName());
        tvDescription.setText(temp.getDescription());
        if(temp.isUnlocked())
        {
            btEquip.setText("Equip");
            btEquip.setEnabled(!temp.isEquipped());
            btEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEquipButtonClick(temp);

                    }
                }
            });
        }
        else { btEquip.setText("Locked"); btEquip.setEnabled(false);
    }

    return view;
}}
