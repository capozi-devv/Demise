package net.capozi.demise.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import java.util.Collections;

public class PlayerRemainsEntity extends LivingEntity implements VehicleInventory {
    private static final int MAX_SIZE = 27*2; // Maximum inventory size
    private DefaultedList<ItemStack> inventory;
    private RegistryWrapper.WrapperLookup wrapper;

    public PlayerRemainsEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }
    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0f)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 100f)
                .add(EntityAttributes.FOLLOW_RANGE, 0f);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, this.getInventory(), wrapper);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, this.getInventory(), wrapper);
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if(source.getAttacker() instanceof PlayerEntity player) {
            if(player.isSneaking()) {
                if(getEntityWorld().isClient) return true;
                this.dropInventory();
                this.discard();
                return true;
            }
        }
        return false;
    }
    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ShieldItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof FishingRodItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CrossbowItem
        ){
            return ActionResult.PASS;
        }
        if (player.getWorld().isClient) {
            return ActionResult.PASS;
        }
        this.open(player);
        return ActionResult.SUCCESS; // Prevent further interaction processing
    }
    protected void dropInventory() {
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            ItemEntity itemEntity = new ItemEntity(getWorld(), getX(), getY(), getZ(), stack.copy());
            getWorld().spawnEntity(itemEntity);
        }
    }
    public void addInventoryStack(ItemStack stack) {
        if (stack.isEmpty()) return;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (inventory.get(i).isEmpty()) {
                setStack(i, stack);
                break;
            }
        }
    }
    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }
    @Override
    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }
    @Override
    public int size() {
        return MAX_SIZE;
    }
    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }
    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = inventory.get(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (stack.getCount() <= amount) {
            inventory.set(slot, ItemStack.EMPTY);
            return stack;
        } else {
            ItemStack result = stack.split(amount);
            if (stack.getCount() == 0) {
                inventory.set(slot, ItemStack.EMPTY);
            } else {
                inventory.set(slot, stack);
            }
            return result;
        }
    }
    @Override
    public ItemStack removeStack(int slot) {
        return inventory.set(slot, ItemStack.EMPTY);
    }
    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, this);
    }
    @Override
    public void clear() {
        inventory.clear();
    }
    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }
    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return new ItemStack(Items.AIR);
    }
    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }
    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public boolean isPushedByFluids() {
        return false;
    }
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    public @Nullable RegistryKey<LootTable> getLootTable() {
        return null;
    }

    @Override
    public void setLootTable(@Nullable RegistryKey<LootTable> lootTable) {

    }
    @Override
    public void setLootTableSeed(long lootTableSeed) {

    }
    @Override
    public void markDirty() {

    }
    //endregion
}
