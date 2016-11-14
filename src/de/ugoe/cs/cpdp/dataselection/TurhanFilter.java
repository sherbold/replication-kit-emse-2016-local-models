package de.ugoe.cs.cpdp.dataselection;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.util.MathArrays;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import de.ugoe.cs.util.ArrayTools;

/**
 * Filter according to B. Turhan, T. Menzies, A. Bener, and J. Die Stefano: On the relative value of cross-company and within company defect prediction
 * @author Steffen Herbold
 */
public class TurhanFilter implements IPointWiseDataselectionStrategy {

	/**
	 * number of neighbors that are selected
	 */
	private int k = 10;
	
	/**
	 * Sets the number of neighbors.
	 * @param parameters number of neighbors
	 */
	@Override
	public void setParameter(String parameters) {
		k = Integer.parseInt(parameters);
	}

	/**
	 * @see de.ugoe.cs.cpdp.dataselection.PointWiseDataselectionStrategy#apply(weka.core.Instances, weka.core.Instances)
	 */
	@Override
	public Instances apply(Instances testdata, Instances traindata) {
		final Attribute classAttribute = testdata.classAttribute();
		
		final List<Integer> selectedIndex = SetUniqueList.setUniqueList(new LinkedList<Integer>());
		
		final double[][] trainDoubles = new double[traindata.numInstances()][testdata.numAttributes()];
		
		for( int i=0; i<traindata.numInstances() ; i++ ) {
			Instance instance = traindata.instance(i);
			int tmp = 0;
			for( int j=0 ; j<testdata.numAttributes(); j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					trainDoubles[i][tmp++] = instance.value(j);
				}
			}
		}
		
		for( int i=0; i<testdata.numInstances() ; i++ ) {
			Instance testIntance = testdata.instance(i);
			double[] targetVector = new double[testdata.numAttributes()-1];
			int tmp = 0;
			for( int j=0 ; j<testdata.numAttributes(); j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					targetVector[tmp++] = testIntance.value(j);
				}
			}
			
			double farthestClosestDistance = Double.MAX_VALUE;
			int farthestClosestIndex = 0;
			double[] closestDistances = new double[k];
			for( int m=0 ; m<closestDistances.length ; m++ ) {
				closestDistances[m] = Double.MAX_VALUE;
			}
			int[] closestIndex = new int[k];
			
			for( int n=0; n<traindata.numInstances() ; n++ ) {
				double distance = MathArrays.distance(targetVector, trainDoubles[n]);
				
				if( distance<farthestClosestDistance ) {
					closestIndex[farthestClosestIndex] = n;
					closestDistances[farthestClosestIndex] = distance;
					
					farthestClosestIndex = ArrayTools.findMax(closestDistances);
					farthestClosestDistance = closestDistances[farthestClosestIndex];
				}
			}
			for( int index : closestIndex ) {
				selectedIndex.add(index);
			}
		}
		
		final Instances selected = new Instances(testdata);
		selected.delete();
		for( Integer i : selectedIndex) {
			selected.add(traindata.instance(i));
		}
		return selected;
	}

}
