package uncertaintyCalculator;

import java.util.ArrayList;
import java.util.Scanner;

public class UncertaintyCalculator {

	private ArrayList<ArrayList<ArrayList<Double>>> storedData;
	private ArrayList<ArrayList<Double>> averageDataSet;
	private ArrayList<Double> percentageUncert;

	public UncertaintyCalculator(){
		averageDataSet = new ArrayList<ArrayList<Double>>();
		percentageUncert = new ArrayList<Double>();
		storedData = new ArrayList<ArrayList<ArrayList<Double>>>();
		//DataSetIndex,X,Y

	}
	
	public void fillMaxMinusMin(){
		int dataNumber = 0;
		int i = 0;
		int ii = 0;
		ArrayList<ArrayList<Double>> maxMinusMin = new ArrayList<ArrayList<Double>>();
		double max = 0;
		double min = Double.MAX_VALUE;
		ArrayList<Double> maxMin = new ArrayList<Double>();
		while(i<storedData.get(dataNumber).size()){
			while(ii<storedData.get(dataNumber).get(i).size()){
				while(dataNumber<storedData.size()){
					if(max<storedData.get(dataNumber).get(i).get(ii)){
						max = storedData.get(dataNumber).get(i).get(ii);
					}
					if(min>storedData.get(dataNumber).get(i).get(ii)){
						min = storedData.get(dataNumber).get(i).get(ii);
					}
					dataNumber++;
				}
				maxMin.add(max-min);
				max = 0;
				min = Double.MAX_VALUE;
				dataNumber = 0;
				ii++;
			}
			ii = 0;
			maxMinusMin.add(maxMin);
			maxMin = new ArrayList<Double>();
			i++;
		}
		//System.out.println(maxMinusMin);

		
		i = 0;
		ii = 0;
		while(i<maxMinusMin.size()){
			while(ii<maxMinusMin.get(i).size()){
				//This if statement prevents NaNs by not dividing by 0 and instead dividing by the minimum value of doubles
				if(averageDataSet.get(i).get(ii) != 0){
				maxMinusMin.get(i).set(ii,(100*maxMinusMin.get(i).get(ii))/averageDataSet.get(i).get(ii));
				}else{
					maxMinusMin.get(i).set(ii,(100*maxMinusMin.get(i).get(ii))/Double.MIN_VALUE);
				}
				ii++;
			}
			ii = 0;
			i++;
		}
		//System.out.println(maxMinusMin);
		
		i = 0;
		ii = 0;
		double total = 0;
		while(ii<maxMinusMin.get(0).size()){
			while(i<maxMinusMin.size()){
				total = total + maxMinusMin.get(i).get(ii);
				i++;
			}
			percentageUncert.add(total/maxMinusMin.size());
			i = 0;
			ii++;
		}
		//System.out.println(percentageUncert);
		
		
	}


	public void clearData(){
		averageDataSet.clear();
		storedData.clear();
		percentageUncert.clear();
	}
	
	
	public double getColumnUnCert(int index){
		return percentageUncert.get(index);
	}
	
	public int getColumnsOfData(){
		return averageDataSet.get(0).size();
	}
	
	public int getSetsOfData(){
		return storedData.size();
	}
	
	public ArrayList<ArrayList<Double>> getDataSet(int index){
		if(index<0||index>=storedData.size()){
			return null;
		}else{
			return storedData.get(index);
		}
	}
	
	public ArrayList<ArrayList<Double>> getAverageDataSet(){
		return averageDataSet;
	}
	
	public ArrayList<Double> getAverageDataColumn(int column){
		int i = 0;
		ArrayList<Double> output = new ArrayList<Double>();
		while(i<averageDataSet.size()){
			output.add(averageDataSet.get(i).get(column));
			i++;
		}
		return output;
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
			a.useDelimiter(" |\t");
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
					System.out.println(storedData.get(dataSetNumber).get(yCoord).get(xCoord));
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
					System.out.println(averageDataSet.get(yCoord).get(xCoord));
					xCoord++;
				}
				yCoord++;
			}
	System.out.println("Printing the whole Average ArrayList");
	System.out.println(" ");
	System.out.println(averageDataSet);
	}

}
