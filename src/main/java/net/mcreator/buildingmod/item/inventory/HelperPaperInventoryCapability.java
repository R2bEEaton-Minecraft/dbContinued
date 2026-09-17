package net.mcreator.buildingmod.item.inventory;

import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponents;

import net.mcreator.buildingmod.world.inventory.HelpPaperMenu;
import net.mcreator.buildingmod.init.DavebuildingmodModItems;

import javax.annotation.Nonnull;

@EventBusSubscriber
public class HelperPaperInventoryCapability extends ComponentItemHandler {
	@SubscribeEvent
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == DavebuildingmodModItems.HELPER_PAPER.get()) {
			Player player = event.getPlayer();
			if (player.containerMenu instanceof HelpPaperMenu)
				player.closeContainer();
		}
	}

	public HelperPaperInventoryCapability(MutableDataComponentHolder parent) {
		super(parent, DataComponents.CONTAINER, 0);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return stack.getItem() != DavebuildingmodModItems.HELPER_PAPER.get();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return super.getStackInSlot(slot).copy();
	}
}