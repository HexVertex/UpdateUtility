package xelitez.updateutility;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiRenameWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class GuiUpdates extends GuiScreen
{
	private GuiModSlot guiModSlot;
	
    protected GuiScreen parentScreen;
	
	private GuiButton buttonOpenUpdateUrl;
	private GuiButton buttonUpdate;
	
    private int selectedMod;
    private boolean selected = false;
    
    public GuiUpdates(GuiScreen parentGui)
    {
        this.parentScreen = parentGui;
        this.selectedMod = -1;
    }
	
    public void drawScreen(int par1, int par2, float par3)
    {
        this.guiModSlot.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, "XEliteZ Update Utility", this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
    
    public void initGui()
    {
        this.guiModSlot = new GuiModSlot(this);
        this.guiModSlot.registerScrollButtons(this.controlList, 4, 5);
        this.initButtons();
    }
    
    public void initButtons()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        this.controlList.add(this.buttonOpenUpdateUrl = new GuiButton(1, this.width / 2 - 88, this.height - 40, 90, 20, var1.translateKey("Open Update Url")));
        this.controlList.add(this.buttonUpdate = new GuiButton(2, this.width / 2 - 165, this.height - 40, 72, 20, var1.translateKey("Update")));
        this.controlList.add(new GuiButtonRefresh(3, this.width / 2 + 150, 7));
        this.controlList.add(new GuiButton(0, this.width / 2 + 15, this.height - 40, 150, 20, var1.translateKey("gui.cancel")));
        this.buttonOpenUpdateUrl.enabled = false;
        this.buttonUpdate.enabled = false;
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 0)
            {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if(par1GuiButton.id == 3)
            {
            	UpdateRegistry.instance().checkForUpdates();
            }
            else
            {
                this.guiModSlot.actionPerformed(par1GuiButton);
            }
        }
    }
    
    public Minecraft getMC()
    {
    	return this.mc;
    }
    
    static int onElementSelected(GuiUpdates par0GuiUpdates, int par1)
    {
        return par0GuiUpdates.selectedMod = par1;
    }
    
    static GuiButton getOpenUpdateUrlButton(GuiUpdates par0GuiUpdates)
    {
    	return par0GuiUpdates.buttonOpenUpdateUrl;
    }
    
    static GuiButton getUpdateButton(GuiUpdates par0GuiUpdates)
    {
    	return par0GuiUpdates.buttonUpdate;
    }
    
    static int getSelectedMod(GuiUpdates par0GuiUpdates)
    {
    	return par0GuiUpdates.selectedMod;
    }
    
    static FontRenderer getFontRenderer(GuiUpdates par0GuiUpdates)
    {
    	return par0GuiUpdates.fontRenderer;
    }
}
