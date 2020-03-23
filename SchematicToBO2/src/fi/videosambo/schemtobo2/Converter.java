package fi.videosambo.schemtobo2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

public class Converter {

	private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
		Tag tag = items.get(key);
		return tag;
	}

	public static File convertToBO2(File file, File saveFile) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		NBTInputStream nbt = new NBTInputStream(fis);
		CompoundTag backuptag = (CompoundTag) nbt.readTag();
		Map<String, Tag> tagCollection = backuptag.getValue();

		short width = (Short) getChildTag(tagCollection, "Width", ShortTag.class).getValue();
		short height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
		short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

		byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
		byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

		List entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
		List tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();
		nbt.close();
		fis.close();

		// Write to file

		File bo2 = saveFile;
		bo2.createNewFile();
		FileWriter fileWriter = new FileWriter(saveFile);
		String s = "[META]\nversion=2.0\nspawnOnBlockType=2\nspawnSunlight=True\nspawnDarkness=False\nspawnWater=False\nspawnLava=False\nunderFill=True\nrandomRotation=True\ndig=True\ntree=False\nbranch=False\nneedsFoundation=True\nrarity=10\ncollisionPercentage=2\nspawnElevationMin=0\nspawnElevationMax=128\nspawnInBiome=All\n[DATA]\n";

		int xOffset = (int) Math.floor(width/2);
		int yOffset = (int) Math.floor(height/2);
		int zOffset = (int) Math.floor(length/2);
		
		boolean copyair = GUI.copyAir(); 
		
		int i = 0;
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < length; z++) {
				for (int x = 0; x < width; x++) {
					if (getByteID(blocks, i) == 0) {
						if (copyair) {
							s = s + (z-zOffset) + "," + (x-xOffset) + "," + (y-yOffset) + ":" + getByteID(blocks, i) + "." + getByteID(data, i) + "\n";
						}
					} else {
						s = s + (z-zOffset) + "," + (x-xOffset) + "," + (y-yOffset) + ":" + getByteID(blocks, i) + "." + getByteID(data, i) + "\n";
					}
					i++;
				}
			}
		}

		fileWriter.write(s);
		fileWriter.close();
		return bo2;
		// return bo2;
	}

	private static int getByteID(byte[] blocks, int index) {
		return (blocks[index] & 0xFF);
	}

}
