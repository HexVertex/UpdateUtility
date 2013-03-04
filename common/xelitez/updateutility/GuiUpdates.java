package xelitez.updateutility;

import java.net.URI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

public class GuiUpdates extends GuiScreen
{
	private GuiModSlot guiModSlot;
	
    protected GuiScreen parentScreen;
	
	private GuiButton buttonOpenUpdateUrl;
	private GuiButton buttonUpdate;
	
    private int selectedMod;
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
    
    @SuppressWarnings("unchecked")
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
            else if(par1GuiButton.id == 1)
            {
                try
                {
                    URI par1URI = new URI(UpdateRegistry.instance().getMod(selectedMod).update.getUpdateUrl());
                    Class<?> var2 = Class.forName("java.awt.Desktop");
                    Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {par1URI});
                }
                catch (Throwable var4)
                {
                    var4.printStackTrace();
                }
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
