import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Double> numbersArray = new ArrayList<>();
        ArrayList<Character> signsArray = new ArrayList<>();
        boolean success = true;
        String regex = "\\+|-|\\*|\\/";
        String str = "1+2-3*543e308/33.324+6*5/3-9-9";
        String[] numbersString = str.split(regex);
        double[] numbers = new double[numbersString.length];
        try {
            for(int i = 0; i < numbers.length; ++i) {
                numbers[i] = Double.parseDouble(numbersString[i]);
            }
        } catch (NumberFormatException e) {
            success = false;
            System.out.println("Неудача");
        }
        char[] signs = new char[numbers.length - 1];
        int j = 0;
        for(int i = 0; i < str.length(); ++i) {
            if(Character.toString(str.charAt(i)).matches(regex)) {
                signs[j] = str.charAt(i);
                ++j;
            }
        }
        for(int i = 0; i <= signs.length; ++i) {
            if(i == signs.length) numbersArray.add(numbers[i]);
            else if(signs[i] == '+') {
                numbersArray.add(numbers[i]);
                signsArray.add(signs[i]);
            }
            else if(signs[i] == '-') {
                numbersArray.add(numbers[i]);
                signsArray.add(signs[i]);
            }
            else if(signs[i] == '*' || signs[i] == '/') {
                for(j = i + 1; j < signs.length; ++j) {
                    if(signs[j] == '+' || signs[j] == '-') break;
                }
                double result = numbers[i];
                for(int k = i; k < j; ++k) {
                    if(signs[k] == '*') result *= numbers[k + 1];
                    else result /= numbers[k + 1];
                }
                numbersArray.add(result);
                if(j != signs.length) signsArray.add(signs[j]);
                i = j;
            }
        }
        double summ = numbersArray.get(0);
        for (int i = 0; i < signsArray.size(); ++i) {
            if(signsArray.get(i) == '+') summ += numbersArray.get(i + 1);
            else summ -= numbersArray.get(i + 1);
        }
        System.out.println(summ);
        System.out.println(Double.isInfinite(summ));
    }
}
