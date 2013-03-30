package xelitez.updateutility;

import java.net.URI;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
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
	
    public void drawScreen(int par1, int par2, float par3)
    {
        this.guiModSlot.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, "XEliteZ Update Utility", this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
        for(Object obj : buttonList)
        {
        	if(obj instanceof GuiButton && ((GuiButton) obj).id == 2)
        	{
        		GuiButton button = (GuiButton)obj;
        		if(par1 > button.xPosition && par1 < button.xPosition + 72 && par2 > button.yPosition && par2 < button.yPosition + 20)
        		{
        	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        	        RenderHelper.disableStandardItemLighting();
        	        GL11.glDisable(GL11.GL_LIGHTING);
        	        GL11.glDisable(GL11.GL_DEPTH_TEST);
        	        int var4 = this.fontRenderer.getStringWidth("Currently not available");
        	        int var5 = par1 + 12;
        	        int var6 = par2 - 12;
        	        byte var8 = 8;
        	        this.zLevel = 300.0F;
        	        int var9 = -267386864;
        	        this.drawGradientRect(var5 - 3, var6 - 4, var5 + var4 + 3, var6 - 3, var9, var9);
        	        this.drawGradientRect(var5 - 3, var6 + var8 + 3, var5 + var4 + 3, var6 + var8 + 4, var9, var9);
        	        this.drawGradientRect(var5 - 3, var6 - 3, var5 + var4 + 3, var6 + var8 + 3, var9, var9);
        	        this.drawGradientRect(var5 - 4, var6 - 3, var5 - 3, var6 + var8 + 3, var9, var9);
        	        this.drawGradientRect(var5 + var4 + 3, var6 - 3, var5 + var4 + 4, var6 + var8 + 3, var9, var9);
        	        int var10 = 1347420415;
        	        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        	        this.drawGradientRect(var5 - 3, var6 - 3 + 1, var5 - 3 + 1, var6 + var8 + 3 - 1, var10, var11);
        	        this.drawGradientRect(var5 + var4 + 2, var6 - 3 + 1, var5 + var4 + 3, var6 + var8 + 3 - 1, var10, var11);
        	        this.drawGradientRect(var5 - 3, var6 - 3, var5 + var4 + 3, var6 - 3 + 1, var10, var10);
        	        this.drawGradientRect(var5 - 3, var6 + var8 + 2, var5 + var4 + 3, var6 + var8 + 3, var11, var11);
        	        this.fontRenderer.drawStringWithShadow("Currently not available", var5, var6, -1);
        	        this.zLevel = 0.0F;
        		}
        	}
        }
    }
    
    public void initGui()
    {
        this.guiModSlot = new GuiModSlot(this);
        this.guiModSlot.registerScrollButtons(this.buttonList, 4, 5);
        this.initButtons();
    }
    
    @SuppressWarnings("unchecked")
	public void initButtons()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(this.buttonOpenUpdateUrl = new GuiButton(1, this.width / 2 - 88, this.height - 40, 90, 20, var1.translateKey("Open Update Url")));
        this.buttonList.add(this.buttonUpdate = new GuiButton(2, this.width / 2 - 165, this.height - 40, 72, 20, var1.translateKey("Update")));
        this.buttonList.add(new GuiButtonRefresh(3, this.width / 2 + 150, 7));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 15, this.height - 40, 150, 20, var1.translateKey("gui.cancel")));
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
