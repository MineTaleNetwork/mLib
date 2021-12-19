package cc.minetale.mlib.hologram.component;

import cc.minetale.mlib.hologram.Hologram;
import cc.minetale.mlib.hologram.HologramEntity;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

@Getter @Setter
public abstract class HologramComponent {

    private Hologram parent;
    private HologramEntity entity;

    public abstract void create(Instance instance, Pos position);
    public abstract double getHeight();

}

