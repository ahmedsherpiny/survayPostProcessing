package de.tum.mw.lfe.survey.utils;

public class SubTask {
	private String name;
	private String[] textIndices;
	private String originalSubTaskText;
	private String subTaskAnswer;
	private double time;
	private String timeIndex;
public String[] getTextIndices() {
	return textIndices;
}
public void setTextIndices(String[] textIndices) {
	this.textIndices = textIndices;
}
public String getTimeIndex() {
	return timeIndex;
}
public void setTimeIndex(String timeIndex) {
	this.timeIndex = timeIndex;
}
public String getOriginalSubTaskText() {
	return originalSubTaskText;
}
public void setOriginalSubTaskText(String originalSubTaskText) {
	this.originalSubTaskText = originalSubTaskText;
}
public String getSubTaskAnswer() {
	return subTaskAnswer;
}
public void setSubTaskAnswer(String subTaskAnswer) {
	this.subTaskAnswer = subTaskAnswer;
}
public double getTime() {
	return time;
}
public void setTime(double time) {
	this.time = time;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
