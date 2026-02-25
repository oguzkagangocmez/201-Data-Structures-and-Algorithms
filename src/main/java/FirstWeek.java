public class FirstWeek {

    public static boolean isPrime(int N){
        if (N <= 1) return false;
        for (int i = 2; i < N; i++) {
            if (N % i == 0)
                return false;
        }
        return true;
    }

    public static boolean isInArray(int K, int[] array){
        for (int i = 0; i < array.length; i++)
            if (array[i] == K)
                return true;
        return false;
    }

    public static void deleteKth(int[] array, int k){
        for (int i = k; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
    }

    public static void insertKth(int[] array, int k, int newValue){
        for (int i = array.length - 2; i >= k; i--) {
            array[i + 1] = array[i];
        }
        array[k] = newValue;
    }

    public static int[] sumColumns(int[][] array){
        int[] result = new int[array[0].length];
        for (int i = 0; i < array[0].length; i++) {
            int sum = 0;
            for (int j = 0; j < array.length; j++) {
                sum += array[j][i];
            }
            result[i] = sum;
        }
        return result;
    }

    public static int[] maxOfColumns(int[][] array){
        int[] result = new int[array[0].length];
        for (int i = 0; i < array[0].length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < array.length; j++) {
                if (array[j][i] > max)
                    max = array[j][i];
            }
            result[i] = max;
        }
        return result;
    }

    public static int kThFibonacciIterative(int k){
        if (k == 0) return 0;
        else if (k == 1) return 1;
        else {
            int fN_2 = 0, fN_1 = 1, fN = 0; // fN will not matter
            for (int i = 1; i < k; i++) {
                fN = fN_2 + fN_1;
                fN_2 = fN_1;
                fN_1 = fN;
            }
            return fN;
        }
    }

    public static int[] positiveDivisorsOfANumber(int N){
        int counter = 0;
        for (int i = 1; i <= N; i++) {
            if (N % i == 0)
                counter++;
        }
        int[] result = new int[counter];
        int resultIndex = 0;
        for (int i = 1; i <= N; i++) {
            if (N % i == 0)
                result[resultIndex++] = i;
        }
        return result;
    }

    public static int iterativeGreatestCommonDivisor(int a, int b){
        if (a == b) return a;
        while (a != 0) {
            System.out.println("a " + a + " b " + b);
            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            } else if (a < b){
                b -= a;
            } else return b;
        }
        return b;
    }

    public static int recursiveGreatestCommonDivisor(int a, int b){
        if (a == b) return a;
        else if (a > b) {
            int temp = b;
            b = a;
            a = temp;
        }
        return recursiveGreatestCommonDivisor(a, b - a);
    }

    public static int recursiveSumUntilN(int N){
        if (N == 1) return N;
        return recursiveSumUntilN(N - 1) + N;
    }

    public static int[] recursiveDigitsBase10(int N){
        if (N >= 0 && N < 10) return new int[]{N};
        else {
            int[] down = recursiveDigitsBase10(N / 10);
            int[] curr = new int[down.length + 1];
            for (int i = 0; i < down.length; i++) {
                curr[i] = down[i];
            }
            curr[curr.length - 1] = N % 10;
            return curr;
        }
    }

}
