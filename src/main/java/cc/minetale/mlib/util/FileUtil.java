package cc.minetale.mlib.util;

import net.minestom.server.extensions.Extension;

import java.io.File;

public class FileUtil {

    public static File getDataFolder(Extension extension, String suffix) {
        return new File("extensions" + File.separator + extension.getOrigin().getName() + File.separator + suffix);
    }

}
