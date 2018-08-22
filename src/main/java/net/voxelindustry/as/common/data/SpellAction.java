package net.voxelindustry.as.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpellAction implements ISpellComponent
{
    THROW(1), CAST(1), SUMMON(1);

    private float cost;

    @Override
    public boolean isCostMultiplier()
    {
        return true;
    }

    @Override
    public String getName()
    {
        return this.toString();
    }

    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
}
