package transportProject;

import java.util.ArrayList;

public class mergSortArrivalTime {
	/**
     * Sorts an ArrayList of arrivalTimes by trip ID.
     *
     * @param a: an ArrayList of arrivalTimes
     * @return after the method returns, the ArrayList of arrivalTimes will be sorted by trip ID.
     */
	public static ArrayList<arrivalTime> mergeSort(ArrayList<arrivalTime> list){       
        mergeRecursive(0, list.size()-1, list);
        return list;
    }
    
    public static void mergeRecursive(int startI,int endI, ArrayList<arrivalTime> list){
        
        //Divide till you breakdown your list to single element
        if(startI<endI && (endI-startI)>=1){
            int mid = (endI + startI)/2;
            mergeRecursive(startI, mid, list);
            mergeRecursive(mid+1, endI, list);        
            
            //merging Sorted array produce above into one sorted array
            merge(startI,mid,endI,list);            
        }       
    }   
    
    public static void merge(int startI,int midI,int endI, ArrayList<arrivalTime> list){
        
        //Auxiliary Array
        ArrayList<arrivalTime> mergedSortedArray = new ArrayList<arrivalTime>();
        
        int leftIndex = startI;
        int rightIndex = midI+1;
        
        while(leftIndex<=midI && rightIndex<=endI){
            if(list.get(leftIndex).trip_id <=list.get(rightIndex).trip_id){
                mergedSortedArray.add(list.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(list.get(rightIndex));
                rightIndex++;
            }
        }       
        
        while(leftIndex<=midI){
            mergedSortedArray.add(list.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex<=endI){
            mergedSortedArray.add(list.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startI;
        while(i<mergedSortedArray.size()){
            list.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }
}
