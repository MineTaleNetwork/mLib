package cc.minetale.mlib;

import cc.minetale.mlib.commands.SpawnNPCCommand;
import cc.minetale.mlib.config.mLibConfig;
import cc.minetale.mlib.fabric.Fabric;
import cc.minetale.mlib.npc.NPC;
import cc.minetale.mlib.npc.NPCTask;
import cc.minetale.mlib.util.FileUtil;
import cc.minetale.commonlib.CommonLib;
import cc.minetale.pigeon.Pigeon;
import cc.minetale.quartz.config.ConfigLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class mLib extends Extension {

    @Getter private static mLib mLib;
    private List<NPC> npcList;
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

        this.fabric = new Fabric();

        this.npcList = new ArrayList<>();

        MinecraftServer.getCommandManager().register(new SpawnNPCCommand());
        NPCTask.startTask();

        this.gson = new GsonBuilder().setPrettyPrinting().create();

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
