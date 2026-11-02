package projeto.algoritmos.busca;

public class BinarySearch {
    public static int iterative(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    public static int recursive(int[] arr, int target) {
        return recursiveHelper(arr, target, 0, arr.length - 1);
    }

    private static int recursiveHelper(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) return recursiveHelper(arr, target, mid + 1, right);
        else return recursiveHelper(arr, target, left, mid - 1);
    }
}
