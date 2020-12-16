package fr.raksrinana.fallingtree;

import fr.raksrinana.fallingtree.config.BreakMode;
import fr.raksrinana.fallingtree.tree.breaking.ITreeBreakingHandler;
import fr.raksrinana.fallingtree.tree.breaking.InstantaneousTreeBreakingHandler;
import fr.raksrinana.fallingtree.tree.breaking.ShiftDownTreeBreakingHandler;
import fr.raksrinana.fallingtree.tree.builder.TreeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import static fr.raksrinana.fallingtree.config.BreakMode.INSTANTANEOUS;
import static fr.raksrinana.fallingtree.utils.FallingTreeUtils.isPlayerInRightState;

public class BlockBreakHandler implements net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.Before{
	@Override
	public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity){
		if(FallingTree.config.getTreesConfiguration().isTreeBreaking() && !world.isClient()){
			if(isPlayerInRightState(player)){
				return TreeBuilder.getTree(world, blockPos).map(tree -> {
					BreakMode breakMode = FallingTree.config.getTreesConfiguration().getBreakMode();
					return getBreakingHandler(breakMode).breakTree(player, tree);
				}).orElse(true);
			}
		}
		return true;
	}
	
	public static ITreeBreakingHandler getBreakingHandler(BreakMode breakMode){
		if(breakMode == INSTANTANEOUS){
			return InstantaneousTreeBreakingHandler.getInstance();
		}
		return ShiftDownTreeBreakingHandler.getInstance();
	}
}