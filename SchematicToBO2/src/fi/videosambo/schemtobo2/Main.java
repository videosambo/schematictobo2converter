package fi.videosambo.schemtobo2;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

	public static void main(String[] args) {
		GUI.showWindow();
	}

	public static File chooseFile() {
		JFileChooser fileChooser = new JFileChooser();
		StringBuilder sb = new StringBuilder();
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	public static File fileSaver() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Minne haluat tallentaa tiedoston");
		chooser.setApproveButtonText("Tallenna");
		chooser.setFileFilter(new FileNameExtensionFilter("Biome Object Builder version 2", "bo2"));
		int selected = chooser.showSaveDialog(new JFrame("Tallennettu"));
		if (selected == JFileChooser.APPROVE_OPTION) {
			File saveFile = chooser.getSelectedFile();
			if (saveFile == null)
				return null;
			if (!saveFile.getName().toLowerCase().endsWith(".bo2")) {
				saveFile = new File(saveFile.getParentFile(), saveFile.getName() + ".bo2");
			}
			return saveFile;
		}
		return null;
	}

}
