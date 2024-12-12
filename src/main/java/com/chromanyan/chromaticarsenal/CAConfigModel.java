package com.chromanyan.chromaticarsenal;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@SuppressWarnings("unused")
@Config(name = "chromaticarsenal", wrapperName = "CAConfig")
public class CAConfigModel {

    @Nest
    @Expanded
    public CommonOptions COMMON = new CommonOptions();

    @Nest
    @Expanded
    public ClientOptions CLIENT = new ClientOptions();

    public static class CommonOptions {
        @RangeConstraint(min = 1, max = Integer.MAX_VALUE)
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int goldenHeartDuration = 400;
        @RangeConstraint(min = 1, max = 255)
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int goldenHeartAmplifier = 1;
        @RangeConstraint(min = 1, max = Integer.MAX_VALUE)
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public int glassShieldCooldown = 400;
    }

    public static class ClientOptions {
        public int tooltipDecimalThreshold = 10;
    }
}
