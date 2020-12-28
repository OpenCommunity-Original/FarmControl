package com.froobworld.farmcontrol.metrics;

import com.froobworld.farmcontrol.FarmControl;
import com.froobworld.farmcontrol.controller.ActionProfile;
import com.froobworld.farmcontrol.controller.action.Action;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class FcMetrics {
    private final FarmControl farmControl;
    private final Metrics metrics;

    public FcMetrics(FarmControl farmControl, int pluginId) {
        this.farmControl = farmControl;
        metrics = new Metrics(farmControl, pluginId);
        addCustomMetrics();
    }

    private void addCustomMetrics() {
        metrics.addCustomChart(new Metrics.AdvancedPie("actions_in_use", () -> {
            Map<String, Integer> actionCountMap = new HashMap<>();
            for (World world : Bukkit.getWorlds()) {
                for (String profileName : farmControl.getFcConfig().worldSettings.of(world).profiles.get()) {
                    ActionProfile actionProfile = farmControl.getProfileManager().getActionProfile(null, profileName);
                    if (actionProfile != null) {
                        for (Action action : actionProfile.getActions()) {
                            actionCountMap.put(action.getName(), 1);
                        }
                    }
                }
            }
            return actionCountMap;
        }));
    }

}