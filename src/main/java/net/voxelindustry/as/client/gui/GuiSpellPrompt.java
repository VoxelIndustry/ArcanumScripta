package net.voxelindustry.as.client.gui;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.value.BaseProperty;
import org.lwjgl.input.Keyboard;
import org.yggard.brokkgui.data.RelativeBindingHelper;
import org.yggard.brokkgui.element.GuiTextfieldComplete;
import org.yggard.brokkgui.event.KeyEvent;
import org.yggard.brokkgui.gui.BrokkGuiScreen;
import org.yggard.brokkgui.panel.GuiAbsolutePane;

import static net.minecraft.util.text.TextFormatting.ITALIC;

public class GuiSpellPrompt extends BrokkGuiScreen
{
    private static final int WIDTH = 250;
    private static final int HEIGHT = 100;

    private final BaseProperty<Float> DUMMY_HEIGHT = new BaseProperty<>(HEIGHT / 2f - 10, "dummyHeightProperty");

    private GuiTextfieldComplete actionField;
    private GuiTextfieldComplete typeField;
    private GuiTextfieldComplete targetField;
    private GuiTextfieldComplete amountField;

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
        actionField = new GuiTextfieldComplete();
        actionField.setPromptText(ITALIC + "action");
        actionField.addSuggestion("throw");
        actionField.addSuggestion("summon");
        actionField.addSuggestion("cast");
        actionField.setCharBeforeCompletion(1);
        actionField.setID("action-field");

        typeField = new GuiTextfieldComplete();
        typeField.setPromptText(ITALIC + "type");
        typeField.addSuggestion("fireball");
        typeField.addSuggestion("lightning");
        typeField.setCharBeforeCompletion(1);
        typeField.setID("type-field");

        targetField = new GuiTextfieldComplete();
        targetField.setPromptText(ITALIC + "target");
        targetField.addSuggestion("me");
        targetField.addSuggestion("attacker");
        targetField.addSuggestion("anyone");
        targetField.setCharBeforeCompletion(1);
        targetField.setID("target-field");

        amountField = new GuiTextfieldComplete();
        amountField.setPromptText(ITALIC + "amount");
        amountField.addSuggestion("once");
        amountField.setCharBeforeCompletion(1);
        amountField.setID("amount-field");

        mainPanel.addChild(actionField, 0, HEIGHT / 2f - 10);
        mainPanel.addChild(typeField);
        mainPanel.addChild(targetField, 0, 0);
        mainPanel.addChild(amountField, 0, 0);

        actionField.setExpandToText(true);
        typeField.setExpandToText(true);
        targetField.setExpandToText(true);
        amountField.setExpandToText(true);
        actionField.setHeight(20);
        typeField.setHeight(20);
        targetField.setHeight(20);
        amountField.setHeight(20);

        RelativeBindingHelper.bindToPos(typeField, mainPanel, new BaseBinding<Float>()
        {
            {
                super.bind(actionField.getWidthProperty());
            }

            @Override
            public Float computeValue()
            {
                return actionField.getWidth() + 2;
            }
        }, DUMMY_HEIGHT);

        RelativeBindingHelper.bindToPos(targetField, mainPanel, new BaseBinding<Float>()
        {
            {
                super.bind(actionField.getWidthProperty(), typeField.getWidthProperty());
            }

            @Override
            public Float computeValue()
            {
                return actionField.getWidth() + typeField.getWidth() + 4;
            }
        }, DUMMY_HEIGHT);

        RelativeBindingHelper.bindToPos(amountField, mainPanel, new BaseBinding<Float>()
        {
            {
                super.bind(actionField.getWidthProperty(), typeField.getWidthProperty(),
                        targetField.getWidthProperty());
            }

            @Override
            public Float computeValue()
            {
                return actionField.getWidth() + typeField.getWidth() + targetField.getWidth() + 6;
            }
        }, DUMMY_HEIGHT);

        actionField.getEventDispatcher().addHandler(KeyEvent.TYPE, e ->
        {
            if (isNextKey(e.getKey()) || (actionField.getCursorPos() == actionField.getText().length() && e.getKey() == Keyboard.KEY_RIGHT))
                typeField.setFocused();
        });

        typeField.getEventDispatcher().addHandler(KeyEvent.TYPE, e ->
        {
            if (isNextKey(e.getKey()) || (typeField.getCursorPos() == typeField.getText().length() && e.getKey() == Keyboard.KEY_RIGHT))
                targetField.setFocused();
            else if (typeField.getCursorPos() == 0 && (e.getKey() == Keyboard.KEY_DELETE || e.getKey() == Keyboard.KEY_LEFT))
                actionField.setFocused();
        });

        targetField.getEventDispatcher().addHandler(KeyEvent.TYPE, e ->
        {
            if (isNextKey(e.getKey()) || (targetField.getCursorPos() == targetField.getText().length() && e.getKey() == Keyboard.KEY_RIGHT))
                amountField.setFocused();
            else if (targetField.getCursorPos() == 0 && (e.getKey() == Keyboard.KEY_DELETE || e.getKey() == Keyboard.KEY_LEFT))
                typeField.setFocused();
        });

        amountField.getEventDispatcher().addHandler(KeyEvent.TYPE, e ->
        {
            if (amountField.getCursorPos() == 0 && (e.getKey() == Keyboard.KEY_DELETE || e.getKey() == Keyboard.KEY_LEFT))
                targetField.setFocused();
        });
    }

    private boolean isNextKey(int key)
    {
        return key == Keyboard.KEY_NUMPADENTER || key == Keyboard.KEY_SPACE || key == Keyboard.KEY_RETURN || key == Keyboard.KEY_TAB;
    }
}
