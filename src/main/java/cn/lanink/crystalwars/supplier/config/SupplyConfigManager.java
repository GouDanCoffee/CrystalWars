package cn.lanink.crystalwars.supplier.config;

import cn.lanink.crystalwars.CrystalWars;
import cn.nukkit.item.Item;
import lombok.Getter;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author iGxnon
 * @date 2021/9/4
 */
public class SupplyConfigManager {

    private static final CrystalWars CRYSTAL_WARS = CrystalWars.getInstance();

    /**
     * 需要根据队伍更改的物品（颜色）
     * 羊毛，玻璃块，粘土块，地毯
     */
    public static final List<Integer> TEAM_CHANGE_ITEM_IDS = Arrays.asList(
            35, 95, 159, 171,
            Item.LEATHER_CAP, Item.LEATHER_TUNIC, Item.LEATHER_PANTS, Item.LEATHER_BOOTS //皮革甲
    );

    @Getter
    private static final Map<String, SupplyConfig> SUPPLY_CONFIG_MAP = new HashMap<>();

    private SupplyConfigManager() {
        throw new RuntimeException("哎呀！你不能实例化这个类！");
    }

    public static void loadAllSupplyConfig() {
        File dir = new File(CRYSTAL_WARS.getDataFolder(), "/Supply/");
        if(!dir.exists()) {
            dir.mkdirs();
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/items/goldenApple.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/items/swordDiamond.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/items/swordIron.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/items/swordIronKnockback.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/items/swordStone.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/items/wool.yml");

            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageBlock.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageQuickBuy.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageProp.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageTool.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageWeapon.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageFood.yml");
            CRYSTAL_WARS.saveResource("Supply/DefaultSupply/pages/pageArmor.yml");
        }
        final File[] files = dir.listFiles();
        if(files == null) {
            return;
        }
        AtomicInteger count = new AtomicInteger();
        Stream.of(Objects.requireNonNull(files))
                .filter(File::isDirectory)
                .forEach(supplyDir -> {
                    SupplyConfig supplyConfig = new SupplyConfig(supplyDir.getName(), supplyDir);
                    SUPPLY_CONFIG_MAP.put(supplyDir.getName(), supplyConfig);
                    count.incrementAndGet();
                });
        CRYSTAL_WARS.getLogger().info("已成功加载" + count + "个商店配置");
        if(CrystalWars.debug) {
            CRYSTAL_WARS.getLogger().info("[debug] " + SUPPLY_CONFIG_MAP);
        }
    }

    public static SupplyConfig getSupplyConfig(String supply) {
        return SUPPLY_CONFIG_MAP.get(supply);
    }

}
