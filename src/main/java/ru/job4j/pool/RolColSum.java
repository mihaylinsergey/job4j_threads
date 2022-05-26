package ru.job4j.pool;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
    int tempRowSum = 0;
    int tempColSum = 0;
    Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                tempRowSum += matrix[i][j];
                tempColSum += matrix[j][i];
                result[i] = new Sums(tempRowSum, tempColSum);
            }
            tempRowSum = 0;
            tempColSum = 0;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = getTask(matrix, i).get();
        }
        return result;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> {
                    int tempRowSum = 0;
                    int tempColSum = 0;
                    for (int i = 0; i < matrix[start].length; i++) {
                        tempRowSum += matrix[start][i];
                        tempColSum += matrix[i][start];
                    }
                    return new Sums(tempRowSum, tempColSum);
                });
    }
}