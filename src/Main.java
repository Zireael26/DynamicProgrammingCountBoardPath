public class Main {

    public static void main(String[] args) {
        int n = 30;
        int[] memoizedArray = new int[n + 1];

//        long start = System.currentTimeMillis();
//        System.out.println("Number of paths from 0 to n are: " + countBoardPaths(0, n));
//        long end = System.currentTimeMillis();
//        System.out.println("The calculation took " + (end - start) + "ms");

        long startMem = System.currentTimeMillis();
        System.out.println("Number of paths from 0 to n are: " + countBoardPathsMemoized(0, n, memoizedArray));
        long endMem = System.currentTimeMillis();
        System.out.println("The calculation took " + (endMem - startMem) + "ms");

        long startTab = System.currentTimeMillis();
        System.out.println("Number of paths from 0 to n are: " + countBoardPathsTabulated(n));
        long endTab = System.currentTimeMillis();
        System.out.println("The calculation took " + (endTab - startTab) + "ms");

        startTab = System.currentTimeMillis();
        System.out.println("Number of paths from 0 to n are: " + countBoardPathsSlider(n));
        endTab = System.currentTimeMillis();
        System.out.println("The calculation took " + (endTab - startTab) + "ms");
    }

    public static int countBoardPaths(int src, int dest) {
        if (src > dest) {
            return 0;
        }

        if (src == dest) {
            return 1;
        }

        int countSrcToDest = 0;
        for (int dice = 1; dice <= 6; dice++) {
            int intermediate = src + dice;
            int countInterToDest = countBoardPaths(intermediate, dest);
            countSrcToDest += countInterToDest;
        }

        return countSrcToDest;
    }

    public static int countBoardPathsMemoized(int src, int dest, int[] memoizedArr) {
        if (src > dest) { // case where we overshoot over 10 and correct our course
            return 0;
        }
        // the case when we are on 10
        if (src == dest) {
            return 1;
        }

        // this prevents recalculation if we already have the value
        if (memoizedArr[src] != 0) {
            return memoizedArr[src];  // precious calculations of the order 6^n saved
        }

        int countSrcToDest = 0;
        for (int dice = 1; dice <= 6; dice++) {
            int intermediate = src + dice; // possible intermediates
            int countInterToDest = countBoardPathsMemoized(intermediate, dest, memoizedArr); //intermediate to dest paths
            countSrcToDest += countInterToDest;         // add all of them to the total count
        }

        // before returning, store the answer in the memoized array
        memoizedArr[src] = countSrcToDest;
        return countSrcToDest;
    }

    public static int countBoardPathsTabulated(int dest) {

        int[] f = new int[dest + 1]; // we declare an array
        f[dest] = 1;                 // and start filling the values we already know

        for (int x = dest - 1; x >= 0; x--) {
            for (int dice = 1; dice <= 6; dice++) {
                if (x + dice <= dest) {
                    f[x] = f[x] + f[x + dice]; // the array f represents the function
                }
            }
        }

        return f[0];
    }

    public static int countBoardPathsSlider(int dest) {
        int[] slider = new int[6];
        slider[0] = 1; // this is f[dest] : known, we need to find f[0]
        // at this point, the rest of the slider's window is outside the f (function array), so they're all 0

        for (int noOfSlides = dest; noOfSlides > 0; noOfSlides--) {
            int newSZero = 0;
            // calculate the new slider[0] value
            for (int i = 0; i < slider.length; i++) {
                newSZero += slider[i];
            }

            // shift the sliding window 1 position to the left
            for (int i = slider.length - 1; i > 0; i--) {
                slider[i] = slider[i - 1];
            }
            // assigning the new value to slider[0]
            slider[0] = newSZero;
        }

        return slider[0];
    }
}
