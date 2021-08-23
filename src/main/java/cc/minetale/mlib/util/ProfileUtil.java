package cc.minetale.mlib.util;

import cc.minetale.mlib.event.AsyncProfileLoadEvent;
import cc.minetale.commonlib.modules.profile.Profile;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ProfileUtil {
    private ProfileUtil() {}

    @Getter private static String TAG_KEY = "profile";

    /**
     * Gets player's profile from their metadata. <br>
     * If it's not found, it loads the profile externally (unless {@code onlyMetadata} is {@code true}). <br>
     * <br>
     * Optionally it can automatically associate externally loaded profile with the player if {@code autoAssociate} is {@code true} (Uses {@linkplain ProfileUtil#associateProfile(Player, Profile)}) internally). <br>
     * <br>
     * For synchronous operations it is generally recommended to set {@code onlyMetadata} to {@code true} and use {@linkplain CompletableFuture#getNow(Object)}.
     * @param player The player to look for a profile for.
     * @param onlyMetadata If set to {@code true}, it skips trying to load the profile externally. <br>
     *                     {@linkplain CompletableFuture} returns {@code null} if it can't find a profile in the metadata. <br>
     *                     <br>
     *                     This comes with a downside of the profile being null if it hasn't been associated yet, <br>
     *                     though a chance of that happening is <strong>very</strong> slim due to the profile being associated when a player joins.
     * @param autoAssociate Automatically associate an externally loaded profile to the player (as metadata).
     *                      {@code onlyMetadata} needs to be set to {@code false} for this to take effect.
     */
    public static CompletableFuture<Profile> getAssociatedProfile(Player player, boolean onlyMetadata, boolean autoAssociate) {
        Profile profile = player.getTag(Tag.Structure(TAG_KEY, new ProfileTagSerializer()));

        if(profile == null && !onlyMetadata) {
            var future = Profile.getProfile(player.getUsername(), player.getUuid());

            future.thenAccept(loadedProfile -> {
                AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(loadedProfile);
                EventDispatcher.call(asyncProfileLoad);

                if(autoAssociate)
                    associateProfile(player, loadedProfile);
            });

            return future;
        }

        return CompletableFuture.completedFuture(profile);
    }

    /**
     * Calls {@linkplain ProfileUtil#getAssociatedProfile(Player, boolean, boolean)} with two last parameters being {@code false} and {@code true}. <br>
     * These are considered the default settings for the aforementioned function. <br>
     * <br>
     * If you wish to use a {@linkplain CompletableFuture#getNow(Object)} instantaneously inside synchronous code, <br>
     * see the documentation for {@linkplain ProfileUtil#getAssociatedProfile(Player, boolean, boolean)}.
     */
    public static CompletableFuture<Profile> getAssociatedProfile(Player player) {
        return getAssociatedProfile(player, false, true);
    }

    public static CompletableFuture<Map<Player, Profile>> getAssociatedProfiles(List<Player> players, boolean onlyMetadata, boolean autoAssociate) {
        List<Player> cacheRequired = new ArrayList<>();
        Map<Player, Profile> profiles = new HashMap<>();

        for(Player player : players) {
            Profile profile = player.getTag(Tag.Structure(TAG_KEY, new ProfileTagSerializer()));

            if(profile != null) {
                profiles.put(player, profile);
            } else if(!onlyMetadata) {
                cacheRequired.add(player);
            }
        }

        if(cacheRequired.isEmpty())
            return CompletableFuture.completedFuture(profiles);

        CompletableFuture<Map<Player, Profile>> future = new CompletableFuture<>();

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            var profileFuture = Profile.getProfiles(PlayerUtil.getAll(cacheRequired));

            profileFuture.thenAccept(loadedProfiles -> {
                Map<Player, Profile> loadedPlayers = new HashMap<>();

                for(Profile loadedProfile : loadedProfiles) {
                    AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(loadedProfile);
                    EventDispatcher.call(asyncProfileLoad);

                    var player = getPlayerFromProfile(cacheRequired, loadedProfile);
                    if(autoAssociate)
                        associateProfile(player, loadedProfile);

                    loadedPlayers.put(player, loadedProfile);
                }

                future.complete(loadedPlayers);
            });
        }).schedule();

        return future;
    }

    public static CompletableFuture<Map<Player, Profile>> getAssociatedProfiles(List<Player> players) {
        return getAssociatedProfiles(players, false, true);
    }

    public static CompletableFuture<Profile> getProfileByBoth(String name, UUID uuid, boolean onlyMetadata, boolean autoAssociate) {
        var player = MinecraftServer.getConnectionManager().getPlayer(uuid);
        if(player != null) {
            return getAssociatedProfile(player, onlyMetadata, autoAssociate);
        } else {
            var future = Profile.getProfile(name, uuid);
            future.thenAccept(profile -> {
                AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(profile);
                EventDispatcher.call(asyncProfileLoad);
            });
            return future;
        }
    }

    public static CompletableFuture<Profile> getProfileByBoth(String name, UUID uuid) {
        return getProfileByBoth(name, uuid, false, true);
    }

    public static CompletableFuture<List<Profile>> getProfilesByAny(List<String> names, List<UUID> uuids, boolean onlyMetadata, boolean autoAssociate) {
        List<CompletableFuture<Profile>> futures = new ArrayList<>();

        for(String name : names) {
            var player = MinecraftServer.getConnectionManager().getPlayer(name);
            if(player != null) {
                futures.add(getAssociatedProfile(player, onlyMetadata, autoAssociate));
            } else {
                var future = Profile.getProfile(name);
                future.thenAccept(profile -> {
                    AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(profile);
                    EventDispatcher.call(asyncProfileLoad);
                });
                futures.add(future);
            }
        }

        for(UUID uuid : uuids) {
            var player = MinecraftServer.getConnectionManager().getPlayer(uuid);
            if(player != null) {
                futures.add(getAssociatedProfile(player, onlyMetadata, autoAssociate));
            } else {
                var future = Profile.getProfile(uuid);
                future.thenAccept(profile -> {
                    AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(profile);
                    EventDispatcher.call(asyncProfileLoad);
                });
                futures.add(future);
            }
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(voidd -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    public static CompletableFuture<List<Profile>> getProfilesByAny(List<String> names, List<UUID> uuids) {
        return getProfilesByAny(names, uuids, false, true);
    }

    public static CompletableFuture<Profile> getProfileById(UUID uuid, boolean onlyMetadata, boolean autoAssociate) {
        Player player = MinecraftServer.getConnectionManager().getPlayer(uuid);
        if(player != null) {
            return getAssociatedProfile(player, onlyMetadata, autoAssociate);
        } else {
            var future = Profile.getProfile(uuid);
            future.thenAccept(profile -> {
                AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(profile);
                EventDispatcher.call(asyncProfileLoad);
            });
            return future;
        }
    }

    public static CompletableFuture<Profile> getProfileById(UUID uuid) {
        return getProfileById(uuid, false, true);
    }

    public static CompletableFuture<Profile> getProfileByName(String name, boolean onlyMetadata, boolean autoAssociate) {
        Player player = MinecraftServer.getConnectionManager().getPlayer(name);
        if(player != null) {
            return getAssociatedProfile(player, onlyMetadata, autoAssociate);
        } else {
            var future = Profile.getProfile(name);
            future.thenAccept(profile -> {
                AsyncProfileLoadEvent asyncProfileLoad = new AsyncProfileLoadEvent(profile);
                EventDispatcher.call(asyncProfileLoad);
            });
            return future;
        }
    }

    public static CompletableFuture<Profile> getProfileByName(String name) {
        return getProfileByName(name, false, true);
    }

    public static CompletableFuture<List<Profile>> getProfilesByNames(List<String> names, boolean onlyMetadata, boolean autoAssociate) {
        return getProfilesByAny(names, new ArrayList<>(), onlyMetadata, autoAssociate);
    }

    public static CompletableFuture<List<Profile>> getProfilesByNames(List<String> names) {
        return getProfilesByNames(names, false, true);
    }

    public static CompletableFuture<List<Profile>> getProfilesByIds(List<UUID> ids, boolean onlyMetadata, boolean autoAssociate) {
        return getProfilesByAny(new ArrayList<>(), ids, onlyMetadata, autoAssociate);
    }

    public static CompletableFuture<List<Profile>> getProfilesByIds(List<UUID> ids) {
        return getProfilesByIds(ids, false, true);
    }

    /**
     * Associates a profile to a player (assigns the profile as player's metadata).
     */
    public static void associateProfile(Player player, Profile profile) {
        player.setTag(Tag.Structure(TAG_KEY, new ProfileTagSerializer()), profile);
    }

    public static boolean isAnyProfileAssociated(Player player) {
        return player.hasTag(Tag.Structure(TAG_KEY, new ProfileTagSerializer()));
    }

    public static Player getPlayerFromProfile(@Nullable Collection<? extends Player> players,
                                              final Profile profile) {

        players = Objects.requireNonNullElse(players, MinecraftServer.getConnectionManager().getOnlinePlayers());

        return players.stream()
                .filter(player -> player.getUuid().equals(profile.getId()))
                .findFirst().orElse(null);
    }
}
