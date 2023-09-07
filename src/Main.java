import java.util.Stack;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Ingrese una expresión infija (sin espacios) o 'salir' para terminar: ");
            String expresion = scanner.nextLine();

            if (expresion.equals("salir")) {
                break;
            }

            String resultado = convertirInfijoASufijo(expresion);

            if (resultado.equals("Expresión no válida")) {
                System.out.println("Expresión no válida debido a paréntesis desequilibrados o expresión mal formada.");
            } else {
                System.out.println("Expresión sufija: " + resultado);
            }
        }
    }

    public static String convertirInfijoASufijo(String expresionInfija) {
        StringBuilder expresionSufija = new StringBuilder();
        Stack<Character> pilaOperadores = new Stack<>();
        int numParentesisAbiertos = 0;
        int numParentesisCerrados = 0;

        for (char c : expresionInfija.toCharArray()) {
            if (esOperandoN(c) || Character.isLetter(c)) {
                expresionSufija.append(c);
            } else if (c == '(') {
                pilaOperadores.push(c);
                numParentesisAbiertos++;
            } else if (c == ')') {
                numParentesisCerrados++;
                if (numParentesisCerrados > numParentesisAbiertos) {
                    return "Expresión no válida";
                }
                while (!pilaOperadores.isEmpty() && pilaOperadores.peek() != '(') {
                    expresionSufija.append(" ").append(pilaOperadores.pop());
                }
                if (!pilaOperadores.isEmpty() && pilaOperadores.peek() == '(') {
                    pilaOperadores.pop();
                } else {
                    return "Expresión no válida";
                }
            } else if (esOperador(c)) {
                while (!pilaOperadores.isEmpty() && obtenerPrecedencia(c) <= obtenerPrecedencia(pilaOperadores.peek())) {
                    expresionSufija.append(" ").append(pilaOperadores.pop());
                }
                pilaOperadores.push(c);
            } else {
                return "Expresión no válida";
            }
        }

        while (!pilaOperadores.isEmpty()) {
            expresionSufija.append(" ").append(pilaOperadores.pop());
        }

        if (numParentesisAbiertos != numParentesisCerrados) {
            return "Expresión no válida";
        }

        return expresionSufija.toString().trim();
    }

    private static boolean esOperandoN(char c) {
        return Character.isDigit(c);
    }

    private static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int obtenerPrecedencia(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        }
        return 0;
    }
}
