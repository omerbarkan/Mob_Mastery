package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainGame extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Equipment> equipmentArrayList;
    ArrayList<Bonus> bonusArrayList;

    ListView lvMain;

    TextView tvGems, tvGold, tvAccount, tvLevel, tvHealth;

    ImageButton btEquipment, btBonus, btSkills, ibSave, ibRebirth, btSkillOne, btSkillTwo, btSkillThree, btSkillFour;


    SharedPreferences spSaveGame, spRebirth;

    Dialog dSaveGame, dRebirth;

    Button btSaveGame, btCloseSaveGame, btRebirth, btCloseRebirth;

    public static HashMap<String, Bitmap> skillsImagesM = new HashMap<>();

    EquipmentAdapter equipmentAdapter;
    BonusAdapter bonusAdapter;

    ProgressBar pbTime, pbHealth, pbSkillOne, pbSkillTwo, pbSkillThree, pbSkillFour;

    ArrayList<ProgressBar> progressBars = new ArrayList<>();

    ArrayList<ImageButton> skillsButtons = new ArrayList<>();

    ImageView ivMonster;

    //////////////////////

    Player player = DataManager.getPlayers().get(DataManager.currentPlayer);
    Monster currentMonster;
    CountDownTimer monsterCountDownTimer ; int pbTimeTotal = 0;


    GameManager gameManager = new GameManager(currentMonster, 500);
    public static GameManager savedGameManager;


    private Handler attackHandler = new Handler();

    private Runnable AttackRunnable = new Runnable() {
        @Override
        public void run() {
            // Perform attack action here
            performAttack();

            // Repeat this runnable with the specified interval

            attackHandler.postDelayed(this, (int)gameManager.getAttackSpeed());
        }
    };

    private Handler skillHandler = new Handler();

    private Runnable skillCountdownRunnable = new Runnable() {
        @Override
        public void run() {
            updateSkillCooldowns();
            updateSkillDurations();

            // Post the Runnable again after 500 milliseconds
            skillHandler.postDelayed(this, 500);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        cancelAlarm();

        //fixing equipped skills
        fixSkills();

        //findView
        btBonus = findViewById(R.id.btBonus);
        btEquipment = findViewById(R.id.btEquipment);
        btSkills = findViewById(R.id.btSkills);
        tvAccount = findViewById(R.id.tvAccount);
        tvHealth = findViewById(R.id.tvHealth);
        tvLevel = findViewById(R.id.tvLevel);
        tvGold = findViewById(R.id.tvGold);
        tvGems = findViewById(R.id.tvGems);
        pbTime = findViewById(R.id.pbTime);
        pbHealth = findViewById(R.id.pbHealth);
        ivMonster = findViewById(R.id.ivMonster);
        lvMain = findViewById(R.id.lvMain);
        btSkillOne = findViewById(R.id.btskillOne);
        btSkillTwo = findViewById(R.id.btskillTwo);
        btSkillThree = findViewById(R.id.btskillThree);
        btSkillFour = findViewById(R.id.btskillFour);
        ibSave = findViewById(R.id.ibSave);
        pbSkillOne = findViewById(R.id.pbSkillOne);
        pbSkillTwo = findViewById(R.id.pbSkillTwo);
        pbSkillThree = findViewById(R.id.pbSkillThree);
        pbSkillFour = findViewById(R.id.pbSkillFour);
        ibRebirth = findViewById(R.id.ibRebirth);

        spSaveGame = getSharedPreferences("details1", 0);
        spRebirth = getSharedPreferences("details1", 0);

        //setSkillImage and CD bars
        ArrayList<Skill> equippedSkills = player.getEquippedSkills();
        skillsImagesM = createMainSKHashmap();
        btSkillOne.setImageBitmap(skillsImagesM.get(player.getEquippedSkills().get(0).getName()));
        btSkillTwo.setImageBitmap(skillsImagesM.get(player.getEquippedSkills().get(1).getName()));
        btSkillThree.setImageBitmap(skillsImagesM.get(player.getEquippedSkills().get(2).getName()));
        btSkillFour.setImageBitmap(skillsImagesM.get(player.getEquippedSkills().get(3).getName()));

        progressBars.add(pbSkillOne); progressBars.add(pbSkillTwo); progressBars.add(pbSkillThree); progressBars.add(pbSkillFour);
        skillsButtons.add(btSkillOne); skillsButtons.add(btSkillTwo); skillsButtons.add(btSkillThree); skillsButtons.add(btSkillFour);
        for(int i = 0; i < equippedSkills.size(); i++)
        {
            if(!(equippedSkills.get(i).getName().equals("NONE")))
            {
                progressBars.get(i).setMax(equippedSkills.get(i).getCooldown() * 10);
            }
        }

        //onClick
        btBonus.setOnClickListener(this);
        btEquipment.setOnClickListener(this);
        btSkills.setOnClickListener(this);
        btSkillOne.setOnClickListener(this);
        btSkillTwo.setOnClickListener(this);
        btSkillThree.setOnClickListener(this);
        btSkillFour.setOnClickListener(this);
        ibSave.setOnClickListener(this);
        ibRebirth.setOnClickListener(this);


        EquipmentAdapter.equipmentImages = createMainEQHashmap();


        equipmentArrayList = player.getEquipment();
        equipmentAdapter = new EquipmentAdapter(this, 0, 0, equipmentArrayList, tvGold);


        BonusAdapter.bonusImages = createMainBNHashmap();

        bonusArrayList = player.getBonuses();
        bonusAdapter = new BonusAdapter(this, 0, 0 ,bonusArrayList);


        lvMain.setAdapter(equipmentAdapter);


        tvAccount.setText(player.getUsername());

        ///////////////////////////////////////////////////////////////////// --> Game
        if(savedGameManager != null)
        {
            gameManager = savedGameManager;
            recallSavedGame();
        }
        else
        {
            newMonster(null);
        }
        pbHealth.setMax(gameManager.getMonster().getMaxHealth());
        pbHealth.setProgress(gameManager.getMonster().getCurrentHealth());
        setCDTimers();

    }

    public void onBackPressed()
    {

    }

    @Override
    public void onClick(View v) {
        if(v == ibSave)
        {
            createSaveGameDialog();
        }
        if(v == btSaveGame)
        {
            stopTimers();
            ArrayList<Skill> equippedSkills = player.getEquippedSkills();
            for(int i = 0; i < equippedSkills.size(); i++)
            {
                equippedSkills.get(i).stopUse(gameManager, player);
                equippedSkills.get(i).setEnabled(false);
                equippedSkills.get(i).setDurationLeft(0);
                equippedSkills.get(i).setCooldownLeft(0);
            }
            DBManager.getDb().getReference("players").child(String.valueOf(DataManager.currentPlayer)).setValue(player);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (20*1*1*1000), pendingIntent);

            dSaveGame.dismiss();
            finishAffinity();
        }
        if(v == btCloseSaveGame)
        {
            dSaveGame.dismiss();
        }
        if (v == ibRebirth)
        {
            createRebirthDialog();
        }
        if(v == btRebirth)
        {
            player.setGems(player.getGems()+((player.getCurrentLevel()*2) - 2));
            player.currentLevel = 1;
            int num = player.getEquipment().size();
            for(int i = 0 ; i < num; i++)
            {
                player.getEquipment().get(i).setLevel(0);
            }
            player.getEquipment().get(0).setLevel(1);
            player.setGold(0);
            newMonster(null);
            pbTime.setProgress(150);
            pbTimeTotal = 150;
            monsterCountDownTimer.cancel();
            monsterCountDownTimer.start();
            equipmentAdapter.notifyDataSetChanged();
            dRebirth.dismiss();
        }
        if(v == btCloseRebirth)
        {
            dRebirth.dismiss();
        }
        if(v == btEquipment)
        {
            lvMain = findViewById(R.id.lvMain);

            lvMain.setAdapter(equipmentAdapter);
        }
        if(v == btBonus)
        {
            lvMain = findViewById(R.id.lvMain);

            lvMain.setAdapter(bonusAdapter);
        }

        if(v == btSkills)
        {
            stopTimers();
            monsterCountDownTimer.cancel();
            savedGameManager = gameManager;
            Intent intent = new Intent(this, Skills.class);
            startActivity(intent);
        }
        if (v == btSkillOne || v == btSkillTwo || v == btSkillThree || v == btSkillFour) {
            if(v == btSkillOne) { useSkill(0); }
            if(v == btSkillTwo) { useSkill(1); }
            if(v == btSkillThree) { useSkill(2); }
            if(v == btSkillFour) { useSkill(3); }
        }
    }

    private void useSkill(int index) {
        Skill skill = player.getEquippedSkills().get(index);
        if (!skill.getName().equals("NONE")) {
            if(!skill.isEnabled() && skill.getCooldownLeft() == 0)
            {
                skill.setCooldownLeft(skill.getCooldown());
                skill.setDurationLeft(skill.getDuration());
                skill.use(gameManager, player);
                skill.setEnabled(true);
                progressBars.get(index).setAlpha(1);
                skillsButtons.get(index).setEnabled(false);

                Toast.makeText(MainGame.this, "Skill used: " + skill.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void createRebirthDialog()
    {
        dRebirth = new Dialog(this);
        dRebirth.setContentView(R.layout.rebirth);
        dRebirth.setTitle("Rebirth");
        dRebirth.setCancelable(true);
        btRebirth = dRebirth.findViewById(R.id.btRebirth);
        btCloseRebirth = dRebirth.findViewById(R.id.btCloseRebirth);
        btRebirth.setOnClickListener(this);
        btCloseRebirth.setOnClickListener(this);
        btRebirth.setText("Rebirth now\nGems : " + ((player.getCurrentLevel()*2) - 2));
        dRebirth.show();
    }
    public void createSaveGameDialog()
    {
        dSaveGame = new Dialog(this);
        dSaveGame.setContentView(R.layout.save);
        dSaveGame.setTitle("Game save");
        dSaveGame.setCancelable(true);
        btSaveGame = dSaveGame.findViewById(R.id.btSaveGame);
        btCloseSaveGame = dSaveGame.findViewById(R.id.btCloseSaveGame);
        btSaveGame.setOnClickListener(this);
        btCloseSaveGame.setOnClickListener(this);

        dSaveGame.show();
    }


    public HashMap<String, Bitmap> createMainEQHashmap()
    {
        HashMap<String, Bitmap> equipmentImage = new HashMap<>();
        equipmentImage.put("Sword", BitmapFactory.decodeResource(getResources(), R.drawable.sword));


        return equipmentImage;

    }

    public HashMap<String, Bitmap> createMainBNHashmap()
    {
        HashMap<String, Bitmap> bonusImage = new HashMap<>();
        bonusImage.put("Gold Boost", BitmapFactory.decodeResource(getResources(), R.drawable.goldcoin));


        return bonusImage;
    }

    public HashMap<String, Bitmap> createMainSKHashmap()
    {
        HashMap<String, Bitmap> skillsImage = new HashMap<>();
        skillsImage.put("Fireball", BitmapFactory.decodeResource(getResources(), R.drawable.fireballmini));
        skillsImage.put("FastAttack", BitmapFactory.decodeResource(getResources(), R.drawable.hourglassmini));


        return skillsImage;
    }



    public void setCDTimers()
    {
        pbTimeTotal = pbTime.getMax();
        monsterCountDownTimer=new CountDownTimer(30000,200) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Log.v("Log_tag", "Tick of Progress"+ TimePBCurrent+ millisUntilFinished);
                pbTimeTotal--;
                pbTime.setProgress(pbTimeTotal*150/(30000/200));

            }

            @Override
            public void onFinish() {
                //Do what you want
                loseMonster();
            }
        };


        startAttacks();
        monsterCountDownTimer.start();
        startSkillCountdown();
    }


    public void loseMonster()
    {
        gameManager.getMonster().setCurrentHealth(gameManager.getMonster().getMaxHealth());
        pbTime.setProgress(150);
        pbTimeTotal = 150;
        monsterCountDownTimer.cancel();
        monsterCountDownTimer.start();
    }
    public void killMonster()
    {
        double newGold = player.getGold()+(Math.pow(player.currentLevel*5, 1.25));
        player.setGold((int)newGold);
        player.setCurrentLevel(player.getCurrentLevel()+1);
        if(player.getCurrentLevel()>player.getMaxLevel())
        {
            player.setMaxLevel(player.getCurrentLevel());
        }
        newMonster(gameManager.getMonster());
        pbTime.setProgress(150);
        pbTimeTotal = 150;
        monsterCountDownTimer.cancel();
        monsterCountDownTimer.start();
    }
    public void newMonster(Monster lastMonster)
    {
        tvGold.setText("Gold : " + player.getGold());
        tvGems.setText("Gems :" + player.getGems());
        tvLevel.setText("level: " + player.getCurrentLevel());
        if(lastMonster == null)
        {
            String[] monsterTypes = {
                    "minotaur", "vampire", "werewolf"
            };
            Random rand = new Random(); int monsterType = rand.nextInt(3);
            gameManager.setMonster(new Monster(monsterTypes[monsterType], Monster.setMonsterHealth(player.getCurrentLevel()), Monster.setMonsterHealth(player.getCurrentLevel())));
        }
        else
        {
            if(player.getCurrentLevel() % 5 == 1)
            {
                String[] monsterTypes = {
                        "minotaur", "vampire", "werewolf"
                };
                Random rand = new Random(); int monsterType = rand.nextInt(3);
                gameManager.setMonster(new Monster(monsterTypes[monsterType], Monster.setMonsterHealth(player.getCurrentLevel()), Monster.setMonsterHealth(player.getCurrentLevel())));
            }
            else
            {
                gameManager.setMonster(new Monster(lastMonster.getType(), Monster.setMonsterHealth(player.getCurrentLevel()), Monster.setMonsterHealth(player.getCurrentLevel())));
            }
        }

        String monsterType = gameManager.getMonster().getType();
        int resourceId = getResources().getIdentifier(monsterType, "drawable", getPackageName());
        if (resourceId != 0) {
            ivMonster.setImageResource(resourceId);
        pbHealth.setMax(gameManager.getMonster().getMaxHealth());
        pbHealth.setProgress(pbHealth.getMax());
        tvHealth.setText(gameManager.getMonster().getCurrentHealth() + "/" + gameManager.getMonster().getMaxHealth());

        }
    }

    public void recallSavedGame()
    {
        tvGold.setText("Gold : " + player.getGold());
        tvGems.setText("Gems : " + player.getGems());
        tvLevel.setText("level: " + player.getCurrentLevel());
        String monsterType = gameManager.getMonster().getType();
        int resourceId = getResources().getIdentifier(monsterType, "drawable", getPackageName());
        if (resourceId != 0) {
            ivMonster.setImageResource(resourceId);
            pbHealth.setMax(gameManager.getMonster().getMaxHealth());
            pbHealth.setProgress(pbHealth.getMax());
            tvHealth.setText(gameManager.getMonster().getCurrentHealth() + "/" + gameManager.getMonster().getMaxHealth());
        }

        gameManager.getMonster().setCurrentHealth(gameManager.getMonster().getMaxHealth());
        tvHealth.setText(gameManager.getMonster().getCurrentHealth() + "/" + gameManager.getMonster().getMaxHealth());

        ArrayList<Skill> equippedSkills = player.getEquippedSkills();
        for(int i = 0; i < equippedSkills.size(); i++)
        {
            if(equippedSkills.get(i).isEnabled())
            {
                equippedSkills.get(i).stopUse(gameManager, player);
            }
            if(equippedSkills.get(i).getCooldownLeft() > 0)
            {
                progressBars.get(i).setAlpha(1);
                skillsButtons.get(i).setEnabled(false);
            }
            equippedSkills.get(i).setEnabled(false);
            equippedSkills.get(i).setDurationLeft(0);
        }

    }

    public void fixSkills()
    {
        ArrayList<Skill> equippedSkills = player.getEquippedSkills();
        ArrayList<Skill> skills = player.getSkills();

        for(int i = 0; i < skills.size(); i++)
        {
            if(skills.get(i).isEquipped())
            {
                for(int j = 0; j < equippedSkills.size(); j++)
                {
                    if(skills.get(i).getName().equals(equippedSkills.get(j).getName()))
                    {
                        equippedSkills.set(j, skills.get(i));
                    }
                }
            }
        }

    }

    private void startSkillCountdown() {
        skillHandler.postDelayed(skillCountdownRunnable, 500); // Delayed by 500 milliseconds
    }

    private void startAttacks() {
        attackHandler.postDelayed(AttackRunnable, (int)gameManager.getAttackSpeed());
    }
    private void performAttack() {
        // Simulate an attack happening
        // For example, show a toast message
        gameManager.getMonster().setCurrentHealth(gameManager.getMonster().getCurrentHealth() - player.getEquipment().get(0).getPerLevelStrength() * player.getEquipment().get(0).getLevel());
        pbHealth.setProgress(Math.max(gameManager.getMonster().getCurrentHealth(), 0));
        tvHealth.setText(gameManager.getMonster().getCurrentHealth() + "/" + gameManager.getMonster().getMaxHealth());
        if(gameManager.getMonster().getCurrentHealth() <= 0)
        {
            killMonster();
        }

        // You can perform any other action related to the attack here
    }

    private void updateSkillCooldowns() {
        ArrayList<Skill> equippedSkills = player.getEquippedSkills();
        for (int i = 0; i < equippedSkills.size(); i++) {
            if (equippedSkills.get(i).getCooldownLeft() > 0) {
                equippedSkills.get(i).setCooldownLeft(equippedSkills.get(i).getCooldownLeft() - 0.5);
                if (equippedSkills.get(i).getCooldownLeft() > 0) {
                    int x = (int) Math.ceil(equippedSkills.get(i).getCooldownLeft() * 10);
                    progressBars.get(i).setProgress(x);
                    if (x == 1) {
                        // Handle cooldown finished
                    }
                } else {
                    progressBars.get(i).setAlpha(0);
                    progressBars.get(i).setProgress(equippedSkills.get(i).getCooldown() * 10);
                    skillsButtons.get(i).setEnabled(true);
                }
            }
        }
    }

    private void updateSkillDurations() {
        ArrayList<Skill> equippedSkills = player.getEquippedSkills();
        for (int i = 0; i < equippedSkills.size(); i++) {
            if (equippedSkills.get(i).getDuration() == 0) {
                equippedSkills.get(i).setEnabled(false);
            }

            if (equippedSkills.get(i).isEnabled() && equippedSkills.get(i).getDurationLeft() != 0) {
                equippedSkills.get(i).setDurationLeft(equippedSkills.get(i).getDurationLeft() - 0.5);
                if (equippedSkills.get(i).getDurationLeft() == 0) {
                    equippedSkills.get(i).setEnabled(false);
                    equippedSkills.get(i).stopUse(gameManager, player);
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending callbacks to prevent memory leaks
        attackHandler.removeCallbacks(AttackRunnable);
        skillHandler.removeCallbacks(skillCountdownRunnable);
    }


    public void stopTimers()
    {
        monsterCountDownTimer.cancel();
        attackHandler.removeCallbacks(AttackRunnable);
        skillHandler.removeCallbacks(skillCountdownRunnable);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }


}