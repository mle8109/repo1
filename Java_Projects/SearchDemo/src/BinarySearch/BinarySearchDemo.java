package BinarySearch;

public class BinarySearchDemo {
	//recursive
	private int binarySearch(int[] array, int start, int end, int target) {
		if (start >= end) {
			return -1;
		}
		
		int mid = (end + start ) /2;
		
		if (target < array[mid]) {
			return binarySearch(array, start, mid-1, target);
		}
		else if (target > array[mid]) {
			return binarySearch(array, mid + 1, end, target);
		}
		else {
			return mid;
		}
	}
	
	//non-recursive
	private int binarySearchNonRecursive(int[] array, int target) {
		int start = 0;
		int end = array.length;
		int mid = (start + end) / 2; 
		while (start <= end) {
			mid = (start + end) / 2; 
			if (target < array[mid]) {
				end = mid - 1;
			}
			else if (target > array [mid]) {
				start = mid + 1;
			}
			else {
				return mid;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] ar = {1,2,4,6,8,9,10,13,15};
		BinarySearchDemo bsd = new BinarySearchDemo();
		//int result = bsd.binarySearch(ar, 0, ar.length, 13);
		int result = bsd.binarySearchNonRecursive(ar, 7);
		if (result != -1) {
			System.out.println("Found at position: " + result);
		}
		else {
			System.out.println("NOT Found: " + result);
		}
	}
}
