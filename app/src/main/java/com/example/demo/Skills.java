package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Skills extends AppCompatActivity implements View.OnClickListener, SkillsAdapter.OnEquipButtonClickListener {

    Player player = DataManager.getPlayers().get(DataManager.currentPlayer);
    ArrayList<Skill> skillsArrayList;

    TextView tvGems;
    Button btBuySkill;
    ImageView ivBack;
    ListView lvSkills;

    Dialog dSkillSlotSelect;

    SharedPreferences spSkillSlotSelect;

    Button btSlotOne, btSlotTwo, btSlotThree, btSlotFour;

    static Skill skillToEquip;

    SkillsAdapter skillsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        ivBack = findViewById(R.id.ivBack3);
        btBuySkill = findViewById(R.id.btBuySkill);
        tvGems = findViewById(R.id.tvGems1);

        SkillsAdapter.skillsImages = createMainSKHashmap();

        skillsArrayList = player.getSkills();
        skillsAdapter = new SkillsAdapter(this, 0, 0, skillsArrayList);
        skillsAdapter.setOnEquipButtonClickListener(this);

        lvSkills = findViewById(R.id.lvSkills);

        lvSkills.setAdapter(skillsAdapter);


        ivBack.setOnClickListener(this);
        btBuySkill.setOnClickListener(this);

        tvGems.setText("Gems: " + player.getGems());


    }

    public void onBackPressed()
    {

    }
    public HashMap<String, Bitmap> createMainSKHashmap()
    {
        HashMap<String, Bitmap> skillsImage = new HashMap<String, Bitmap>();
        skillsImage.put("Fireball", BitmapFactory.decodeResource(getResources(), R.drawable.fireball));
        skillsImage.put("FastAttack", BitmapFactory.decodeResource(getResources(), R.drawable.hourglass));

        return skillsImage;
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack)
        {
            Intent intent = new Intent(this, MainGame.class);
            startActivity(intent);
        }
        if(v == btBuySkill)
        {
            if(player.getGems() >= 50)
            {
                boolean allUnlocked = true;
                ArrayList<Integer> lockedSkills = new ArrayList<Integer>();
                for(int i = 0; i < player.getSkills().size(); i++)
                {
                    if(!player.getSkills().get(i).isUnlocked())
                    {
                        allUnlocked = false;
                        lockedSkills.add(i);
                    }
                }
                if(!allUnlocked)
                {
                    Random rnd = new Random();
                    int num = rnd.nextInt(lockedSkills.size());

                    player.getSkills().get(lockedSkills.get(num)).setUnlocked(true);

                    player.setGems(player.getGems()-50);
                    tvGems.setText("Gems: " + player.getGems());

                    skillsAdapter.notifyDataSetChanged();
                }
            }


        }

        if (v == btSlotOne || v == btSlotTwo || v == btSlotThree || v == btSlotFour) {
                int slotIndex = -1;
                if (v == btSlotOne) {
                    slotIndex = 0;
                } else if (v == btSlotTwo) {
                    slotIndex = 1;
                } else if (v == btSlotThree) {
                    slotIndex = 2;
                } else if (v == btSlotFour) {
                    slotIndex = 3;
                }
                if (slotIndex >= 0 && slotIndex < player.getEquippedSkills().size()) {
                    player.getEquippedSkills().get(slotIndex).setEquipped(false);
                    player.getEquippedSkills().set(slotIndex, skillToEquip);
                    skillToEquip.setEquipped(true);
                    skillsAdapter.notifyDataSetChanged();
                    dSkillSlotSelect.dismiss();
                }
        }

    }



    public void createSkillSlotSelectDialog()
    {
        dSkillSlotSelect = new Dialog(this);
        dSkillSlotSelect.setContentView(R.layout.skillslotselect);
        dSkillSlotSelect.setTitle("Select Skill Slot");
        dSkillSlotSelect.setCancelable(true);
        btSlotOne = dSkillSlotSelect.findViewById(R.id.btSlotOne);
        btSlotTwo = dSkillSlotSelect.findViewById(R.id.btSlotTwo);
        btSlotThree = dSkillSlotSelect.findViewById(R.id.btSlotThree);
        btSlotFour = dSkillSlotSelect.findViewById(R.id.btSlotFour);

        btSlotOne.setOnClickListener(this);
        btSlotTwo.setOnClickListener(this);
        btSlotThree.setOnClickListener(this);
        btSlotFour.setOnClickListener(this);

        dSkillSlotSelect.show();

    }

    @Override
    public void onEquipButtonClick(Skill skill) {
        skillToEquip = skill;
        createSkillSlotSelectDialog();
    }
}