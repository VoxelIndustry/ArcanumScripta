package net.voxelindustry.as.client.gui;

import fr.ourten.teabeans.value.BaseProperty;
import net.voxelindustry.as.common.data.ISpellComponent;
import net.voxelindustry.as.common.data.SpellAction;
import net.voxelindustry.as.common.data.SpellGraph;
import org.yggard.brokkgui.gui.BrokkGuiScreen;
import org.yggard.brokkgui.panel.GuiAbsolutePane;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GuiSpellPrompt extends BrokkGuiScreen
{
    private static final int WIDTH  = 250;
    private static final int HEIGHT = 100;

    public final static BaseProperty<Float> DUMMY_HEIGHT = new BaseProperty<>(HEIGHT / 2f - 10, "dummyHeightProperty");

    private SpellGraph     graph = new SpellGraph();
    private SpellTextField actionField;
    private SpellTextField typeField;
    private SpellTextField targetField;
    private SpellTextField amountField;

    public GuiSpellPrompt()
    {
        super(0.5f, 0.5f, 200, 200);

        GuiAbsolutePane mainPanel = new GuiAbsolutePane();
        this.setMainPanel(mainPanel);

        this.setupFields(mainPanel);

        this.addStylesheet("/assets/arcanumscripta/css/spellprompt.css");
    }

    private void setupFields(GuiAbsolutePane mainPanel)
    {
        actionField = new SpellTextField(SpellPart.ACTION, this, mainPanel);
        actionField.setSuggestions(Arrays.stream(SpellAction.values()).map(SpellAction::toString).collect(Collectors.toList()));

        typeField = new SpellTextField(SpellPart.TYPE, this, mainPanel, actionField);

        actionField.getTextProperty().addListener(obs ->
        {
            if (Arrays.stream(SpellAction.values()).anyMatch(spell -> spell.toString().equals(actionField.getText())))
                typeField.setSuggestions(graph.getGraph().successors(SpellAction.valueOf(actionField.getText().toUpperCase()))
                        .stream().map(ISpellComponent::getName).collect(Collectors.toList()));
        });

        targetField = new SpellTextField(SpellPart.TARGET, this, mainPanel, actionField, typeField);
        targetField.addSuggestion("me");
        targetField.addSuggestion("attacker");
        targetField.addSuggestion("anyone");

        amountField = new SpellTextField(SpellPart.AMOUNT, this, mainPanel, actionField, typeField, targetField);
        amountField.addSuggestion("once");

        mainPanel.addChild(actionField, 0, HEIGHT / 2f - 10);
    }

    void jumpNext(SpellTextField field)
    {
        jumpField(field, actionField, typeField, targetField, amountField);
    }

    void jumpPrev(SpellTextField field)
    {
        jumpField(field, amountField, targetField, typeField, actionField);
    }

    private void jumpField(SpellTextField field, SpellTextField fourthField, SpellTextField thirdField,
                           SpellTextField secondField, SpellTextField firstField)
    {
        if (field == fourthField)
            thirdField.setFocused();
        else if (field == thirdField)
            secondField.setFocused();
        else if (field == secondField)
            firstField.setFocused();
    }
}
