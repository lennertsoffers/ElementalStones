package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;

public class SaveDefaultConfigCommand extends OperatorCommand {
    public SaveDefaultConfigCommand(String command) {
        super(command);
    }

    @Override
    boolean execute() {
        StaticVariables.plugin.saveDefaultConfig();
        return true;
    }
}
