package net.voxelindustry.as.client.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.voxelindustry.as.common.ArcanumScripta;

@AllArgsConstructor
@Getter
public enum SpellPart
{
    ACTION(ArcanumScripta.MODID + ".spell.part.action"),
    TYPE(ArcanumScripta.MODID + ".spell.part.type"),
    TARGET(ArcanumScripta.MODID + ".spell.part.target"),
    AMOUNT(ArcanumScripta.MODID + ".spell.part.amount");

    private String unlocalizedName;

    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
}
