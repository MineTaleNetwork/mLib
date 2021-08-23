package cc.minetale.mlib.util;

import cc.minetale.commonlib.modules.profile.Profile;
import net.minestom.server.tag.TagReadable;
import net.minestom.server.tag.TagSerializer;
import net.minestom.server.tag.TagWritable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProfileTagSerializer implements TagSerializer<Profile> {

    @Override
    public @Nullable Profile read(@NotNull TagReadable tagReadable) {
        return null;
    }

    @Override
    public void write(@NotNull TagWritable tagWritable, @Nullable Profile profile) {}

}
