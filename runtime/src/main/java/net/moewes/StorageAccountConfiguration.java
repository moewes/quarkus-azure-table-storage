package net.moewes;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class StorageAccountConfiguration {

    /**
     * account name
     */
    @ConfigItem()
    String accountName;

    /**
     * account key
     */
    @ConfigItem()
    String accountKey;
}
