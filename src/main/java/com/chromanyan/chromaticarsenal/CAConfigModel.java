package com.chromanyan.chromaticarsenal;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.Sync;

@Config(name = "chromaticarsenal", wrapperName = "CAConfig")
public class CAConfigModel {
    @RangeConstraint(min = 1, max = Integer.MAX_VALUE)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public int goldenHeartDuration = 400;
    @RangeConstraint(min = 1, max = 255)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public int goldenHeartAmplifier = 1;
    @RangeConstraint(min = 1, max = Integer.MAX_VALUE)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public int glassShieldCooldown = 400;

    public int tooltipDecimalThreshold = 10;
}
