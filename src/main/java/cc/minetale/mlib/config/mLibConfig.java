package cc.minetale.mlib.config;

import lombok.Getter;

@Getter
public class mLibConfig {

    private String name = "Lobby-1";
    private String type = "Lobby";

    private int maxPlayers = 20;

    private String networkId = "minetale";

    private String mongoHost = "127.0.0.1";
    private Integer mongoPort = 27017;
    private String mongoDatabase = "MineTale";

    private String rabbitMqHost = "127.0.0.1";
    private Integer rabbitMqPort = 5672;

}
