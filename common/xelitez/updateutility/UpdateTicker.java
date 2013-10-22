package xelitez.updateutility;

import java.util.EnumSet;

import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class UpdateTicker implements ITickHandler
{
	private static UpdateTicker instance = new UpdateTicker();
	
	public boolean drawMainMenuButton = true;
	public int mainMenuButtonMode = 0;
	
    private static final ResourceLocation texture = new ResourceLocation("uu:xelitez/updateutility/buttonrefresh.png");
	
	public static UpdateTicker getInstance()
	{
		return instance;
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.RENDER) && drawMainMenuButton)
		{
			if(FMLClientHandler.instance().getClient().currentScreen != null && FMLClientHandler.instance().getClient().currentScreen instanceof GuiMainMenu)
			{
				GuiMainMenu gui = (GuiMainMenu) FMLClientHandler.instance().getClient().currentScreen;
	            ScaledResolution var13 = new ScaledResolution(FMLClientHandler.instance().getClient().gameSettings, FMLClientHandler.instance().getClient().displayWidth, FMLClientHandler.instance().getClient().displayHeight);
	            int var14 = var13.getScaledWidth();
	            int var15 = var13.getScaledHeight();
	            int var16 = Mouse.getX() * var14 / FMLClientHandler.instance().getClient().displayWidth;
	            int var17 = var15 - Mouse.getY() * var15 / FMLClientHandler.instance().getClient().displayHeight - 1;
	            int width = 20;
	            int height = 20;
	            int xPosition = var13.getScaledWidth() / 2 + 104;
	            int yPosition = var13.getScaledHeight() / 4 + 96;
	            boolean var4 = var16 >= xPosition && var17 >= yPosition && var16 < xPosition + width && var17 < yPosition + height;
	            int var5 = 0;
	            int var6 = 20;
				switch(mainMenuButtonMode)
				{
				case 0:

					FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
					
		            if (var4)
		            {
		                var5 += height;
		            }
		            
		            if (UpdateRegistry.instance().getNumberOfModUpdatesAvailable() > 0)
		            {
		            	var6 += 20;
		            }
					gui.drawTexturedModalRect(xPosition, yPosition, var6, var5, width, height);
		            gui.drawCenteredString(FMLClientHandler.instance().getClient().fontRenderer, new StringBuilder().append(UpdateRegistry.instance().getNumberOfModUpdatesAvailable()).toString(), xPosition + 17 + width / 2, yPosition + (height - 8) / 2, 0xffffff);
					if(Mouse.isButtonDown(0) && var4)
					{
						FMLClientHandler.instance().getClient().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
						FMLClientHandler.instance().displayGuiScreen(FMLClientHandler.instance().getClient().thePlayer, new GuiUpdates(gui));
					}
					break;
				case 1:
					FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
					xPosition = var13.getScaledWidth() - 55;
					yPosition = 4;
					var4 = var16 >= xPosition && var17 >= yPosition && var16 < xPosition + width && var17 < yPosition + height;
					
		            if (var4)
		            {
		                var5 += height;
		            }
		            
		            if (UpdateRegistry.instance().getNumberOfModUpdatesAvailable() > 0)
		            {
		            	var6 += 20;
		            }
					gui.drawTexturedModalRect(xPosition, yPosition, var6, var5, width, height);
		            gui.drawCenteredString(FMLClientHandler.instance().getClient().fontRenderer, new StringBuilder().append(UpdateRegistry.instance().getNumberOfModUpdatesAvailable()).toString(), xPosition + 17 + width / 2, yPosition + (height - 8) / 2, 0xffffff);
					if(Mouse.isButtonDown(0) && var4)
					{
						FMLClientHandler.instance().getClient().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
						FMLClientHandler.instance().displayGuiScreen(FMLClientHandler.instance().getClient().thePlayer, new GuiUpdates(gui));
					}
					break;
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() 
	{
		return "UpdateTicker";
	}

}
