package it.polimi.ingsw.Client.ClientData;

public class EnemiesData {

    private String enemyGod;
    private String enemyPowerGod;
    private String enemyNumber;
    private String enemyNickname;

    public EnemiesData(String enemyNumber, String enemyNickname) {
        this.enemyGod = "";
        this.enemyPowerGod = "";
        this.enemyNumber = enemyNumber;
        this.enemyNickname = enemyNickname;
    }

    //--------------------------------GETTER & SETTER------------------------------------
    public String getEnemyGod() {
        return enemyGod;
    }
    public void setEnemyGod(String enemyGod) {
        this.enemyGod = enemyGod;
    }
    public String getEnemyNumber() {
        return enemyNumber;
    }
    public String getEnemyNickname() {
        return enemyNickname;
    }
    public String getEnemyPowerGod() {
        return enemyPowerGod;
    }
    public void setEnemyPowerGod(String enemyPowerGod) {
        this.enemyPowerGod = enemyPowerGod;
    }
}
