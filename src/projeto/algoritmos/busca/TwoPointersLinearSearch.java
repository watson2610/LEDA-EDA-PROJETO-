package projeto.algoritmos.busca;

public class TwoPointersLinearSearch {
    public static int search(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            if (arr[left] == target) return left;
            if (arr[right] == target) return right;
            left++;
            right--;
        }
        return -1;
    }
}
