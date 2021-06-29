package fr.raksrinana.fallingtree.forge.config;

import fr.raksrinana.fallingtree.forge.tree.TreePart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum BreakOrder{
	FURTHEST_FIRST(Comparator.comparingInt(TreePart::sequence).reversed()),
	CLOSEST_FIRST(Comparator.comparingInt(TreePart::sequence));
	
	private final Comparator<? super TreePart> comparator;
}
