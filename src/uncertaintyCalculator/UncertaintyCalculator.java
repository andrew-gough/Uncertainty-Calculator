package uncertaintyCalculator;

import java.util.ArrayList;
import java.util.Scanner;

public class UncertaintyCalculator {

	private ArrayList<ArrayList<ArrayList<Double>>> storedData;
	private ArrayList<ArrayList<Double>> averageDataSet;
	private ArrayList<ArrayList<Double>> currentDataSet;

	public UncertaintyCalculator(){
		averageDataSet = new ArrayList<ArrayList<Double>>();
		storedData = new ArrayList<ArrayList<ArrayList<Double>>>();
		currentDataSet = new ArrayList<ArrayList<Double>>();
		//DataSetIndex,X,Y

	}

	public boolean selectDataSet(int index){
		if(index<0||index>=storedData.size()){
			return false;
		}else{
			currentDataSet = storedData.get(index);
			return true;
		}
	}

	public void clearData(){
		storedData.clear();
	}
	
	public void calculateAverageDataSet(){
		int yCoord = 0;
		while(yCoord < storedData.get(0).size()){
			int xCoord = 0;
			ArrayList<Double> inputArrayList = new ArrayList<Double>();
			while(xCoord<storedData.get(0).get(yCoord).size()){
				Double averageData = 0D;
				for(ArrayList<ArrayList<Double>> item : storedData ){
					   averageData = averageData + item.get(yCoord).get(xCoord);
				}
				averageData = averageData/storedData.size();
				inputArrayList.add(averageData);
				xCoord++;
			}
			averageDataSet.add(inputArrayList);
			yCoord++;
		}
	}

	public boolean addDataSet(String input){
		ArrayList<String> lines = new ArrayList<String>();
		Scanner s = new Scanner(input);
		s.useDelimiter("\\n");
		while(s.hasNext()){
			lines.add(s.next());
		}
		s.close();
		int i = 0;
		ArrayList<ArrayList<Double>> individual = new ArrayList<ArrayList<Double>>();
		while(i<lines.size()){
			ArrayList<Double> inputArrayList = new ArrayList<Double>();
			Scanner a = new Scanner(lines.get(i));
			a.useDelimiter(" ");
			while(a.hasNext()){
				inputArrayList.add(a.nextDouble());
			}	
			a.close();
			individual.add(inputArrayList);
			i++;
		}
		storedData.add(individual);
		return true;
	}
	
	
	//This Method just prints all the Data Sets to the console
	public void printAllDataSetsToConsole(){
		System.out.println(" ");
		int dataSetNumber = 0;
		while(dataSetNumber < storedData.size()){
			System.out.println("Data Set Number : " + (dataSetNumber + 1));
			System.out.println(" ");
			int yCoord = 0;
			while(yCoord < storedData.get(dataSetNumber).size()){
				int xCoord = 0;
				while(xCoord<storedData.get(dataSetNumber).get(yCoord).size()){
					System.out.println(storedData.get(dataSetNumber).get(xCoord).get(yCoord));
					xCoord++;
				}
				yCoord++;
			}
			dataSetNumber++;
			System.out.println(" ");
		}
	System.out.println("Printing the whole ArrayList");
	System.out.println(" ");
	System.out.println(storedData);
	}
	
	public void printAverageDataSetToConsole(){
		System.out.println(" ");
		System.out.println("Average Data Set:");
		System.out.println(" ");
			int yCoord = 0;
			while(yCoord < averageDataSet.size()){
				int xCoord = 0;
				while(xCoord<averageDataSet.get(yCoord).size()){
					System.out.println(averageDataSet.get(xCoord).get(yCoord));
					xCoord++;
				}
				yCoord++;
			}
	System.out.println("Printing the whole Average ArrayList");
	System.out.println(" ");
	System.out.println(averageDataSet);
	}

}
