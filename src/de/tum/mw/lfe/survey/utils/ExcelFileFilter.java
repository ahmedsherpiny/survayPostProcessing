package de.tum.mw.lfe.survey.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExcelFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
			}
		if(f.getName().toLowerCase().endsWith("xls")||f.getName().toLowerCase().endsWith("xlsx"))
			return true;
		return false;
		}
	@Override
	public String getDescription() {
		return ".XLS and .XLSX format files only";
		}

}
