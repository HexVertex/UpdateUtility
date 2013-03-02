package xelitez.updateutility;

import org.lwjgl.opengl.GL11;

import xelitez.updateutility.UpdateRegistry.ModInstance;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.texturepacks.GuiTexturePacks;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.EnumGameType;

public class GuiModSlot extends GuiSlot
{
    final GuiUpdates parentUpdateGui;
	
	public GuiModSlot(GuiUpdates par1GuiUpdates) 
	{
		super(par1GuiUpdates.getMC(), par1GuiUpdates.width, par1GuiUpdates.height, 32, par1GuiUpdates.height - 51, 36);
		this.parentUpdateGui = par1GuiUpdates;
	}

	@Override
	protected int getSize() 
	{
		return UpdateRegistry.instance().getModAmount();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) 
	{
        GuiUpdates.onElementSelected(this.parentUpdateGui, var1);
        boolean hasUpdateUrl = UpdateRegistry.instance().getMod(var1).update.getUpdateUrl() != null;
        boolean hasDownloadUrl = UpdateRegistry.instance().getMod(var1).update.getDownloadUrl() != null && UpdateRegistry.instance().getMod(var1).update.isUpdateAvailable();
        GuiUpdates.getOpenUpdateUrlButton(this.parentUpdateGui).enabled = hasUpdateUrl;
        GuiUpdates.getUpdateButton(this.parentUpdateGui).enabled = hasDownloadUrl;
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

	@Override
	protected void drawBackground() 
	{
		this.parentUpdateGui.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5) 
	{
		this.parentUpdateGui.getMC().renderEngine.bindTexture(this.parentUpdateGui.getMC().renderEngine.getTexture(UpdateRegistry.instance().getMod(par1).update.getModIcon() != null ? UpdateRegistry.instance().getMod(par1).update.getModIcon() : "/xelitez/updateutility/default_icon.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        par5.startDrawingQuads();
        par5.setColorOpaque_I(16777215);
        par5.addVertexWithUV((double)par2, (double)(par3 + par4), 0.0D, 0.0D, 1.0D);
        par5.addVertexWithUV((double)(par2 + 32), (double)(par3 + par4), 0.0D, 1.0D, 1.0D);
        par5.addVertexWithUV((double)(par2 + 32), (double)par3, 0.0D, 1.0D, 0.0D);
        par5.addVertexWithUV((double)par2, (double)par3, 0.0D, 0.0D, 0.0D);
        par5.draw();
        ModInstance mod = UpdateRegistry.instance().getMod(par1);
        this.parentUpdateGui.drawString(GuiUpdates.getFontRenderer(parentUpdateGui), mod.mod.getName(), par2 + 32 + 2, par3 + 1, 16777215);
        this.parentUpdateGui.drawString(GuiUpdates.getFontRenderer(parentUpdateGui), "Current version: " + (mod.update.getCurrentVersion() != null ? mod.update.getCurrentVersion() : ""), par2 + 32 + 2, par3 + 12, 8421504);
        this.parentUpdateGui.drawString(GuiUpdates.getFontRenderer(parentUpdateGui), "Latest version: " + (mod.update.getNewVersion() != null ? mod.update.getNewVersion() : ""), par2 + 32 + 2, par3 + 12 + 10, 8421504);
	}

}
