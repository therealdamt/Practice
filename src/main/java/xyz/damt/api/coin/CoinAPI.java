package xyz.damt.api.coin;

import xyz.damt.Practice;

import java.util.UUID;

public class CoinAPI {

    private final Practice practice;

    public CoinAPI(Practice practice) {
        this.practice = practice;
    }

    public int getCoins(UUID uuid) {
        return practice.getProfileHandler().getProfile(uuid).getCoins();
    }

    public void setCoins(UUID uuid, int coins) {
        practice.getProfileHandler().getProfile(uuid).setCoins(coins);
    }

}
