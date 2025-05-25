package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number > 1) {
            for (int delim = 2; delim <= Math.sqrt(number); delim++) {
                if (number % delim == 0) return false;
            }
        } else {
            throw new IllegalNumberException("Number is negative or 0 or 1!");
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0, mod = 0;
        boolean sign = true;

        if (number < 0) sign = false;
        number = Math.abs(number);
         while (number > 0) {
             mod = number % 10;
             sum += mod;
             number /= 10;
         }
         if (sign == false) {
             sum -= 2*mod;
         }
         return sum;
    }

}
