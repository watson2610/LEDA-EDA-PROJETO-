package projeto.algoritmos.ordenacao;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static int[] sortSimple(int[] input) {
        int[] arr = input.clone();
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    public static int[] sortWithShuffle(int[] input) {
        int[] arr = input.clone();
        shuffleArray(arr);
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    public static int[] sortJava(int[] input) {
        int[] arr = input.clone();
        Arrays.sort(arr);
        return arr;
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static void shuffleArray(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
