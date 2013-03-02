package xelitez.updateutility;

import java.util.EnumSet;

import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class UpdateKeyHandler extends KeyHandler
{
	private GuiScreen currentscreen;
    private static UpdateKeyHandler INSTANCE = new UpdateKeyHandler();
	
	public UpdateKeyHandler()
	{
        super(new KeyBinding[] {new KeyBinding("Open update GUI", Keyboard.KEY_F4)}, new boolean[] {false});
	}

	@Override
	public String getLabel() 
	{
		return "xelitez.updatekeyhandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) 
	{
		
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd) 
	{
		currentscreen = FMLClientHandler.instance().getClient().currentScreen;
        if (kb == this.keyBindings[0] && !(currentscreen instanceof GuiControls) && !(currentscreen instanceof GuiUpdates))
        {
        	FMLClientHandler.instance().displayGuiScreen(FMLClientHandler.instance().getClient().thePlayer, new GuiUpdates(currentscreen));
        }
	}
	
    public String getKey(int i)
    {
        return keyBindings[i].keyDescription;
    }
	
	public static UpdateKeyHandler instance()
	{
		return INSTANCE;
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.CLIENT, TickType.GUI);
	}
}
