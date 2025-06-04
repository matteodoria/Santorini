package it.polimi.ingsw.Client.ClientData;

public class YourData {

    private String yourGod;
    private String yourGodPower;
    private String yourNumber;
    private String yourNickname;
    private boolean youWon;
    private boolean youLose;

    /**
     * standard constructor
     */
    public YourData() {
        yourGod="";
        yourGodPower="";
        yourNumber="";
        youWon = false;
        youLose = false;
    }

    /**
     * standard getter for god
     * @return your god
     */
    public String getYourGod() {
        return yourGod;
    }

    /**
     * standard setter for your god
     * @param yourGod your God for the game
     */
    public void setYourGod(String yourGod) {
        this.yourGod = yourGod;
    }

    public String getYourGodPower() {
        return yourGodPower;
    }

    public void setYourGodPower(String yourGodPower) {
        this.yourGodPower = yourGodPower;
    }

    /**
     * standard getter for player number
     * @return your player number
     */
    public String getYourNumber() {
        return yourNumber;
    }

    /**
     * standard setter ro your player number
     * @param yourNumber your number for the game
     */
    public void setYourNumber(String yourNumber) {
        this.yourNumber = yourNumber;
    }


    /**
     * method that sets to true win or lose condition
     * @param condition string containing win or lose condition
     */
    public void winLoseSetter(String condition){
        if (condition.equals("0"))
            youLose=true;
        else
            youWon=true;
    }

    public boolean isYouWon() {
        return youWon;
    }

    public boolean isYouLose() {
        return youLose;
    }
}
