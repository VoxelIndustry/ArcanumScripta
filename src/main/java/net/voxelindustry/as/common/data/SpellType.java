package net.voxelindustry.as.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@Getter
@AllArgsConstructor
public class SpellType implements ISpellComponent
{
    private float                cost;
    private String               name;
    private EnumSet<SpellTarget> targets;
    private boolean              continuous;

    @Override
    public boolean isCostMultiplier()
    {
        return false;
    }

    public static Builder build()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private float                cost;
        private String               name;
        private EnumSet<SpellTarget> targets;
        private boolean              continuous;

        public Builder()
        {
            this.targets = EnumSet.noneOf(SpellTarget.class);
        }

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder cost(float cost)
        {
            this.cost = cost;
            return this;
        }

        public Builder target(SpellTarget target)
        {
            this.targets.add(target);
            return this;
        }

        public Builder continuous()
        {
            this.continuous = true;
            return this;
        }

        public SpellType create()
        {
            SpellType type = new SpellType(cost, name, targets, continuous);
            SpellTypes.TYPES.add(type);
            return type;
        }
    }
}
