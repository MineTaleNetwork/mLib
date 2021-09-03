package cc.minetale.mlib.fabric;

import lombok.Getter;

@Getter
public class Fabric {

    private final FabricManager fabricManager;

    public Fabric() {
        this.fabricManager = new FabricManager();
        this.fabricManager.initialize();
    }

}
