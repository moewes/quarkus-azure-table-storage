package net.moewes;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.RUN_TIME)
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
