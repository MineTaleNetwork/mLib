package cc.minetale.mlib.canvas.template;

import cc.minetale.mlib.canvas.CanvasType;
import cc.minetale.mlib.canvas.Filler;
import cc.minetale.mlib.canvas.Fragment;
import cc.minetale.mlib.util.SoundsUtil;
import cc.minetale.sodium.util.Message;
import cc.minetale.sodium.util.Pagination;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.UUID;

@Getter @Setter
public abstract class PaginatedMenu extends Menu {

    private Pagination<Fragment> pagination;
    private Menu previousPage;

    public PaginatedMenu(Player player, Component title, CanvasType type) {
        super(player, title, type);
    }

    @Override
    public void initialize() {
        var type = getType();
        var player = getPlayer();

        if(type.getSize() < 27) {
            type = CanvasType.THREE_ROW;
        }

        var size = type.getSize();

        pagination = new Pagination<>((size / 9 - 2) * 7, getPaginatedFragments(player));

        setFiller(Filler.BORDER);

        setButton(size - 6, Fragment.of(ItemStack.of(Material.PLAYER_HEAD)
                        .withMeta(PlayerHeadMeta.class, meta -> {
                            meta.skullOwner(UUID.randomUUID());
                            meta.playerSkin(new PlayerSkin(
                                    "ewogICJ0aW1lc3RhbXAiIDogMTYxMzkwOTY3MjQzOCwKICAicHJvZmlsZUlkIiA6ICJiN2ZkYmU2N2NkMDA0NjgzYjlmYTllM2UxNzczODI1NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzYyZmZhOTg0MTVmZTVlMDE4MDE0NzIwNGI2MTg0YmQ3OWMxNjM2ODYyYjI2Nzc0MTM1YjM0NWFhNmYxNDU0NzkiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
                                    "iI7fWrziHRSp6oSaOZpNKEqDyRhggQof/qlAplCUtsrUMrLHrZHwRabg8KLtdHJPFfeFv9ZsF1lHBrOcVmsPZm2fyYVKnNnx5ld0Ub9aWA4AVkshfQrEd7D8gnXrwLeZSx9wU7xYhkfj8DSXHZEwY7aStQYYj71CkUvpoAwebVV6/5U4fFox2rrLGLrl5c/oQ1HOqjXUKpU7Ae6oWr7yNuZcChnR0bWRa7qh3++dI6TU2yL0XROUBKNM86tkelRO+eJCi2YeMO059btgyWXrhojq4ezOwJr/X5Lg3SD13qMCeis7Id4gKesK9HpxbGgLLJkJyGj2XyWy/GSP2auKmz/tVmaubwZrvQwWYYxyk0rbfSBkUK30HvH0ZKuDybTUfkOyAP1hzLuDZPGyu1vW2bXb905o5CkA300WhLSJoPIoxNJu2Q6W1xhdr0HeYnqzhrhFrHn8YxjhiPXswVS40FV0f7Ienn021qaQr47Jm44wAgmCnU6jbuJsqbQ6w0gu3PomqU6+YL4Dy2hcdQYXAkN8uiuadIeOPDIc0mIK9G2OPHBElKd0Lyr81q+44Z4/blRxFUBvx40TvJ9VoBXS73S9aMfWekma/S06bhN2c+FadLxOqgGkyy792TPLuiT2bVFC7kPNfNrpd4KMlZsXrOkEMVwv1URW1u3DQ8PVyKg="
                            ));
                        })
                .withDisplayName(Component.text("\u2190 Previous Page", Message.style(NamedTextColor.GRAY))), event -> {
            if(pagination.previousPage()) {
                SoundsUtil.playClickSound(player);
                setPaginatedItems();
            } else {
                SoundsUtil.playErrorSound(player);
            }
        }));

        setButton(size - 5, Fragment.of(ItemStack.of(Material.PLAYER_HEAD)
                .withMeta(PlayerHeadMeta.class, meta -> {
                    meta.skullOwner(UUID.randomUUID());
                    meta.playerSkin(new PlayerSkin(
                            "ewogICJ0aW1lc3RhbXAiIDogMTYxMzkwOTcwMjkxNSwKICAicHJvZmlsZUlkIiA6ICJiMGQ3MzJmZTAwZjc0MDdlOWU3Zjc0NjMwMWNkOThjYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJPUHBscyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85YTEwMDhmYjk5NWQyMWI3MjRjOWI3NWY4ZmE3NjRlNmQ4NmU0ZTc2NWQzYTA4MTFjZGI4NmU2YjgwNTYzYzljIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                            "hbGEbuqvwSIvFjoFFybaXk5rvhzVFkKd+zyfCTNf3G8Fde6qRyK+NxkB7JMBAKcInXCV5+Xq9x00lU5IlVmw3FcsJpwFAsfnawdv8HOwumSjw1yndOGs88zMrZKqNmgUUa831r+BA4EQGpjvMSLJuIKwaAhN/S/mrMLIcDNVt3++AhyFGh23DKJxjcjBq1lOX83fMwX/RRumLN25mZBKGz1nqMCt9aR45biMpwOmpHL9ajdtQz2Dx7F863Ep06jiSPKIoF9xhbHvE3qFhBGAEgmOXmpqFrmfxxW/RMDG/E9gVh7gQHBzOKPKMUkBIrmFdxhfyBOgEwJIWEvrKVTIN6W+AzSo1ZDfn5D7QDxuWZ3+99hy50/Kl1GpARefbsxP+kMSSMsRym+cPg3035xJRXdbwE+8uIG21tar+E5+Vseyrt9Zy23naipLBGCZzimpzuaYu+pPgOZ5THKNj8Y/hIYXXjqLKVQBieujzKFo6KDVsGXJPp/F6AtSaGzUGWGTtvUOkqcgtED++SutKv/q3JWWT+4Hf6wHSyxSXGQ5MyIjQVQ3C43MbgzpBWL1R3nZSVe4hjo9ouIYi2UvCFAFwpM/PtM4nk0DnFMBC7JMZ4kJM/uOBV74t2S4T0p8f0XqqCLPyGkxcsOkjm2GWaVYySQw39h9XpxcuhMUqvZAocA="
                    ));
                })
                .withDisplayName(Component.text("\u2193 " + (previousPage != null ? "Back" : "Exit") + " \u2193", Message.style(NamedTextColor.GRAY))), event -> {
            handleClose(player);

            if(previousPage != null) {
                openMenu(previousPage);
            }

            SoundsUtil.playClickSound(player);
        }));

        setButton(size - 4, Fragment.of(ItemStack.of(Material.PLAYER_HEAD)
                        .withMeta(PlayerHeadMeta.class, meta -> {
                            meta.skullOwner(UUID.randomUUID());
                            meta.playerSkin(new PlayerSkin(
                                    "ewogICJ0aW1lc3RhbXAiIDogMTYxMzkwOTYxNDI1NiwKICAicHJvZmlsZUlkIiA6ICIzM2ViZDMyYmIzMzk0YWQ5YWM2NzBjOTZjNTQ5YmE3ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEYW5ub0JhbmFubm9YRCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83MmY4ODMzNTE5NWQ5OTk0ZDcxMWI5MDBkZTI1MWM5ZTU5YmE1OTZmZTY0NDFkZjQwOWEwMjhmYWQyMzJiNWY2IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                                    "yCwcbhvE7exsj/12MMtPlfH4C1aHXgRpZgGAV3jY1eR5QKMwiiRNcM63UVX1RROtWbZA1lqXWoTFg+jWT5CDN/C993Rh3F7Uo58uDSuvgvV2V+OkRBozfsR7thmzViT/peBTEFjROzTKiIipIeJBuQAoLlM2WJhXAG6vfmOYZwqEdoU0JNPFE8FdVkTR4qt8Fp3QBpou81azfgd5MFO31TWZRSwrz9oX1B/rDf4sKAhL3jNesT8Hk/iqRU3T4D4fw8g7kxus88nWqMtb/eHOGRvyKq2Au1XJ6CXiPwBuDZ6dR46quETBp+yu8DUlROvIlI3GYo58cJEpgWbIHYDniVUmy54UiN4mn8ymWLGZB2rTLi7z2Jbpkq1GXa7BOF6VW7J7ovev7dbV1E1y/yLSAkrcXNae/jvujw2JKvCjRjnq3g3pZJ6HF1VsaM8gUsgFv4XHvUZQ0zy3fi4wShJY6mSp1bzC49htpkT6qHCxe+XfS99Yys55Or4WRWkox7bdZs7sWt+AfBJBUJj4MTSMns0/SwYkFWTx8Qt7N+kkcsKLmP+xxkCJs/ZeS9O2BGR1/ssczgil+P/CP2uixzrZTsLlrSr3ovElppYou94goVXwhWVO9QJEkGaa25tIbxJzBbDu9exly3GPG+++LQNLl0JlSI/G2nRBD/xbw/cVsTw="
                            ));
                        })
                        .withDisplayName(Component.text("Next Page \u2192", Message.style(NamedTextColor.GRAY))),
                event -> {
                    if(pagination.nextPage()) {
                        SoundsUtil.playClickSound(player);
                        setPaginatedItems();
                    } else {
                        SoundsUtil.playErrorSound(player);
                    }
                }));

        setPaginatedItems();
    }

    public void setPaginatedItems() {
        var fragments = pagination.getPageItems();

        var size = getType().getSize();

        for (int index = 0, fragmentIndex = 0; index < size; index++) {
            if (index > 9 && index <= size - 9 && index % 9 != 0 && index % 9 != 8) {
                if(fragmentIndex < fragments.length && fragments[fragmentIndex] != null) {
                    setButton(index, fragments[fragmentIndex]);
                    fragmentIndex++;
                }
            }
        }
    }

    public abstract Fragment[] getPaginatedFragments(Player player);

}
