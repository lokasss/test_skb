package fapp.bush.skb;

import java.util.HashMap;


/**
 * Created by andbu_001 on 01.08.2017.
 */

public class TestParser {
    private HashMap<String, Double> variables;

    public TestParser() {
        variables = new HashMap<String, Double>();
    }

    public void setVariable(String variableName, Double variableValue) {
        variables.put(variableName, variableValue);
    }

    public Double getVariable(String variableName) {
        if (!variables.containsKey(variableName)) {
            return 0.0;
        }
        return variables.get(variableName);
    }

    public double Parse(String s) throws Exception {
        Result result = PM(s);
        return result.acc;
    }

    private Result PM(String s) throws Exception {
        Result current = DividMilt(s);
        double acc = current.acc;

        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = DividMilt(next);
            if (sign == '+') {
                acc += current.acc;
            } else {
                acc -= current.acc;
            }
        }
        return new Result(acc, current.rest);
    }

    private Result Bracket(String s) throws Exception {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = PM(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                throw new Exception("Количество открытых и закрытых скобок не равно!");
            }
        return r;
        }
        return ErrorBracket(s);
    }

    private Result ErrorBracket(String s) throws Exception {
        //Обработка отсутствия арифметического знака действия перед скобками пр. 5(5+5)
        int i = 0;
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || (Character.isLetter(s.charAt(i)) && i > 0))) {
            i++;
        }
        if (s.length() > i && s.charAt(i) == '(') { //следующий символ скобка значит ошибка
            throw new Exception("Пропущен арифметический знак перед скобкой");
        }

        return Num(s);
    }

    private Result DividMilt(String s) throws Exception
    {
        Result current = Bracket(s);

        double acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;

            String next = current.rest.substring(1);
            Result right = Bracket(next);

            if (sign == '*') {
                acc *= right.acc;
            } else {
                acc /= right.acc;
            }

            current = new Result(acc, right.rest);
        }
    }

    private Result Num(String s) throws Exception
    {
        int i = 0;
        int dot = 0;
        boolean negative = false;
        if( s.charAt(0) == '-' ){  // унарный минус
            negative = true;
            s = s.substring( 1 );
        }
        // ищем цифры и точки
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            // должна быть только одна точка
            if (s.charAt(i) == '.' && ++dot > 1) {
                throw new Exception("В одном из чисел больше одной точки");
            }
            i++;
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if( negative ) dPart = -dPart;
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

}
