package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;

@CommandExecutor
public class SaveDefaultConfigCommand extends OperatorCommand {
    public SaveDefaultConfigCommand() {
        super("saveDefaultConfig");
    }

    @Override
    boolean execute() {
        StaticVariables.plugin.saveDefaultConfig();
        return true;
    }
}
