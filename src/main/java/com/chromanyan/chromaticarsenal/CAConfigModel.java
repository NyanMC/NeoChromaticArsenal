package com.chromanyan.chromaticarsenal;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

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
    }

    public static class ClientOptions {
        public int tooltipDecimalThreshold = 10;
    }
}
