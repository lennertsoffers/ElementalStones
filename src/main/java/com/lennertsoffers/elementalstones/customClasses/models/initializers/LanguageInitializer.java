package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageInitializer extends Initializer {
    public LanguageInitializer(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        String language = ElementalStones.configuration.getString("language");

        if (language != null) {
            Locale locale = new Locale(language);

            ResourceBundle resourceBundle = ResourceBundle.getBundle("",locale);
        }
    }
}
