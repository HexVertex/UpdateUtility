package xelitez.updateutility;

import java.awt.Color;
import java.net.URI;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;

public class GuiUpdates extends GuiScreen
{
	private GuiModSlot guiModSlot;
	
    protected GuiScreen parentScreen;
	
	private GuiButton buttonOpenUpdateUrl;
	private GuiButton buttonUpdate;
	
	public boolean isDownloading = false;
	
    private int selectedMod;
    
    private int downloadPercentage = -1;
    private String text = "";
    private boolean flashing = false;
    private int tickcounter = 0;
    private boolean visable = true;
    
    public GuiUpdates(GuiScreen parentGui)
    {
        this.parentScreen = parentGui;
        this.selectedMod = -1;
    }
    
    public void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (float)(par5 >> 24 & 255) / 255.0F;
        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
        float f3 = (float)(par5 & 255) / 255.0F;
        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
        float f7 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)par3, (double)par2, (double)this.zLevel);
        tessellator.addVertex((double)par1, (double)par2, (double)this.zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)par1, (double)par4, (double)this.zLevel);
        tessellator.addVertex((double)par3, (double)par4, (double)this.zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    
    public void updateScreen() 
    {
    	if(UpdaterThread.stringsToLookFor.size() > 0 || UpdaterThread.filesToMove.size() > 0)
    	{
    		for(Object button : this.buttonList)
    		{
    			if(button instanceof GuiButton && ((GuiButton) button).id == 0 && ((GuiButton) button).displayString.matches(I18n.format("gui.cancel", new Object[0])))
    			{
    				((GuiButton)button).displayString = "Shutdown Minecraft and Update";
    			}
    		}
    	}
    	if(this.flashing)
    	{
    		this.tickcounter++;
    		if(this.tickcounter >= 15)
    		{
    			this.tickcounter = 0;
    			this.visable = !this.visable;
    		}
    	}
    }
	
    public void drawScreen(int par1, int par2, float par3)
    {
        this.guiModSlot.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, "XEliteZ Update Utility", this.width / 2, 20, 16777215);
        this.drawDownloadBar();
        super.drawScreen(par1, par2, par3);
    }
    
    public void initGui()
    {
        this.guiModSlot = new GuiModSlot(this);
        this.guiModSlot.registerScrollButtons(4, 5);
        this.initButtons();
    }
    
    @SuppressWarnings("unchecked")
	public void initButtons()
    {
        this.buttonList.add(this.buttonOpenUpdateUrl = new GuiButton(1, this.width / 2 - 88, this.height - 40, 90, 20, I18n.format("Open Update Url", new Object[0])));
        this.buttonList.add(this.buttonUpdate = new GuiButton(2, this.width / 2 - 165, this.height - 40, 72, 20, I18n.format("Update", new Object[0])));
        this.buttonList.add(new GuiButtonRefresh(3, this.width / 2 + 150, 7));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 15, this.height - 40, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonOpenUpdateUrl.enabled = false;
        this.buttonUpdate.enabled = false;
    }
    
    
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 0)
            {
            	if(UpdaterThread.stringsToLookFor.size() > 0 || UpdaterThread.filesToMove.size() > 0)
            	{
            		if(this.mc.theWorld != null)
            		{
            			par1GuiButton.enabled = false;
                        this.mc.theWorld.sendQuittingDisconnectingPacket();
                        this.mc.loadWorld((WorldClient)null);
            		}
            		UpdaterThread.runCopier();
            		this.mc.shutdown();
            	}
            	else
            	{
                    this.mc.displayGuiScreen(this.parentScreen);
            	}
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
            else if(par1GuiButton.id == 2)
            {
            	Downloader.download(this, selectedMod);
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
    
    @SuppressWarnings("unchecked")
	public void setDownloading(boolean b)
    {
    	this.isDownloading = b;
    	this.buttonUpdate.enabled = !b;
    	this.buttonOpenUpdateUrl.enabled = !b;
    	for(GuiButton button : (List<GuiButton>)buttonList)
    	{
    		if(button.id == 0)
    		{
    			button.enabled = !b;
    		}
    	}
    }
    
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 && !this.isDownloading)
        {
        	if(UpdaterThread.stringsToLookFor.size() > 0 || UpdaterThread.filesToMove.size() > 0)
        	{
        		if(this.mc.theWorld != null)
        		{
                    this.mc.theWorld.sendQuittingDisconnectingPacket();
                    this.mc.loadWorld((WorldClient)null);
        		}
        		UpdaterThread.runCopier();
        		this.mc.shutdown();
        	}
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
    }
    
    public void updateDownloadBar(int percentage, String Text, boolean flashing)
    {
    	this.downloadPercentage = percentage;
    	this.text = Text;
    	this.flashing = flashing;
    	this.tickcounter = 0;
    	this.visable = true;
    }
    
    @SuppressWarnings("static-access")
	private void drawDownloadBar()
    {
    	if(this.downloadPercentage >= 0)
    	{
    		this.drawRect(this.width / 2 - 150, this.height - 17, this.width / 2 - 150 + 100 * 3, this.height - 5, Color.RED.getRGB());
    		this.drawRect(this.width / 2 - 150, this.height - 17, this.width / 2 - 150 + this.downloadPercentage * 3, this.height - 5, Color.GREEN.getRGB());
    		if(this.visable || !this.flashing)
    		{
    			this.drawCenteredString(this.fontRendererObj, text, this.width / 2, this.height - 15, 16777215);
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
    	return par0GuiUpdates.fontRendererObj;
    }
}
