package net.voxelindustry.as.client.gui;

import fr.ourten.teabeans.binding.BaseBinding;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import org.yggard.brokkgui.component.GuiNode;
import org.yggard.brokkgui.data.RelativeBindingHelper;
import org.yggard.brokkgui.element.GuiTextfieldComplete;
import org.yggard.brokkgui.event.KeyEvent;
import org.yggard.brokkgui.panel.GuiPane;

import static net.minecraft.util.text.TextFormatting.ITALIC;

public class SpellTextField extends GuiTextfieldComplete
{
    private boolean resetCursor = true;

    public SpellTextField(SpellPart part, GuiSpellPrompt prompt, GuiPane mainPanel, GuiNode... nodes)
    {
        super();
        this.setPromptText(ITALIC + I18n.format(part.getUnlocalizedName()));
        this.setCharBeforeCompletion(0);
        this.setID(part.toString() + "-field");

        this.setExpandToText(true);
        this.setHeight(20);

        if (nodes.length != 0)
        {
            mainPanel.addChild(this);
            RelativeBindingHelper.bindToPos(this, mainPanel, new BaseBinding<Float>()
            {
                {
                    for (GuiNode node : nodes)
                        super.bind(node.getWidthProperty());
                }

                @Override
                public Float computeValue()
                {
                    float result = 0;
                    for (GuiNode node : nodes)
                        result += node.getWidth() + 2;
                    return result;
                }
            }, GuiSpellPrompt.DUMMY_HEIGHT);
        }

        this.getEventDispatcher().addHandler(KeyEvent.TYPE, e ->
        {
            if (isNextKey(e.getKey()) || (this.getCursorPos() == this.getText().length() && e.getKey() == Keyboard.KEY_RIGHT))
            {
                this.setText(this.getText().trim());
                this.setCursorPos(this.getText().length());
                prompt.jumpNext(this);
            }
            else if (this.getCursorPos() == 0 && e.getKey() == Keyboard.KEY_LEFT)
                prompt.jumpPrev(this);
            else if (this.getCursorPos() == 0 && resetCursor && e.getKey() == Keyboard.KEY_BACK)
            {
                prompt.jumpPrev(this);
                this.resetCursor = false;
            }
        });

        this.setOnCursorMoveEvent(e -> resetCursor = e.getMouseX() == 0 && e.getMouseY() == 0);
    }

    private boolean isNextKey(int key)
    {
        return key == Keyboard.KEY_NUMPADENTER || key == Keyboard.KEY_SPACE || key == Keyboard.KEY_RETURN || key == Keyboard.KEY_TAB;
    }
}
