package com.example.demo;


public class Skill {
    protected String name;
    protected boolean equipped;
    protected boolean unlocked;
    protected int cooldown;
    protected double cooldownLeft;

    protected int duration;

    protected double durationLeft;
    protected boolean enabled;
    protected String description;

    public Skill(String name, int cooldown, String description, int duration) {
        this.name = name;
        this.equipped = false;
        this.unlocked = false;
        this.cooldown = cooldown;
        this.cooldownLeft = 0;
        this.description = description;
        this.enabled = false;
        this.duration = duration;
        this.durationLeft = 0;

    }

    public Skill() {
    }

    public void use(GameManager gameManager, Player player)
    {
        switch (name)
        {
            case("Fireball"):
                int strength = player.getEquipment().get(0).getPerLevelStrength() * player.getEquipment().get(0).getLevel();
                gameManager.getMonster().setCurrentHealth(gameManager.getMonster().getCurrentHealth() - (strength*10));
                break;

            case("FastAttack"):
                gameManager.setAttackSpeed(gameManager.getAttackSpeed() / 5);
                break;
        }

    }

    public void stopUse(GameManager gameManager, Player player)
    {
        switch (name)
        {
            case("Fireball"):
                break;

            case("FastAttack"):
                gameManager.setAttackSpeed(gameManager.getAttackSpeed() * 5);

                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public double getCooldownLeft() {
        return cooldownLeft;
    }

    public void setCooldownLeft(double cooldownLeft) {
        this.cooldownLeft = cooldownLeft;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDurationLeft() {
        return durationLeft;
    }

    public void setDurationLeft(double durationLeft) {
        this.durationLeft = durationLeft;
    }


}



