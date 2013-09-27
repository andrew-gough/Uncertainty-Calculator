package uncertaintyCalculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.andrew.util.Maths;

import javax.swing.JOptionPane;

public class UncertaintyCalculator {

	private ArrayList<ArrayList<ArrayList<Double>>> storedData;
	private ArrayList<ArrayList<Double>> averageDataSet;
	private ArrayList<Double> percentageUncert;
	private ArrayList<ArrayList<Double>> maxMinusMin;
	private ArrayList<ArrayList<Double>> calculationMinMax;

	public UncertaintyCalculator(){
		averageDataSet = new ArrayList<ArrayList<Double>>();
		percentageUncert = new ArrayList<Double>();
		storedData = new ArrayList<ArrayList<ArrayList<Double>>>();
		maxMinusMin = new ArrayList<ArrayList<Double>>();
		
		//DataSetIndex,X,Y

	}

	public void fillMaxMinusMin(){
		int dataNumber = 0;
		int i = 0;
		int ii = 0;
		//ArrayList<ArrayList<Double>> maxMinusMin = new ArrayList<ArrayList<Double>>();
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

		calculationMinMax = new ArrayList<ArrayList<Double>>();
		i = 0;
		ii = 0;
		while(i<maxMinusMin.size()){
			calculationMinMax.add(new ArrayList<Double>());
			while(ii<maxMinusMin.get(i).size()){
				calculationMinMax.get(i).add(maxMinusMin.get(i).get(ii));
				ii++;
			}
			ii = 0;
			i++;	
		}
//		System.out.println("New ArrayList : ");
//		System.out.println(calculationMinMax);
//		System.out.println("OldArrayList : ");
//		System.out.println(maxMinusMin);
		

		i = 0;
		ii = 0;
		while(i<calculationMinMax.size()){
			while(ii<calculationMinMax.get(i).size()){
				//This if statement prevents NaNs by not dividing by 0 and instead dividing by the minimum value of doubles
				if(averageDataSet.get(i).get(ii) != 0){
					calculationMinMax.get(i).set(ii,(100*calculationMinMax.get(i).get(ii))/averageDataSet.get(i).get(ii));
				}else{
					calculationMinMax.get(i).set(ii,(100*calculationMinMax.get(i).get(ii))/Double.MIN_VALUE);
				}
				ii++;
			}
			ii = 0;
			i++;
		}
   
//		System.out.println("New ArrayList : ");
//		System.out.println(calculationMinMax);
//		System.out.println("OldArrayList : ");
//		System.out.println(maxMinusMin);
		
		i = 0; 
		ii = 0;
		double total = 0;
		while(ii<calculationMinMax.get(0).size()){
			while(i<calculationMinMax.size()){
				total = total + calculationMinMax.get(i).get(ii);
				i++;
			}
			percentageUncert.add(total/calculationMinMax.size());
			total = 0;
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
		try{
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
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" Exception Caught!");
			JOptionPane.showMessageDialog(null, "Make Sure You Entered in a Valid Set Of Data","Data Error Error",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		storedData.add(individual);
		return true;
	}


	//This Method just prints all the Data Sets to the console

	public void printAllDataSetsToConsole(){
		int dataSetNumber = 0;
		StringBuilder output = new StringBuilder();
		while(dataSetNumber < storedData.size()){
			output.append("Data Set Number : " + (dataSetNumber + 1));
			output.append("\n");
			int yCoord = 0;
			while(yCoord < storedData.get(dataSetNumber).size()){
				int xCoord = 0;
				while(xCoord<storedData.get(dataSetNumber).get(yCoord).size()){
					output.append(storedData.get(dataSetNumber).get(yCoord).get(xCoord));
					output.append(" ");
					xCoord++;
				}
				output.append("\n");
				yCoord++;
			}
			dataSetNumber++;
			output.append("\n");
		}
		System.out.println(output.toString().trim());

	}

	//	public void printAllDataSetsToConsole(){
	//		System.out.println(" ");
	//		int dataSetNumber = 0;
	//		while(dataSetNumber < storedData.size()){
	//			System.out.println("Data Set Number : " + (dataSetNumber + 1));
	//			System.out.println(" ");
	//			int yCoord = 0;
	//			while(yCoord < storedData.get(dataSetNumber).size()){
	//				int xCoord = 0;
	//				while(xCoord<storedData.get(dataSetNumber).get(yCoord).size()){
	//					System.out.println(storedData.get(dataSetNumber).get(yCoord).get(xCoord));
	//					xCoord++;
	//				}
	//				yCoord++;
	//			}
	//			dataSetNumber++;
	//			System.out.println(" ");
	//		}
	//
	//	}


	public void printAverageDataSetToConsole(){
		System.out.println(" ");
		System.out.println("Average Data Set:");
		System.out.println(" ");
		StringBuilder output = new StringBuilder();
		int yCoord = 0;
		while(yCoord < averageDataSet.size()){
			int xCoord = 0;
			while(xCoord<averageDataSet.get(yCoord).size()){
				output.append(averageDataSet.get(yCoord).get(xCoord));
				output.append(" ");
				//					System.out.println(averageDataSet.get(yCoord).get(xCoord));
				xCoord++;
			}
			System.out.println(output.toString().trim());
			output.setLength(0);
			yCoord++;
		}

	}

	//	public void printAverageDataSetToConsole(){
	//		System.out.println(" ");
	//		System.out.println("Average Data Set:");
	//		System.out.println(" ");
	//			int yCoord = 0;
	//			while(yCoord < averageDataSet.size()){
	//				int xCoord = 0;
	//				while(xCoord<averageDataSet.get(yCoord).size()){
	//					System.out.println(averageDataSet.get(yCoord).get(xCoord));
	//					xCoord++;
	//				}
	//				yCoord++;
	//			}
	//
	//	}


	public boolean outputDataToFile(File output){
		try{
			FileWriter writer = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(writer);


			//Writes the number of data sets stored
			bw.write("Number of Individual Data Sets: "+getSetsOfData());
			bw.newLine();
			bw.newLine();
			//Writes the data from the average data set
			bw.write("Average Data Set:");
			bw.newLine();
			bw.newLine();
			int yCoord = 0;
			while(yCoord < averageDataSet.size()){
				int xCoord = 0;
				while(xCoord<averageDataSet.get(yCoord).size()){
					bw.write(averageDataSet.get(yCoord).get(xCoord).toString());
					bw.write("\t");
					//					System.out.println(averageDataSet.get(yCoord).get(xCoord));
					xCoord++;
				}
				yCoord++;
				bw.newLine();
			}
			bw.newLine();

			//Writes all the data from the individual data sets


			int dataSetNumber = 0;
			while(dataSetNumber < storedData.size()){
				bw.write("Data Set Number : " + (dataSetNumber + 1));
				bw.newLine();
				bw.newLine();
				yCoord = 0;
				while(yCoord < storedData.get(dataSetNumber).size()){
					int xCoord = 0;
					while(xCoord<storedData.get(dataSetNumber).get(yCoord).size()){
						bw.write(storedData.get(dataSetNumber).get(yCoord).get(xCoord).toString());
						bw.write("\t");
						xCoord++;
					}
					bw.newLine();
					yCoord++;
				}
				dataSetNumber++;
				bw.newLine();
			}

			bw.newLine();

			bw.write("Percentage Uncertainty in Columns:");
			bw.newLine();
			bw.newLine();
			int i = 0;
			while(i < percentageUncert.size()){

				bw.write("Column "+ (i+1) + ":  ±" + percentageUncert.get(i)+ "%");
				bw.newLine();
				i++;
			}


			bw.close();
			return true;
		}catch(IOException e){
			System.out.println("File wasn't accessed for whatever reason");
			return false;
		}




	}

	public boolean outputProcessToFile(File output){
		try{
			FileWriter writer = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(writer);
			int yCoord;
			int xCoord;


			//Calculation walk through
			bw.newLine();
			bw.write("Calculation Data for Random Uncertainties:");
			bw.newLine();
			bw.newLine();
			bw.write("First of all, the average data set is constructed from the individual data sets:");
			bw.newLine();
			bw.newLine();

			//Writes all the data from the individual data sets

			int dataSetNumber = 0;
			while(dataSetNumber < storedData.size()){
				bw.write("Data Set Number : " + (dataSetNumber + 1));
				bw.newLine();
				bw.newLine();
				yCoord = 0;
				while(yCoord < storedData.get(dataSetNumber).size()){
					xCoord = 0;
					while(xCoord<storedData.get(dataSetNumber).get(yCoord).size()){
						bw.write(storedData.get(dataSetNumber).get(yCoord).get(xCoord).toString());
						bw.write("\t");
						xCoord++;
					}
					bw.newLine();
					yCoord++;
				}
				dataSetNumber++;
				bw.newLine();
			}

			//Writes the data from the average data set
			bw.write("Average Data Set:");
			bw.newLine();
			bw.newLine();
			yCoord = 0;
			while(yCoord < averageDataSet.size()){
				xCoord = 0;
				while(xCoord<averageDataSet.get(yCoord).size()){
					bw.write(averageDataSet.get(yCoord).get(xCoord).toString());
					bw.write("\t");
					//					System.out.println(averageDataSet.get(yCoord).get(xCoord));
					xCoord++;
				}
				yCoord++;
				bw.newLine();
			}

			bw.newLine();
			bw.newLine();
			bw.write("Then a table is filled with the Max-Min Calculations:");
			bw.newLine();
			bw.newLine();

			bw.write("Max-Min Set:");
			bw.newLine();
			bw.newLine();
			yCoord = 0;
			while(yCoord < maxMinusMin.size()){
				xCoord = 0;
				while(xCoord<maxMinusMin.get(yCoord).size()){
					bw.write(Maths.roundToSignificantFigures(maxMinusMin.get(yCoord).get(xCoord),2).toString());
					bw.write("\t");
					//					System.out.println(averageDataSet.get(yCoord).get(xCoord));
					xCoord++;
				}
				yCoord++;
				bw.newLine();
			}
			
			bw.newLine();
			bw.write("The percentage uncertainty in each measurement is calculated using the average set and the MaxMinusMin set");
			bw.newLine();	
			bw.newLine();
			bw.write("Equation:");
			bw.newLine();	
			bw.newLine();
			bw.write("(100*MaxMinusMin)/AverageValue (%)");
			bw.newLine();	
			bw.newLine();
			bw.write("Percentage uncertainty in each measurement :");
			bw.newLine();
			bw.newLine();
			yCoord = 0;
			while(yCoord < calculationMinMax.size()){
				xCoord = 0;
				while(xCoord<calculationMinMax.get(yCoord).size()){
					bw.write(Maths.roundToSignificantFigures(calculationMinMax.get(yCoord).get(xCoord),2).toString()+ "%");
					bw.write("\t");
					//					System.out.println(averageDataSet.get(yCoord).get(xCoord));
					xCoord++;
				}
				yCoord++;
				bw.newLine();
			}
			
			bw.newLine();	
			bw.newLine();
			bw.write("These percentages are averaged out to a single value per column");
			bw.newLine();
			bw.newLine();
			bw.write("Percentage Uncertainty in Columns:");
			bw.newLine();
			bw.newLine();
			int i = 0;
			while(i < percentageUncert.size()){

				bw.write("Column "+ (i+1) + ":  ±" + Maths.roundToSignificantFigures(percentageUncert.get(i),2)+ "%");
				bw.newLine();
				i++;
			}


			bw.close();
			return true;
		}catch(IOException e){
			System.out.println("File wasn't accessed for whatever reason");
			return false;
		}




	}
}
