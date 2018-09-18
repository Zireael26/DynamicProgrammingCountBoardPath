public class Main {

    public static void main(String[] args) {
        int n = 30;
        int[] memoizedArray = new int[n+1];

        long start = System.currentTimeMillis();
        System.out.println("Number of paths from 0 to n are: " + countBoardPaths(0, n));
        long end = System.currentTimeMillis();
        System.out.println("The calculation took " + (end-start) + "ms");

        long startMem = System.currentTimeMillis();
        System.out.println("Number of paths from 0 to n are: " + countBoardPathsMemoized(0, n, memoizedArray));
        long endMem = System.currentTimeMillis();
        System.out.println("The calculation took " + (endMem-startMem) + "ms");
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
        if (src > dest) {
            return 0;
        }

        if (src == dest) {
            return 1;
        }

        if (memoizedArr[src] != 0) {
            return memoizedArr[src];
        }

        int countSrcToDest = 0;
        for (int dice = 1; dice <= 6; dice++) {
            int intermediate = src + dice;
            int countInterToDest = countBoardPathsMemoized(intermediate, dest, memoizedArr);
            countSrcToDest += countInterToDest;
        }

        memoizedArr[src] = countSrcToDest;
        return countSrcToDest;
    }
}
