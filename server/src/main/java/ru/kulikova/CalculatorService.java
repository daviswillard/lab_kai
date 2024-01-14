package ru.kulikova;

import java.util.ArrayList;

public class CalculatorService {

  private final char[] operands = {'+', '-'};

  public double calculate(String expression) {
    ArrayList<Double> numbers = getNumbersFromExpression(expression);

    double result = 0;
		for (double num : numbers) {
			result += num;
		}

    return result;
  }

  private ArrayList<Double> getNumbersFromExpression(String expression) {
    ArrayList<Double> numbers = new ArrayList<>();

    // если изначально идёт положительное число, то добавляем в начале '+'
		if (!isMathOperation(expression.charAt(0))) {
			expression = "+" + expression;
		}

    StringBuilder curValue = new StringBuilder();
		if (expression.charAt(0) == '-') {
			curValue.append(expression.charAt(0));
		}
    for (int i = 1; i < expression.length(); i++) {
      char curSym = expression.charAt(i);
      if (isMathOperation(curSym)) {
        numbers.add(Double.parseDouble(curValue.toString()));
        curValue.delete(0, curValue.capacity());

				if (curSym == '-') {
					curValue.append(expression.charAt(i));
				}
      } else {
        curValue.append(expression.charAt(i));
      }
    }
    // в конце строки никакого математического знака не будет, поэтому просто парсим последнее число
    numbers.add(Double.parseDouble(curValue.toString()));

    return numbers;
  }

  private boolean isMathOperation(char symbol) {
    for (char c : operands) {
			if (c == symbol) {
				return true;
			}
    }
    return false;
  }

}