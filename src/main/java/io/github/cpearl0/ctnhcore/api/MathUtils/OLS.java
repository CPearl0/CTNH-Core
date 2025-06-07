package io.github.cpearl0.ctnhcore.api.MathUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 多元线性回归(OLS)实现
 * @author coshaho
 */
public class OLS {

    /**
     * 多元线性回归(OLS)
     * @param X 特征矩阵，每行是一个样本，每列是一个特征
     * @param y 目标值数组
     * @return 包含系数和截距的Map
     */
    public Map<String, double[]> multiLinearRegression(double[][] X, double[] y) {

        if (X == null || y == null || X.length != y.length || X.length == 0) {
            throw new IllegalArgumentException("输入数据不合法");
        }

        int n = X.length;       // 样本数量
        int k = X[0].length;    // 特征数量

        // 添加截距项(全1列)
        double[][] XWithIntercept = new double[n][k + 1];
        for (int i = 0; i < n; i++) {
            XWithIntercept[i][0] = 1.0; // 截距项
            System.arraycopy(X[i], 0, XWithIntercept[i], 1, k);
        }

        // 计算X'X和X'y
        double[][] XtX = new double[k + 1][k + 1];
        double[] Xty = new double[k + 1];

        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < k + 1; j++) {
                for (int m = 0; m < n; m++) {
                    XtX[i][j] += XWithIntercept[m][i] * XWithIntercept[m][j];
                }
            }

            for (int m = 0; m < n; m++) {
                Xty[i] += XWithIntercept[m][i] * y[m];
            }
        }

        // 解线性方程组 XtX * beta = Xty
        double[] beta = solveLinearSystem(XtX, Xty);

        // 分离截距和系数
        double intercept = beta[0];
        double[] coefficients = new double[k];
        System.arraycopy(beta, 1, coefficients, 0, k);

        // 计算R平方
        double rSquared = calculateRSquared(XWithIntercept, y, beta);

        Map<String, double[]> result = new HashMap<>();
        result.put("coefficients", coefficients);
        result.put("intercept", new double[]{intercept});
        result.put("rSquared", new double[]{rSquared});

        return result;
    }

    /**
     * 解线性方程组 Ax = b
     * 使用高斯消元法
     */
    private double[] solveLinearSystem(double[][] A, double[] b) {
        int n = b.length;

        // 高斯消元
        for (int p = 0; p < n; p++) {
            // 找到主元行
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            // 交换行
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // 消元
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // 回代
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }

        return x;
    }

    /**
     * 计算R平方值
     */
    private double calculateRSquared(double[][] X, double[] y, double[] beta) {
        double sst = 0.0; // 总平方和
        double ssr = 0.0; // 回归平方和
        double yMean = arrayMean(y);

        // 计算总平方和
        for (double value : y) {
            sst += Math.pow(value - yMean, 2);
        }

        // 计算回归平方和
        for (int i = 0; i < y.length; i++) {
            double predicted = predict(X[i], beta);
            ssr += Math.pow(y[i] - predicted, 2);
        }

        // 计算R平方
        return 1 - (ssr / sst);
    }

    /**
     * 使用模型进行预测
     */
    private double predict(double[] x, double[] beta) {
        double prediction = beta[0]; // 截距项
        for (int i = 1; i < beta.length; i++) {
            prediction += beta[i] * x[i - 1];
        }
        return prediction;
    }

    /**
     * 计算数组平均值
     */
    private double arrayMean(double[] array) {
        double sum = 0.0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }
//
//    public static void main(String[] args) {
//        Random random = new Random();
//
//        // 生成测试数据
//        int n = 100; // 样本数量
//        int k = 3;   // 特征数量
//
//        double[][] X = new double[n][k];
//        double[] y = new double[n];
//
//        // 真实系数
//        double[] trueCoefficients = {2.5, -1.2, 0.8, 3.0}; // 包括截距项
//
//        // 生成随机特征数据
//        for (int i = 0; i < n; i++) {
//            X[i][0] = random.nextDouble() * 10;
//            X[i][1] = random.nextDouble() * 5;
//            X[i][2] = random.nextDouble() * 8;
//
//            // 生成目标值(带噪声)
//            y[i] = trueCoefficients[0] +
//                    trueCoefficients[1] * X[i][0] +
//                    trueCoefficients[2] * X[i][1] +
//                    trueCoefficients[3] * X[i][2] +
//                    random.nextGaussian() * 0.5; // 添加高斯噪声
//        }
//
//        // 运行回归
//        OLS regression = new OLS();
//        Map<String, double[]> result = regression.multiLinearRegression(X, y);
//
//        // 输出结果
//        System.out.println("真实系数(包括截距):");
//        for (double coef : trueCoefficients) {
//            System.out.printf("%.4f ", coef);
//        }
//        System.out.println("\n");
//
//        System.out.println("估计系数:");
//        System.out.print("截距: ");
//        for (double val : result.get("intercept")) {
//            System.out.printf("%.4f ", val);
//        }
//        System.out.println();
//
//        System.out.print("系数: ");
//        for (double val : result.get("coefficients")) {
//            System.out.printf("%.4f ", val);
//        }
//        System.out.println();
//
//        System.out.print("R平方: ");
//        for (double val : result.get("rSquared")) {
//            System.out.printf("%.4f ", val);
//        }
//        System.out.println();
//    }
}