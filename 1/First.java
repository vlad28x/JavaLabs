import java.util.ArrayList;

public class First {
    public static void main(String[] args) {
        int i;
        ArrayList<Integer> evenPositiveInts = new ArrayList<>();//Четные положительные числа
        ArrayList<Integer> oddNegativeInts = new ArrayList<>();//Нечетные отрицательные числа
        for(String str : args) {
            i = Integer.parseInt(str);
            if((i >= 0) && (i % 2 == 0)) evenPositiveInts.add(i);
            else if(i % 2 == -1) oddNegativeInts.add(i);
        }
        int evenCount = evenPositiveInts.size();
        int oddCount = oddNegativeInts.size();
        String comp;
        if(evenCount < oddCount) comp = "<";
        else if(evenCount > oddCount) comp = ">";
        else comp = "=";
        System.out.println("Count of even positive numbers " + comp + " count of odd negative numbers.");
        System.out.println(evenPositiveInts);
        System.out.println(oddNegativeInts);
    }
}