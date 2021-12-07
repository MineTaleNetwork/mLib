package cc.minetale.mlib;

import cc.minetale.commonlib.CommonLib;
import cc.minetale.commonlib.util.StringUtil;
import cc.minetale.mlib.fabric.Fabric;
import cc.minetale.mlib.npc.NPC;
import cc.minetale.mlib.npc.NPCInteraction;
import cc.minetale.pigeon.Pigeon;
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
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;

@Getter
public class mLib extends Extension {

    @Getter private static mLib mLib;
    @Getter private static Team npcTeam;
    private Gson gson;
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

        this.loadPigeon();
        this.loadMongo();

        this.commonLib = new CommonLib(this.mongoClient, this.mongoDatabase, this.pigeon);

        this.pigeon.acceptDelivery();
    }

    @Override
    public void terminate() {}

    public static EventNode<EntityEvent> events() {
        return EventNode.type("mLib", EventFilter.ENTITY)
                .addListener(PlayerEntityInteractEvent.class, event -> {
                    Entity target = event.getTarget();

                    if (!(target instanceof NPC npc)) {
                        return;
                    }

                    if(event.getHand() == Player.Hand.MAIN)
                        npc.getInteraction().accept(new NPCInteraction(event.getPlayer(), npc));
                });
    }

    private void loadPigeon() {
        this.pigeon = new Pigeon();

        this.pigeon.initialize(
                System.getProperty("pigeonHost", "127.0.0.1"),
                Integer.getInteger("pigeonPort", 5672),
                System.getProperty("pigeonNetwork", "minetale"),
                System.getProperty("pigeonUnit", StringUtil.generateId())
        );

        this.pigeon.setupDefaultUpdater();
    }

    private void loadMongo() {
        this.mongoClient = new MongoClient(System.getProperty("mongoHost", "127.0.0.1"), Integer.getInteger("mongoPort", 27017));
        this.mongoDatabase = this.mongoClient.getDatabase(System.getProperty("mongoDatabase", "MineTale"));
    }
}
