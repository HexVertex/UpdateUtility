package xelitez.updateutility;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import xelitez.updateutility.UpdateRegistry.ModInstance;

public class GuiModSlot extends GuiSlot
{
    final GuiUpdates parentUpdateGui;
    final List<GuiButtonTwitter> twitterButtons = new ArrayList<GuiButtonTwitter>();
	
	public GuiModSlot(GuiUpdates par1GuiUpdates) 
	{
		super(par1GuiUpdates.getMC(), par1GuiUpdates.width, par1GuiUpdates.height, 32, par1GuiUpdates.height - 51, 36);
		this.parentUpdateGui = par1GuiUpdates;
		for(int i = 0;i < this.getSize();i++)
		{
			twitterButtons.add(new GuiButtonTwitter(UpdateRegistry.instance().getIsTwitterAvailable(UpdateRegistry.instance().getMod(i)), UpdateRegistry.instance().shouldGetTweets(UpdateRegistry.instance().getMod(i))));
		}
	}

	@Override
	protected int getSize() 
	{
		return UpdateRegistry.instance().getModAmount();
	}

	@Override
	protected void elementClicked(int var1, boolean var2, int var3, int var4) 
	{
		if(!parentUpdateGui.isDownloading)
		{
			if(GuiUpdates.getSelectedMod(parentUpdateGui) == var1)
			{
		        GuiButtonTwitter button = this.twitterButtons.get(var1);
		        if(button.isAvailable && var3 >= button.xPosition && var3 < button.xPosition + button.width && var4 >= button.yPosition && var4 < button.yPosition + button.height)
		        {
		        	UpdateRegistry.instance().toggleModAvailibility(UpdateRegistry.instance().getMod(var1));
		        	this.updateButton(var1);
		        }
			}
	        GuiUpdates.onElementSelected(this.parentUpdateGui, var1);
	        boolean hasUpdateUrl = UpdateRegistry.instance().getMod(var1).update.getUpdateUrl() != null;
	        boolean hasDownloadUrl = UpdateRegistry.instance().getMod(var1).update.getDownloadUrl() != null && UpdateRegistry.instance().getMod(var1).update.isUpdateAvailable();
	        GuiUpdates.getOpenUpdateUrlButton(this.parentUpdateGui).enabled = hasUpdateUrl;
	        GuiUpdates.getUpdateButton(this.parentUpdateGui).enabled = hasDownloadUrl && UpdateRegistry.instance().getMod(var1).update.isUpdateAvailable();
		}
	}

	@Override
	protected boolean isSelected(int var1) 
	{
		return var1 == GuiUpdates.getSelectedMod(this.parentUpdateGui);
	}
	
    protected int getContentHeight()
    {
        return UpdateRegistry.instance().getModAmount() * 36;
    }
    
//    protected void drawContainerBackground(Tessellator tess)
//    {
//    	if(parentUpdateGui.getMC().theWorld != null)
//    	{
//    		parentUpdateGui.drawGradientRect(0, 0, parentUpdateGui.width, parentUpdateGui.height, -1072689136, -804253680);
//    	}
//    	else
//    	{
//    		super.drawContainerBackground(tess);
//    	}
//    }

	@Override
	protected void drawBackground() 
	{

	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5, int var6, int var7)
	{
	    ResourceLocation texture = new ResourceLocation(UpdateRegistry.instance().getMod(var1).update.getModIcon() != null ? UpdateRegistry.instance().getMod(var1).update.getModIcon() : "uu:xelitez/updateutility/default_icon.png");
		this.parentUpdateGui.getMC().getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        var5.startDrawingQuads();
        var5.setColorOpaque_I(16777215);
        var5.addVertexWithUV((double)var2, (double)(var3 + var4), 0.0D, 0.0D, 1.0D);
        var5.addVertexWithUV((double)(var2 + 32), (double)(var3 + var4), 0.0D, 1.0D, 1.0D);
        var5.addVertexWithUV((double)(var2 + 32), (double)var3, 0.0D, 1.0D, 0.0D);
        var5.addVertexWithUV((double)var2, (double)var3, 0.0D, 0.0D, 0.0D);
        var5.draw();
        ModInstance mod = UpdateRegistry.instance().getMod(var1);
        this.parentUpdateGui.drawString(GuiUpdates.getFontRenderer(parentUpdateGui), mod.mod.getName(), var2 + 34, var3 + 1, 16777215);
        this.parentUpdateGui.drawString(GuiUpdates.getFontRenderer(parentUpdateGui), "Current version: " + (mod.update.getCurrentVersion() != null ? mod.update.getCurrentVersion() : ""), var2 + 34, var3 + 12, 8421504);
        String color = mod.update.isUpdateAvailable() ? "\u00a7c" : "\u00a7a";
        this.parentUpdateGui.drawString(GuiUpdates.getFontRenderer(parentUpdateGui), "Latest version: " + color + (mod.update.getNewVersion() != null ? mod.update.getNewVersion() : ""), var2 + 34, var3 + 22, 8421504);
        if(GuiUpdates.getSelectedMod(parentUpdateGui) == var1)
        {
	        this.twitterButtons.get(var1).xPosition = var2 + 6;
	        this.twitterButtons.get(var1).yPosition = var3 + 6;
	        this.twitterButtons.get(var1).drawButton(this.parentUpdateGui.getMC(), var6, var7);
        }
	}
	
	public void updateButton(int var1)
	{
		twitterButtons.get(var1).setTwitterEnabled(UpdateRegistry.instance().shouldGetTweets(UpdateRegistry.instance().getMod(var1)));
	}

}
