package br.ufmg.engsoft2.gameloan;

import br.ufmg.engsoft2.gameloan.config.GlobalConfig;
import br.ufmg.engsoft2.gameloan.view.View;

public class Main {
    public static void main(String[] args) {
        GlobalConfig.getExternalProperties();
        View.showHome();
    }
}
