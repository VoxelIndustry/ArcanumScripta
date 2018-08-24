package net.voxelindustry.as.client.gui;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.value.BaseProperty;
import net.minecraft.client.resources.I18n;
import net.voxelindustry.as.common.data.*;
import org.yggard.brokkgui.data.EAlignment;
import org.yggard.brokkgui.data.RectOffset;
import org.yggard.brokkgui.data.RelativeBindingHelper;
import org.yggard.brokkgui.element.GuiLabel;
import org.yggard.brokkgui.gui.BrokkGuiScreen;
import org.yggard.brokkgui.panel.GuiAbsolutePane;

import java.util.Arrays;
import java.util.Optional;
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
    private GuiLabel       targetPrefixLabel;

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
        actionField.setFocused();

        typeField = new SpellTextField(SpellPart.TYPE, this, mainPanel, actionField);

        actionField.getTextProperty().addListener(obs ->
        {
            if (Arrays.stream(SpellAction.values()).anyMatch(spell -> spell.toString().equals(actionField.getText())))
                typeField.setSuggestions(graph.getGraph().successors(SpellAction.valueOf(actionField.getText().toUpperCase()))
                        .stream().map(ISpellComponent::getName).collect(Collectors.toList()));
        });

        targetPrefixLabel = new GuiLabel("");
        targetPrefixLabel.setExpandToText(true);
        targetPrefixLabel.setHeight(20);
        targetPrefixLabel.setID("target-prefix-label");
        targetPrefixLabel.setTextAlignment(EAlignment.MIDDLE_UP);
        targetPrefixLabel.setTextPadding(new RectOffset(2, 0, 0, 0));
        mainPanel.addChild(targetPrefixLabel);

        RelativeBindingHelper.bindToPos(targetPrefixLabel, mainPanel, new BaseBinding<Float>()
        {
            {
                super.bind(actionField.getWidthProperty(), typeField.getWidthProperty());
            }

            @Override
            public Float computeValue()
            {
                return actionField.getWidth() + typeField.getWidth() + 4;
            }
        }, GuiSpellPrompt.DUMMY_HEIGHT);

        targetField = new SpellTextField(SpellPart.TARGET, this, mainPanel, actionField, typeField, targetPrefixLabel);
        amountField = new SpellTextField(SpellPart.AMOUNT, this, mainPanel, actionField,
                typeField, targetPrefixLabel, targetField);

        typeField.getTextProperty().addListener(obs ->
        {
            SpellTypes.TYPES.stream().filter(spell -> spell.getName().equals(typeField.getText())).forEach(spell ->
            {
                targetField.setSuggestions(spell.getTargets().stream()
                        .map(spellTarget -> I18n.format(spellTarget.getUnlocalizedName())).collect(Collectors.toList()));

                amountField.setSuggestions(Arrays.asList("once", "twice", "thrice"));
                if (spell.isContinuous())
                    amountField.addSuggestion("forever");
            });
        });

        targetField.getTextProperty().addListener(obs ->
        {
            Optional<SpellTarget> target = Arrays.stream(SpellTarget.values())
                    .filter(spell -> I18n.format(spell.getUnlocalizedName()).equals(targetField.getText())).findFirst();

            if (target.isPresent())
                targetPrefixLabel.setText(I18n.format(target.get().getUnlocalizedPrefix()));
            else
                targetPrefixLabel.setText("");
        });
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
