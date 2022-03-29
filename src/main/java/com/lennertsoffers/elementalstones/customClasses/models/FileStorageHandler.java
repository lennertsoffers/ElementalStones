package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStorageHandler {

    private final Logger logger;

    public FileStorageHandler(Logger logger) {
        this.logger = logger;
    }

    public void initDeathPlayerStones() {
        try {
            File file = new File("./Plugins/ElementalStones/death_player_stones.txt");

            if (file.createNewFile()) {
                this.logger.log(Level.INFO, "Created new " + file.getName().toUpperCase() + " file.");
            } else {
                this.logger.log(Level.INFO, file.getName().toUpperCase() + " already exists, using this file.");
            }
        } catch (IOException e) {
            this.logger.log(Level.WARNING, "An error when reading the file.");
            e.printStackTrace();

            StaticVariables.plugin.getServer().getPluginManager().disablePlugin(StaticVariables.plugin);
        }
    }
}
