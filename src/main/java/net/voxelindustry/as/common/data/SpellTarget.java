package net.voxelindustry.as.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static net.voxelindustry.as.common.ArcanumScripta.MODID;

@AllArgsConstructor
public enum SpellTarget
{
    SINGULAR_BLOCK(MODID + ".spell.target.singleblock"),
    SINGULAR_PLAYER(MODID + ".spell.target.singleplayer"),
    WORLD_AREA(MODID + ".spell.target.worldarea"),
    GROUP_PLAYER(MODID + ".spell.target.groupplayer"),
    ALL_PLAYER(MODID + ".spell.target.allplayer");

    @Getter
    private String unlocalizedName;

    public static SpellTarget fromUnlocalizedName(String unloc)
    {
        for (SpellTarget value : values())
        {
            if (value.getUnlocalizedName().equals(unloc))
                return value;
        }
        return SINGULAR_BLOCK;
    }

    public String toString()
    {
        return this.name().toLowerCase();
    }
}
