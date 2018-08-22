package net.voxelindustry.as.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpellType implements ISpellComponent
{
    private float  cost;
    private String name;

    @Override
    public boolean isCostMultiplier()
    {
        return false;
    }
}
