package cc.minetale.mlib.oldfabric;

import lombok.Getter;

@Getter
public class Fabric {

    private final FabricManager fabricManager;

    public Fabric() {
        this.fabricManager = new FabricManager();
        this.fabricManager.initialize();
    }

}
