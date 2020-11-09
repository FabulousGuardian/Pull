package me.alpho320.fabulous.skills;

import me.alpho320.fabulous.bosses.Main;
import me.alpho320.fabulous.bosses.extension.Skill;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Pull extends Skill {

    Map<LivingEntity, LocalDateTime> timeMap = new HashMap<>();

    public Pull(Main main) {
        super(main);
    }

    @Override
    public void use(LivingEntity entity, long tick) {
        if(entity.isDead()) return;
        if(!timeMap.containsKey(entity))
            timeMap.put(entity, LocalDateTime.now().plusSeconds(tick/20));

        if(ChronoUnit.SECONDS.between(LocalDateTime.now(), timeMap.get(entity)) > 0) return;

        for (Entity e : entity.getNearbyEntities(20, 20, 20)) {
            if (e instanceof Player) {
                Player p = (Player) e;

                Location playerCenterLocation = entity.getEyeLocation();
                Location playerToThrowLocation = p.getEyeLocation();

                double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
                double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
                double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

                Vector throwVector = new Vector(x, y, z);

                throwVector.normalize();
                throwVector.multiply(-1.0D);
                throwVector.setY(0.1D);

                p.setVelocity(throwVector);
            }
        }
        timeMap.put(entity, LocalDateTime.now().plusSeconds(tick/20));
    }

    @Override
    public String getName() {
        return "pull";
    }
}
