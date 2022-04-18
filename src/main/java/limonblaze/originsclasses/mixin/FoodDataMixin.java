package limonblaze.originsclasses.mixin;

import limonblaze.originsclasses.util.NbtType;
import limonblaze.originsclasses.util.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Shadow public abstract void eat(int food, float saturationModifier);

    @Inject(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("TAIL"), remap = false)
    private void originsClasses$handleFoodBonus(Item pItem, ItemStack pStack, LivingEntity entity, CallbackInfo ci) {
        FoodProperties food = pStack.getFoodProperties(entity);
        if(food != null) {
            NbtUtils.getOriginsClassesData(pStack, NbtUtils.FOOD_BONUS, NbtType.FLOAT).ifPresent(f ->
                this.eat(Mth.floor(food.getNutrition() * f), food.getSaturationModifier()));
        }
    }

}
