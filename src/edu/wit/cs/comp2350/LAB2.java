package edu.wit.cs.comp2350;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * 
 * @author kreimendahl
 */


public class LAB2 {

	// TODO: document this method
	public static float heapAdd(float[] a) {
		// TODO: implement this method

		a=heapSort(a);

		while (a.length != 1)
		{
			a = extract(a);
			a = heapSort(a);
		}

		return a[0];
	}

	public static float[] heapSort(float[] a) {

		for (int i = (a.length / 2) - 1; i >=0 ; i--)
		{
			a = pushDown(a, i);
		}
		return a;
	}

	public static float[] pullUp(float[] a,int i){

		//never used. didnt finish implementing
		int parent = (i - 1) /2;
		float temp;

		try {
			if (a[i] < a[parent])
			{
				temp = a[i];
				a[i] = a[parent];
				a[parent] = temp;
				parent = (i - 1) /2;
				pullUp(a, parent);

			}

		} catch (Exception ex)
		{

			return a;
		}

		return a;
	}

	public static float[] pushDown(float[] arr, int i) {

		float min = arr[i];
		float temp = 0;
		int num;

		try {
			if ((2 * i) + 1 > arr.length)
				min = arr[i];
			else if ((2 * i) + 2 > arr.length - 1)
				min = Math.min(arr[i], arr[(2 * i) + 1]);
			else if ((2 * i) + 2 <= arr.length - 1)
				min = Math.min(arr[i], Math.min(arr[(2 * i) + 1], arr[(2 * i) + 2]));
			//
			if (min == arr[i])
				num = i;
			else if (min == arr[(2*i) + 1])
				num = (2*i) + 1;
			else if (min == arr[(2*i) + 2])
				num = (2*i) + 2;
			else
				num = i;

			if (min != arr[i])
			{
				temp = arr[i];
				arr[i] = min;
				arr[num] = temp;
				pushDown(arr, num);
			}

		} catch (ArrayIndexOutOfBoundsException ex)
		{}

		return arr;
	}

	//extract method
	public static float[] extract(float[] a)
	{
		int num;
		float[] arr = new float[a.length-1];
		float total = 0;

		if (a.length == 2)
		{
			total = a[0] + a[1];
			for (int i = 0; i <arr.length; i++)
			{
				arr[i] = total;
			}
			return arr;
		}


		if (a[0] - a[1] < a[0] - a[2])
		{
			total = a[0] + a[2];
			num = 1;
			a[0] = a[num];
			for (int i = 1; i <a.length - 2; i++)
			{
				a[i] = a[i +2];
			}
			a[a.length-2] = total;
		}
		else if (a[0] - a[1] > a[0] - a[2])
		{
			total = a[0] + a[1];
			num = 2;
			for (int i = 0; i < a.length - 2; i++)
			{
			a[i] = a[i + num];
			}
			a[a.length-2] = total;

		}
		else {
			total = a[0] + a[1];
			num = 2;
			for (int i = 0; i < a.length - 2; i++) {
				a[i] = a[i + num];
			}
			a[a.length - 2] = total;
		}

		for (int i = 0; i <arr.length; i++)
		{
			arr[i] = a[i];
		}

		return arr;
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	// sum an array of floats sequentially
	public static float seqAdd(float[] a) {
		float ret = 0;
		
		for (int i = 0; i < a.length; i++)
			ret += a[i];
		
		return ret;
	}

	// sort an array of floats and then sum sequentially
	public static float sortAdd(float[] a) {
		Arrays.sort(a);
		return seqAdd(a);
	}

	// scan linearly through an array for two minimum values,
	// remove them, and put their sum back in the array. repeat.
	public static float min2ScanAdd(float[] a) {
		int min1, min2;
		float tmp;
		
		if (a.length == 0) return 0;
		
		for (int i = 0, end = a.length; i < a.length - 1; i++, end--) {
			
			if (a[0] < a[1]) { min1 = 0; min2 = 1; }	// initialize
			else { min1 = 1; min2 = 0; }
			
			for (int j = 2; j < end; j++) {		// find two min indices
				if (a[min1] > a[j]) { min2 = min1; min1 = j; }
				else if (a[min2] > a[j]) { min2 = j; }
			}
			
			tmp = a[min1] + a[min2];	// add together
			if (min1<min2) {			// put into first slot of array
				a[min1] = tmp;			// fill second slot from end of array
				a[min2] = a[end-1];
			}
			else {
				a[min2] = tmp;
				a[min1] = a[end-1];
			}
		}
		
		return a[0];
	}

	// read floats from a Scanner
	// returns an array of the floats read
	private static float[] getFloats(Scanner s) {
		ArrayList<Float> a = new ArrayList<Float>();

		while (s.hasNextFloat()) {
			float f = s.nextFloat();
			if (f >= 0)
				a.add(f);
		}
		return toFloatArray(a);
	}

	// copies an ArrayList to an array
	private static float[] toFloatArray(ArrayList<Float> a) {
		float[] ret = new float[a.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}


	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the adding algorithm to use ([h]eap, [m]in2scan, se[q], [s]ort): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the positive floats that you would like summed: ");
		float[] values = getFloats(s);
		float sum = 0;

		s.close();

		if (values.length == 0) {
			System.out.println("You must enter at least one value");
			System.exit(0);
		}
		else if (values.length == 1) {
			System.out.println("Sum is " + values[0]);
			System.exit(0);
			
		}
		
		switch (algo) {
		case 'h':
			sum = heapAdd(values);
			break;
		case 'm':
			sum = min2ScanAdd(values);
			break;
		case 'q':
			sum = seqAdd(values);
			break;
		case 's':
			sum = sortAdd(values);
			break;
		default:
			System.out.println("Invalid adding algorithm");
			System.exit(0);
			break;
		}

		System.out.printf("Sum is %f\n", sum);		

	}

}
