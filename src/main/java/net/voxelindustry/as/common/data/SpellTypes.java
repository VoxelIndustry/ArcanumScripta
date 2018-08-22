package net.voxelindustry.as.common.data;

import static net.voxelindustry.as.common.data.SpellTarget.*;

public class SpellTypes
{
    public static final SpellType FIREBALL = SpellType.build().name("fireball").cost(10)
            .target(SINGULAR_PLAYER).target(SINGULAR_BLOCK).create();

    public static final SpellType ICEBALL = SpellType.build().name("iceball").cost(10)
            .target(SINGULAR_PLAYER).target(SINGULAR_BLOCK).create();

    public static final SpellType BOULDER = SpellType.build().name("boulder").cost(10)
            .target(SINGULAR_PLAYER).target(SINGULAR_BLOCK).target(WORLD_AREA).create();

    public static final SpellType LIGHTNING = SpellType.build().name("lightning").cost(10)
            .target(SINGULAR_PLAYER).target(GROUP_PLAYER).create();
}
