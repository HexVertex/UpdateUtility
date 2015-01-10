package xelitez.updateutility;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonTwitter extends GuiButton
{
	private static final ResourceLocation texture = new ResourceLocation("uu:xelitez/updateutility/buttontwitter.png");
	
	public boolean isAvailable;
	public boolean isTwitterEnabled;
	
    public GuiButtonTwitter(boolean isAvailable, boolean isEnabled)
    {
        super(0, 0, 0, 20, 20, "");
        this.isAvailable = isAvailable;
        this.isTwitterEnabled = isEnabled;
    }
    
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            par1Minecraft.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = 0;
            int var6 = 0;

            if (var4 && isAvailable)
            {
                var5 += this.height;
            }
            if(isAvailable)
            {
            	var6 += isTwitterEnabled ? this.width : this.width * 2;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
        }
    }
    
    public void setTwitterEnabled(boolean b)
    {
    	isTwitterEnabled = b;
    }
}
