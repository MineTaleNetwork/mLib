package cc.minetale.mlib.hologram.component;

import cc.minetale.mlib.hologram.Hologram;
import cc.minetale.mlib.hologram.EntityHologram;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.Instance;

@Getter @Setter
public abstract class HologramComponent {

    private Hologram parent;
    private EntityHologram entity;

    public abstract void create(Instance instance, Pos position);
    public abstract double getHeight();
    public abstract Vec getDescendingOffset();
    public abstract Vec getAscendingOffset();

}

