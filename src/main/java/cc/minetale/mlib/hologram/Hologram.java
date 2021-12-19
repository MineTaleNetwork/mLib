package cc.minetale.mlib.hologram;

import cc.minetale.mlib.hologram.component.HologramComponent;
import lombok.Getter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Hologram {

    private final CopyOnWriteArrayList<HologramComponent> children;
    public Instance instance;
    public Pos position;
    public HologramDirection direction;

    public Hologram(Instance instance, Pos position) {
        this(instance, position, HologramDirection.DESCENDING);
    }

    public Hologram(Instance instance, Pos position, HologramDirection direction) {
        this.children = new CopyOnWriteArrayList<>();

        this.instance = instance;
        this.position = position;
        this.direction = direction;
    }

    public Hologram append(HologramComponent component) {
        component.setParent(this);
        this.children.add(component);
        return this;
    }

    public void create() {
        Pos origin = this.position;

        switch (this.direction) {
            case ASCENDING -> {
                for(HologramComponent component : this.children) {
                    component.create(this.instance, origin);
                    origin = origin.sub(0, component.getHeight(), 0);
                }
            }
            case DESCENDING -> {
                for(var i = this.children.size() - 1; i >= 0; i--) {
                    var component = this.children.get(i);
                    component.create(this.instance, origin);
                    origin = origin.add(0, component.getHeight(), 0);
                }
            }
        }
    }

}
