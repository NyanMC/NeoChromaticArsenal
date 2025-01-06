package com.chromanyan.chromaticarsenal;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Config(name = ChromaticArsenal.MODID, wrapperName = "CAConfig")
@Modmenu(modId = ChromaticArsenal.MODID)
public class CAConfigModel {

    @Nest
    @Expanded
    public CommonOptions COMMON = new CommonOptions();

    @Nest
    @Expanded
    public ClientOptions CLIENT = new ClientOptions();

    public static class CommonOptions {
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int goldenHeartDuration = 400;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int goldenHeartAmplifier = 1;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int glassShieldCooldown = 400;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double wardCrystalIncomingMultiplier = 0.25;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double wardCrystalOutgoingMultiplier = 0.25;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        @RangeConstraint(min = 0, max = 15)
        public int shadowTreadsMaxLightLevel = 7;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double shadowTreadsSpeedModifier = 0.2;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double dualityRingsDamageMultiplier = 1.25;
        public int dualityRingsStrengthDuration = 2400;
        public int dualityRingsStrengthAmplifier = 0;
        public int dualityRingsHealthBoostDuration = 2400;
        public int dualityRingsHealthBoostAmplifier = 2;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int friendlyFireFlowerRecoveryTime = 60;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int friendlyFireFlowerOverheatCooldown = 100;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double lunarCrystalGravityModifier = -0.25;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double lunarCrystalSafeFallDistanceModifier = 3;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int lunarCrystalLevitationChance = 10;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int lunarCrystalLevitationDuration = 60;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int lunarCrystalLevitationAmplifier = 2;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int cryoRingSnowballDamage = 2;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int cryoRingVulnerableSnowballDamage = 5;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int cryoRingFreezeTicks = 100;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double bubbleAmuletSwimSpeedModifier = 0.3;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        @RangeConstraint(min = 0, max = 1)
        public double momentumStoneFrictionAddition = 0.3;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean momentumStoneExtremelyUnbalancedMode = false;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int advancingHeartHealthModifier = 20;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public List<String> advancingHeartAdvancementBlacklist = new ArrayList<>(List.of("cosmic_scroll"));

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int thunderchargedDuration = 60;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double thunderguardZapDamage = 3;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean thunderguardDefaultRecipe = true;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int diamondHeartFracturedAmplifier = 2;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int diamondHeartCooldownMinutes = 5;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int undyingShieldDeathClockTicks = 200;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double dispellingCrystalDurationMultiplier = 0.2;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public List<String> dispellingCrystalEffectBlacklist = new ArrayList<>(List.of("minecraft:night_vision"));

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double celestialCharmSpeedMultiplier = 0.3;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double celestialCharmDamageMultiplier = 0.3;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int infernoFlowerDuration = 100;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public float infernoFlowerDamageMultiplier = 1.1f;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double prismaticCrystalGravityModifier = -0.5;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public double prismaticCrystalVoidBounceMultiplier = 2;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public float prismaticCrystalVoidBounceDamage = 4;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int ascendedStarSlots = 1;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int ascendedStarFortune = 1;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int ascendedStarLooting = 1;
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public float ascendedStarDamage = 3;
    }

    public static class ClientOptions {
        public int tooltipDecimalThreshold = 10;
    }
}
