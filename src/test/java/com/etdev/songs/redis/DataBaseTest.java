package com.etdev.songs.redis;


import org.junit.jupiter.api.Test;

class DataBaseTest {
    private DataBase db = new DataBase();

    @Test
    void smokeTest() {
        db.actionJedis(); // prints stuff
    }
}