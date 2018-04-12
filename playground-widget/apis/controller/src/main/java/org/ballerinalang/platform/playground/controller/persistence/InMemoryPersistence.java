package org.ballerinalang.platform.playground.controller.persistence;

import org.ballerinalang.platform.playground.utils.MemberConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A mock implementation of the {@link Persistence} for development purposes.
 * Suitable for single node testing.
 */
public class InMemoryPersistence implements Persistence {

    private Map<String, String> members = new HashMap<>();

    @Override
    public void addFreeLaunchers(List<String> launcherUrls) {
        Map<String, String> launchers = launcherUrls.stream()
                .collect(Collectors.toMap((url) -> url, (url) -> MemberConstants.MEMBER_STATUS_FREE));
        members.putAll(launchers);
    }

    @Override
    public void addFreeLauncher(String launcherUrl) {
        members.put(launcherUrl, MemberConstants.MEMBER_STATUS_FREE);
    }

    @Override
    public void unregisterLauncher(String launcherUrl) {
        members.remove(launcherUrl);
    }

    @Override
    public List<String> getFreeLauncherUrls() {
        List<String> freeList = new ArrayList<>();
        for (Map.Entry<String, String> memberEntry : members.entrySet()) {
            if (memberEntry.getValue().equals(MemberConstants.MEMBER_STATUS_FREE)) {
                freeList.add(memberEntry.getKey());
            }
        }

        return freeList;
    }

    @Override
    public List<String> getBusyLauncherUrls() {
        List<String> busyList = new ArrayList<>();
        for (Map.Entry<String, String> memberEntry : members.entrySet()) {
            if (memberEntry.getValue().equals(MemberConstants.MEMBER_STATUS_BUSY)) {
                busyList.add(memberEntry.getKey());
            }
        }

        return busyList;
    }

    @Override
    public List<String> getTotalLauncherUrls() {
        return new ArrayList<>(members.values());
    }

    @Override
    public boolean markLauncherAsFree(String launcherUrl) {
        members.put(launcherUrl, MemberConstants.MEMBER_STATUS_FREE);
        return false;
    }

    @Override
    public boolean markLauncherAsBusy(String launcherUrl) {
        members.put(launcherUrl, MemberConstants.MEMBER_STATUS_BUSY);
        return false;
    }

    @Override
    public boolean launcherExists(String launcherUrl) {
        return members.containsKey(launcherUrl);
    }
}
