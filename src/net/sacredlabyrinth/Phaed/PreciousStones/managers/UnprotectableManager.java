package net.sacredlabyrinth.Phaed.PreciousStones.managers;

import org.bukkit.block.Block;

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.managers.SettingsManager.FieldSettings;

/**
 * Handles unprotectable blocks
 *
 * @author Phaed
 */
public class UnprotectableManager
{
    private PreciousStones plugin;

    /**
     *
     * @param plugin
     */
    public UnprotectableManager(PreciousStones plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Whether the block is touching an unprotectable block
     * @param block the block that has been placed
     * @return true if an unprotectable is found touching it
     */
    public boolean touchingUnprotectableBlock(Block block)
    {
        return getTouchingUnprotectableBlock(block) != null;
    }

    /**
     * If the block is touching an unprotectable block return it
     * @param block the block that has been placed
     * @return the offending unprotectable block
     */
    public Block getTouchingUnprotectableBlock(Block block)
    {
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                for (int y = -1; y <= 1; y++)
                {
                    if (x == 0 && y == 0 && z == 0)
                    {
                        continue;
                    }

                    int type = block.getWorld().getBlockTypeIdAt(block.getX() + x, block.getY() + y, block.getZ() + z);

                    if (plugin.settings.isUnprotectableType(type))
                    {
                        return block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);
                    }
                }
            }
        }

        return null;
    }

    /**
     * If an unprotectable block exists inside the field return it
     * @param fieldblock the block that contains the field
     * @return the offending block
     */
    public Block existsUnprotectableBlock(Block fieldblock)
    {
        FieldSettings fieldsettings = plugin.settings.getFieldSettings(fieldblock.getTypeId());

        if (fieldsettings == null)
        {
            return null;
        }

        int minx = fieldblock.getX() - fieldsettings.radius;
        int maxx = fieldblock.getX() + fieldsettings.radius;
        int minz = fieldblock.getZ() - fieldsettings.radius;
        int maxz = fieldblock.getZ() + fieldsettings.radius;

        int miny = fieldblock.getY() - (int) Math.floor(((double) fieldsettings.getHeight()) / 2);
        int maxy = fieldblock.getY() + (int) Math.ceil(((double) fieldsettings.getHeight()) / 2);

        for (int x = minx; x <= maxx; x++)
        {
            for (int z = minz; z <= maxz; z++)
            {
                for (int y = miny; y <= maxy; y++)
                {
                    int type = fieldblock.getWorld().getBlockTypeIdAt(x, y, z);

                    if (plugin.settings.isUnprotectableType(type))
                    {
                        return fieldblock.getWorld().getBlockAt(x, y, z);
                    }
                }
            }
        }

        return null;
    }
}
