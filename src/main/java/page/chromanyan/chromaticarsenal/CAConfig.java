package page.chromanyan.chromaticarsenal;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CAConfig extends MidnightConfig {

    public static final String COMMON = "common";
    public static final String CLIENT = "client";

    @Entry(category = COMMON) public static boolean lootTableInsertion = true;
    @Entry(category = COMMON) public static List<String> chromaShardBlacklist = new ArrayList<>(List.of("dispenser", "trial_chambers"));

    @Entry(category = COMMON) public static int goldenHeartDuration = 400;
    @Entry(category = COMMON) public static int goldenHeartAmplifier = 1;

    @Entry(category = COMMON) public static int glassShieldCooldown = 400;
    @Entry(category = COMMON) public static int glassShieldMaxBaseDamage = 500;

    @Entry(category = COMMON) public static double wardCrystalIncomingMultiplier = 0.25;
    @Entry(category = COMMON) public static double wardCrystalOutgoingMultiplier = 0.25;

    @Entry(category = COMMON, min = 0, max = 15) public static int shadowTreadsMaxLightLevel = 7;
    @Entry(category = COMMON) public static double shadowTreadsSpeedModifier = 0.2;

    @Entry(category = COMMON) public static double dualityRingsDamageMultiplier = 1.25;
    @Entry(category = COMMON) public static int dualityRingsStrengthDuration = 2400;
    @Entry(category = COMMON) public static int dualityRingsStrengthAmplifier = 0;
    @Entry(category = COMMON) public static int dualityRingsHealthBoostDuration = 2400;
    @Entry(category = COMMON) public static int dualityRingsHealthBoostAmplifier = 2;

    @Entry(category = COMMON) public static int friendlyFireFlowerRecoveryTime = 60;
    @Entry(category = COMMON) public static int friendlyFireFlowerOverheatCooldown = 100;

    @Entry(category = COMMON) public static double lunarCrystalGravityModifier = -0.25;
    @Entry(category = COMMON) public static double lunarCrystalSafeFallDistanceModifier = 3;
    @Entry(category = COMMON) public static int lunarCrystalLevitationChance = 10;
    @Entry(category = COMMON) public static int lunarCrystalLevitationDuration = 60;
    @Entry(category = COMMON) public static int lunarCrystalLevitationAmplifier = 2;

    @Entry(category = COMMON) public static int cryoRingSnowballDamage = 2;
    @Entry(category = COMMON) public static int cryoRingVulnerableSnowballDamage = 5;
    @Entry(category = COMMON) public static int cryoRingFreezeTicks = 100;

    @Entry(category = COMMON) public static double bubbleAmuletSwimSpeedModifier = 0.3;

    @Entry(category = COMMON, min = 0, max = 1) public static double momentumStoneFrictionAddition = 0.3;
    @Entry(category = COMMON) public static boolean momentumStoneExtremelyUnbalancedMode = false;

    @Entry(category = COMMON) public static int advancingHeartHealthModifier = 20;
    @Entry(category = COMMON) public static List<String> advancingHeartAdvancementBlacklist = new ArrayList<>(List.of("cosmic_scroll"));

    @Entry(category = COMMON) public static int thunderchargedDuration = 60;
    @Entry(category = COMMON) public static double thunderguardZapDamage = 3;
    @Entry(category = COMMON) public static boolean thunderguardDefaultRecipe = true;

    @Entry(category = COMMON) public static int diamondHeartFracturedAmplifier = 2;
    @Entry(category = COMMON) public static int diamondHeartCooldownMinutes = 5;

    @Entry(category = COMMON) public static int undyingShieldDeathClockTicks = 200;

    @Entry(category = COMMON) public static double dispellingCrystalDurationMultiplier = 0.2;
    @Entry(category = COMMON) public static List<String> dispellingCrystalEffectBlacklist = new ArrayList<>(List.of("minecraft:night_vision"));

    @Entry(category = COMMON) public static double celestialCharmSpeedMultiplier = 0.3;
    @Entry(category = COMMON) public static double celestialCharmDamageMultiplier = 0.3;

    @Entry(category = COMMON) public static int infernoFlowerDuration = 100;
    @Entry(category = COMMON) public static float infernoFlowerDamageMultiplier = 1.1f;

    @Entry(category = COMMON) public static double prismaticCrystalGravityModifier = -0.5;
    @Entry(category = COMMON) public static double prismaticCrystalVoidBounceMultiplier = 2;
    @Entry(category = COMMON) public static float prismaticCrystalVoidBounceDamage = 4;

    @Entry(category = COMMON) public static int ascendedStarSlots = 1;
    @Entry(category = COMMON) public static int ascendedStarFortune = 1;
    @Entry(category = COMMON) public static int ascendedStarLooting = 1;
    @Entry(category = COMMON) public static float ascendedStarDamage = 3;

    @Entry(category = COMMON) public static double worldAnchorArmor = 4;

    @Entry(category = COMMON) public static int cursedTotemFracturedAmplifier = 4;
    @Entry(category = COMMON) public static int cursedTotemLootingBonus = 2;

    @Entry(category = COMMON) public static int harpyFeatherMaxJumps = 1;
    @Entry(category = COMMON) public static int superHarpyFeatherMaxJumps = 3;

    @Entry(category = COMMON) public static int illuminatedSoulGlowingDuration = 1200;
    @Entry(category = COMMON) public static float illuminatedSoulUndeadMultiplier = 1.2f;

    @Entry(category = COMMON, min = 0, max = 1) public static double copperRingUnbreakingChance = 0.1;
    @Entry(category = COMMON) public static int amethystRingReachModifier = 1;
    @Entry(category = COMMON) public static int vitalStoneFrequency = 100;

    @Entry(category = CLIENT) @Client public static int tooltipDecimalThreshold = 10;
    @Entry(category = CLIENT) @Client public static boolean anonymityOptOut = false;
}
