package xelitez.updateutility;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class UpdateEventListener
{
	public static final UpdateEventListener instance = new UpdateEventListener();
	public KeyBinding button = new KeyBinding("Open update GUI", Keyboard.KEY_F4, "XEliteZ Mod Update Utility");
	
	public boolean drawMainMenuButton = true;
	public int mainMenuButtonMode = 0;
	
    private static final ResourceLocation texture = new ResourceLocation("uu:xelitez/updateutility/buttonrefresh.png");
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent evt) 
	{
		if(evt.phase == TickEvent.Phase.END && FMLClientHandler.instance().getClient().currentScreen instanceof GuiMainMenu && drawMainMenuButton )
		{
			GuiMainMenu gui = (GuiMainMenu) FMLClientHandler.instance().getClient().currentScreen;
			ScaledResolution var13 = new ScaledResolution(FMLClientHandler.instance().getClient(), FMLClientHandler.instance().getClient().displayWidth, FMLClientHandler.instance().getClient().displayHeight);
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
					FMLClientHandler.instance().getClient().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
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
					FMLClientHandler.instance().getClient().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					FMLClientHandler.instance().displayGuiScreen(FMLClientHandler.instance().getClient().thePlayer, new GuiUpdates(gui));
				}
				break;
			}
		}
	}	

	@SubscribeEvent
	public void onConnect(PlayerEvent.PlayerLoggedInEvent evt) 
	{
		if(UpdateRegistry.instance().getNumberOfModUpdatesAvailable() > 0)
		{
			evt.player.addChatMessage(new ChatComponentText("[XEZUpdateUtility] \u00a7eOne or more mods appear to have updates. go back to the main menu to check which mods or press \u00a7f" + Keyboard.getKeyName(button.getKeyCode()) + "\u00a7e to open the ingame-GUI."));
		}
	}
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent evt) 
	{
		if(GameSettings.isKeyDown(button))
		{
			GuiScreen currentscreen = FMLClientHandler.instance().getClient().currentScreen;
	        if (!(currentscreen instanceof GuiControls) && !(currentscreen instanceof GuiUpdates))
	        {
	        	FMLClientHandler.instance().displayGuiScreen(FMLClientHandler.instance().getClient().thePlayer, new GuiUpdates(currentscreen));
	        }
		}
	}
}
