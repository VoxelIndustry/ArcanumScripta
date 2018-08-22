package net.voxelindustry.as.common.data;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import lombok.Getter;

public class SpellGraph
{
    @Getter
    private MutableGraph<ISpellComponent> graph;

    public SpellGraph()
    {
        this.graph = GraphBuilder.directed().build();

        for (SpellAction action : SpellAction.values())
            this.graph.addNode(action);

        this.graph.addNode(SpellTypes.FIREBALL);
        this.graph.addNode(SpellTypes.ICEBALL);
        this.graph.addNode(SpellTypes.BOULDER);
        this.graph.addNode(SpellTypes.LIGHTNING);

        this.graph.putEdge(SpellAction.THROW, SpellTypes.FIREBALL);
        this.graph.putEdge(SpellAction.THROW, SpellTypes.ICEBALL);
        this.graph.putEdge(SpellAction.THROW, SpellTypes.BOULDER);

        this.graph.putEdge(SpellAction.CAST, SpellTypes.LIGHTNING);
    }
}
