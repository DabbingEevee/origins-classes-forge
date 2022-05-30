package limonblaze.originsclasses.mixin;

import limonblaze.originsclasses.util.PotionUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @Inject(method = "getOutput", at = @At("RETURN"), cancellable = true, remap = false)
    private static void originsClasses$handleAdditionalPotionNbt(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack output = cir.getReturnValue();
        if(output.getItem() instanceof PotionItem && input.hasTag()) {
            if(PotionUtils.hasPotionBonus(input)) {
                cir.setReturnValue(PotionUtils.addPotionBonus(output));
            }
        }
    }

}
