package me.alpho320.fabulous.skills;

import me.alpho320.fabulous.guardian.Main;
import me.alpho320.fabulous.guardian.extension.Skill;
import org.bukkit.entity.Entity;

import java.util.Collection;

public class Pull extends Skill {

    public Pull(Main main) {
        super(main);
    }

    @Override
    public void use(Entity entity, long tick) {
        if(entity.isDead()) return;

        Collection<Entity> entityList = entity.getLocation().getWorld().getNearbyEntities(
                entity.getLocation(),
                30,
                15,
                20
        );

        entityList.forEach(e -> e.setVelocity(entity.getLocation().getDirection()));

        main.getServer().getScheduler().runTaskLater(main, () -> {
            use(entity, tick);
        }, tick);
    }

    @Override
    public String getName() {
        return "pull";
    }
}
