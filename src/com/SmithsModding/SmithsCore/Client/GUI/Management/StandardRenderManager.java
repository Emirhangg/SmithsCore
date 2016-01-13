package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Animation.*;
import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Scissoring.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Client.Registry.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

import java.util.*;

/**
 * Created by marcf on 12/3/2015.
 */
public class StandardRenderManager implements IRenderManager {

    private static ArrayList<MinecraftColor> colorStack = new ArrayList<MinecraftColor>();

    Gui root;

    public StandardRenderManager (Gui root)
    {
        if (!(root instanceof IGUIBasedComponentHost))
            throw new IllegalArgumentException("The given Root for this manager is not a IGUIBasedComponentHost!");

        this.root = root;
    }

    /**
     * Method should be used when rendering UI Components. It is used to set the color to a new one and remember the old
     * one.
     * <p/>
     * Allows for the Enabled color to be put on the known stack, or any other color set by a component.
     */
    public static void pushColorOnRenderStack (MinecraftColor color) {
        colorStack.add(0, color);
        color.performOpenGLColoring();
    }

    /**
     * Method should be used when rendering UI Components. It is used to reset the color to the previous state instead
     * of to white.
     * <p/>
     * Allows for the Enabled color to be put on the known stack, or any other color set by a component.
     */
    public static void popColorFromRenderStack () {
        if (colorStack.size() == 0) {
            SmithsCore.getLogger().error("The color Stack is empty!");
            return;
        }

        colorStack.remove(0);

        if (colorStack.size() == 0) {
            new MinecraftColor(MinecraftColor.WHITE).performOpenGLColoring();
            return;
        }

        colorStack.get(0).performOpenGLColoring();
    }

    /**
     * Method to get the Gui this StandardRenderManager renders on.
     *
     * @return The current active GUI
     */
    @Override
    public Gui getRootGuiObject() {
        return root;
    }

    /**
     * Methd to get the root GuiManager this StandardRenderManager belongs to.
     *
     * @return The GuiManager of the Root GUI object.
     */
    @Override
    public IGUIManager getRootGuiManager() {
        return ((IGUIBasedComponentHost)root).getRootManager();
    }

    /**
     * Method to render the BackGround of a Component
     *
     * @param component The Component to render.
     */
    @Override
    public void renderBackgroundComponent (IGUIComponent component, boolean parentEnabled) {
        ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

        IGUIComponentState state = component.getState();

        if (!state.isVisible())
            return;

        component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

        GlStateManager.pushMatrix();

        GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

        if (component instanceof IAnimatibleGuiComponent)
            ( (IAnimatibleGuiComponent) component ).performAnimation(registry.getPartialTickTime());

        if (component instanceof IGUIBasedLedgerHost) {
            IGUIBasedLedgerHost ledgerHost = (IGUIBasedLedgerHost) component;

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getLeftLedgers().values()) {
                this.renderBackgroundComponent(ledger, false);
            }

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getRightLedgers().values()) {
                this.renderBackgroundComponent(ledger, false);
            }
        }

        if (!state.isEnabled()) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            pushColorOnRenderStack(new MinecraftColor(MinecraftColor.darkGray));
        }

        if (!( component instanceof GuiContainerSmithsCore ))
            component.drawBackground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

        if (!state.isEnabled()) {
            popColorFromRenderStack();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }

        if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor())
            GuiHelper.enableScissor(( (IScissoredGuiComponent) component ).getGlobalScissorLocation());

        if (component instanceof IGUIBasedComponentHost) {
            for (IGUIComponent subComponent : ( (IGUIBasedComponentHost) component ).getAllComponents().values()) {
                if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor() && subComponent instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) subComponent ).shouldScissor())
                    GuiHelper.disableScissor();

                this.renderBackgroundComponent(subComponent, state.isEnabled());

                if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor() && subComponent instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) subComponent ).shouldScissor())
                    GuiHelper.enableScissor(( (IScissoredGuiComponent) component ).getGlobalScissorLocation());
            }
        }

        if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor())
            GuiHelper.disableScissor();

        GlStateManager.popMatrix();
    }

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component The Component to render
     */
    @Override
    public void renderForegroundComponent (IGUIComponent component, boolean parentEnabled) {

    }

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     */
    @Override
    public void renderToolTipComponent(IGUIComponent component) {

    }
}