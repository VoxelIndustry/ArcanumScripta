package net.voxelindustry.as.client.gui;

import fr.ourten.teabeans.value.BaseProperty;
import org.yggard.brokkgui.gui.BrokkGuiScreen;
import org.yggard.brokkgui.panel.GuiAbsolutePane;

public class GuiSpellPrompt extends BrokkGuiScreen
{
    private static final int WIDTH  = 250;
    private static final int HEIGHT = 100;

    public final static BaseProperty<Float> DUMMY_HEIGHT = new BaseProperty<>(HEIGHT / 2f - 10, "dummyHeightProperty");

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
        actionField.addSuggestion("throw");
        actionField.addSuggestion("summon");
        actionField.addSuggestion("cast");

        typeField = new SpellTextField(SpellPart.TYPE, this, mainPanel, actionField);
        typeField.addSuggestion("fireball");
        typeField.addSuggestion("lightning");

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
