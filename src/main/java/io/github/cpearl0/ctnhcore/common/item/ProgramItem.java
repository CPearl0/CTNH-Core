package io.github.cpearl0.ctnhcore.common.item;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ProgramItem extends Item {
    public static final List<ProgramItem> PROGRAMS = new ArrayList<>();

    public ProgramItem(Properties properties) {
        super(properties);
        PROGRAMS.add(this);
    }
}
