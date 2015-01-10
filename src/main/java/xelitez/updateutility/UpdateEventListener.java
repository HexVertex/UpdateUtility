package xelitez.updateutility;

import java.util.List;
import java.util.Map.Entry;

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

import xelitez.updateutility.UpdateRegistry.ModInstance;
import xelitez.updateutility.twitter.Tweet;
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
			evt.player.addChatMessage(new ChatComponentText("<\u00a73XEZUpdateUtility\u00a7r> \u00a7c" + UpdateRegistry.instance().getNumberOfModUpdatesAvailable() + "\u00a7e mod" + (UpdateRegistry.instance().getNumberOfModUpdatesAvailable() != 1 ? "s" : "") + " \u00a7eappears \u00a7eto \u00a7ehave \u00a7ean \u00a7eupdate. \u00a7eGo \u00a7eback \u00a7eto \u00a7ethe \u00a7emain \u00a7emenu \u00a7eto \u00a7echeck \u00a7ewhich \u00a7emod" + (UpdateRegistry.instance().getNumberOfModUpdatesAvailable() != 1 ? "s" : "") +  " \u00a7eor \u00a7epress \u00a7f" + Keyboard.getKeyName(button.getKeyCode()) + " \u00a7eto \u00a7eopen \u00a7ethe \u00a7eingame-GUI."));
		}
		if(UpdateRegistry.instance().tweets.size() > 0)
		{
			for(Entry<ModInstance, List<Tweet>> entry : UpdateRegistry.instance().tweets.entrySet())
			{
				List<Tweet> tweetList = entry.getValue();
				for(Tweet tweet : tweetList)
				{
					if(tweet != null && tweet.text != null && tweet.text.contains(entry.getKey().update.getTInstance().filterString))
					{
						evt.player.addChatMessage(new ChatComponentText("<\u00a73UUTwitter \u00a73Plug-In\u00a7r> \u00a7o@" + entry.getKey().update.getTInstance().displayName + "\u00a7r: " + tweet.text));
					}
				}
			}
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
