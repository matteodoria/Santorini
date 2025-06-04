package it.polimi.ingsw.Client.UI.GUI;

import it.polimi.ingsw.Client.ClientData.GodPowersPhrases;

public interface CodeToGod {

    static String getNameFromIndex(int godnum) {
        switch (godnum) {
            case 1:
                return "APOLLO";
            case 2:
                return "ARTEMIS";
            case 3:
                return "ATHENA";
            case 4:
                return "ATLAS";
            case 5:
                return "DEMETER";
            case 6:
                return "PROMETHEUS";
            case 7:
                return "HEPHAESTUS";
            case 8:
                return "MINOTAUR";
            case 9:
                return "PAN";
            case 10:
                return "ARES";
            case 11:
                return "CHRONUS";
            case 12:
                return "MEDUSA";
            case 13:
                return "POSEIDON";
            case 14:
                return "ZEUS";

        }
        return "";
    }

    static String assignPower(int godnum){
        switch (godnum) {
            case 1:
                return GodPowersPhrases.apolloPhrase;
            case 2:
                return GodPowersPhrases.artemisPhrase;
            case 3:
                return GodPowersPhrases.athenaPhrase;
            case 4:
                return GodPowersPhrases.atlasPhrase;
            case 5:
                return GodPowersPhrases.demeterPhrase;
            case 6:
                return GodPowersPhrases.prometheusPhrase;
            case 7:
                return GodPowersPhrases.hephaestusPhrase;
            case 8:
                return GodPowersPhrases.minotaurPhrase;
            case 9:
                return GodPowersPhrases.panPhrase;
            case 10:
                return GodPowersPhrases.aresPhrase;
            case 11:
                return GodPowersPhrases.chronoPhrase;
            case 12:
                return GodPowersPhrases.medusaPhrase;
            case 13:
                return GodPowersPhrases.poseidonPhrase;
            case 14:
                return GodPowersPhrases.zeusPhrase;
        }
        return "";
    }

    static int getIndexFromName(String name) {
        switch (name) {
            case "APOLLO":
                return 1;
            case "ARTEMIS":
                return 2;
            case "ATHENA":
                return 3;
            case "ATLAS":
                return 4;
            case "DEMETER":
                return 5;
            case "PROMETHEUS":
                return 6;
            case "HEPHAESTUS":
                return 7;
            case "MINOTAUR":
                return 8;
            case "PAN":
                return 9;
            case "ARES":
                return 10;
            case "CHRONUS":
                return 11;
            case "MEDUSA":
                return 12;
            case "POSEIDON":
                return 13;
            case "ZEUS":
                return 14;

        }
        return 100;
    }
}
