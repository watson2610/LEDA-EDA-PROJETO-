package projeto.algoritmos.busca;

public class LinearSearch {
    public static int iterative(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    public static int recursive(int[] arr, int target) {
        return recursiveHelper(arr, target, 0);
    }

    private static int recursiveHelper(int[] arr, int target, int index) {
        if (index >= arr.length) return -1;
        if (arr[index] == target) return index;
        return recursiveHelper(arr, target, index + 1);
    }
}
