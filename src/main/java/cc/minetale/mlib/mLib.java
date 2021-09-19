package cc.minetale.mlib;

import cc.minetale.commonlib.CommonLib;
import cc.minetale.mlib.config.mLibConfig;
import cc.minetale.mlib.fabric.Fabric;
import cc.minetale.mlib.npc.NPC;
import cc.minetale.mlib.npc.NPCInteraction;
import cc.minetale.mlib.util.FileUtil;
import cc.minetale.pigeon.Pigeon;
import cc.minetale.quartz.config.ConfigLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter
public class mLib extends Extension {

    @Getter private static mLib mLib;
    @Getter private static Team npcTeam;
    private Gson gson;
    private mLibConfig config;
    private Pigeon pigeon;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private CommonLib commonLib;
    private Fabric fabric;

    @Override
    public void initialize() {
        mLib = this;

        npcTeam = MinecraftServer.getTeamManager().createTeam(
                "NPC-TEAM",
                Component.text("[NPC] ", NamedTextColor.DARK_GRAY),
                NamedTextColor.DARK_GRAY,
                Component.empty()
        );

        npcTeam.setNameTagVisibility(TeamsPacket.NameTagVisibility.NEVER);

        this.fabric = new Fabric();

        this.gson = new GsonBuilder().setPrettyPrinting().create();

        MinecraftServer.getGlobalEventHandler().addChild(events());

        mLibConfig config = null;

        try {
            File configFile = FileUtil.getDataFolder(this, "config.json");
            config = ConfigLoader.loadConfig(new mLibConfig(), configFile);
        } catch (IOException e) {
            e.printStackTrace();
            MinecraftServer.stopCleanly();
        }

        this.config = config;

        if(config != null) {
            this.loadPigeon();
            this.loadMongo();

            this.commonLib = new CommonLib(this.mongoClient, this.mongoDatabase, this.pigeon);

            this.pigeon.acceptDelivery();
        } else {
            System.out.println("Config was NULL in mLib");
        }
    }

    @Override
    public void terminate() {}

    public static EventNode<EntityEvent> events() {
        return EventNode.type("npc-events", EventFilter.ENTITY)
                .addListener(PlayerEntityInteractEvent.class, event -> {
                    Entity target = event.getTarget();

                    if (!(target instanceof NPC)) {
                        return;
                    }

                    NPC npc = (NPC) target;

                    if(event.getHand() == Player.Hand.MAIN)
                        npc.getInteraction().accept(new NPCInteraction(event.getPlayer(), npc));
                });
    }

    private void loadPigeon() {
        String host = this.config.getRabbitMqHost();
        int port = this.config.getRabbitMqPort();

        this.pigeon = new Pigeon();
        this.pigeon.initialize(host, port, this.config.getNetworkId(), this.config.getName());
        this.pigeon.setupDefaultUpdater();
    }

    private void loadMongo() {
        this.mongoClient = new MongoClient(this.config.getMongoHost(), this.config.getMongoPort());
        this.mongoDatabase = this.mongoClient.getDatabase(this.config.getMongoDatabase());
    }
}
