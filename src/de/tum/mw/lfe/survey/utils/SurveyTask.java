package de.tum.mw.lfe.survey.utils;

import java.util.ArrayList;

public class SurveyTask {
private String name;
private ArrayList<SubTask> subTasks;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public ArrayList<SubTask> getSubTasks() {
	return subTasks;
}
public void setSubTasks(ArrayList<SubTask> subTasks) {
	this.subTasks = subTasks;
}
}
