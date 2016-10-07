package com.app.maps.commonClasses;

import java.io.Serializable;
import java.util.Date;

public class PAction implements Serializable{
	
	public enum ActionType{
		Measure, Drugtake, Activity
	}
	public enum MeasureType{
		Blood, Temperature
	}
	
	private static final long serialVersionUID = -1882493010132364116L;  // what's this?
	private int actionId;
	private String medicName;
	private ActionType actionType;
	private MeasureType measureType;
	private int period;   					// ex: numero de dias, fazer durante x dias
	private int numberreps;  				// numero de repeticoes por dia
	//String com o formato "dd/mm/aa-hh:mm"
	private String timeToStart; 	//nao sei se assim dá, é timeStamp na BD
	private String description;				//Breve descrição do médico sobre a prescrição.
	private String drugName;
	
	//java.util.Date date= new java.util.Date();
	//System.out.println(new Timestamp(date.getTime()));
	
	
	public PAction(ActionType actionType, String timeToStart){
		this.actionType = actionType;
		this.timeToStart = timeToStart;
	}
	
	//Activity
	public PAction(int actionId,ActionType actionType, String timeToStart, int period, int numberreps, String description){
		this.actionId=actionId;
		this.actionType = actionType;
		this.timeToStart = timeToStart;
		this.period = period;
		this.numberreps = numberreps;
		this.description = description;
	}
	
	//Drugtake
	public PAction(int actionId,ActionType actionType, String timeToStart, int period, int numberreps, String description, String drugName){
		this.actionId=actionId;
		this.actionType = actionType;
		this.timeToStart = timeToStart;
		this.period = period;
		this.numberreps = numberreps;
		this.description = description;
		this.drugName = drugName;
	}
	
	//Measure
	public PAction(int actionId,ActionType actionType, MeasureType measureType, String timeToStart, int period, int numberreps, String description){
		this.actionId=actionId;
		this.actionType = actionType;
		this.measureType = measureType;
		this.timeToStart = timeToStart;
		this.period = period;
		this.numberreps = numberreps;
		this.description = description;
	}
	
	public ActionType getActionType(){
		return actionType;
	}
	
	public MeasureType getActionMeasureType(){
		return measureType;
	}
	
	public String getActionDescription(){
		return description;
	}
	
	public String getActionDrugName(){
		return drugName;
	}
	
	public int getActionPeriod(){
		return period;
	}
	
	public int getActionNumberOfReps(){
		return numberreps;
	}
	
	public String getActionMedicName(){
		return medicName;
	}
	
	public String getDateToStart(){
		return timeToStart;
	}
	
	public void setActionId(int actionId){
		this.actionId = actionId;
	}
	
	public int getActionId(){
		return actionId;
	}
	
	@Override
	public String toString(){
		return "Action( actionId= "+actionId+" medicName= "+medicName+" actionType= "+actionType+" measureType= "+measureType+" period= "+period+" numberreps= "+numberreps+" timeToStart= "+timeToStart+" description= "+description+" drugName= "+drugName;
	}

	public String getMedicName() {
		return medicName;
	}

	public void setMedicName(String medicName) {
		this.medicName = medicName;
	}

	public MeasureType getMeasureType() {
		return measureType;
	}

	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getNumberreps() {
		return numberreps;
	}

	public void setNumberreps(int numberreps) {
		this.numberreps = numberreps;
	}

	public String getTimeToStart() {
		return timeToStart;
	}

	public void setTimeToStart(String timeToStart) {
		this.timeToStart = timeToStart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
}