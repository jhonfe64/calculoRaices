import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
    * Obtener la raíz de la funcion f(x)= Cos(πx)-0.3 en el intervalo [0,1] por el
    * Método  de bisección
    * Usar criterio de error de 0.05%
*/

public class Main {

    static double errorPermisible = 0.05;
    // Array de intervalos
    static ArrayList<double[]> intervalos = new ArrayList<>();
    // Array de Array de raíces
    static ArrayList<Double> xr = new ArrayList<Double>();
    // Array de errores
    static ArrayList<Double> errores = new ArrayList<Double>();
    // Array de iteraciones
    static ArrayList<Integer> iteraciones = new ArrayList<Integer>();
    // Array fxr función evaluada en la raíz xr
    static ArrayList<Double> fxrArray = new ArrayList<Double>();

    // Ecuación Fx
    public static double fx(double x) {
        return Math.cos(Math.PI * x) - 0.3;
    }

    // Cálculo del bolsano
    // Evalúa la función con los valores de los intervalos
    public static boolean bolsano(double valueA, double valueB) {
        double fa = fx(valueA);
        double fb = fx(valueB);
        double numeroBolsano = fa * fb;
        if (numeroBolsano < 0) {
            return true;
        } else {
            return false;
        }
    }

    // Calculo de la raíz xr
    public static double xr(double valueA, double valueB) {
        BigDecimal xr = new BigDecimal((valueA + valueB) / 2);
        // Permite 4 decimales y redondea
        double xrRedondeado = xr.setScale(4, RoundingMode.HALF_UP).doubleValue();
        return xrRedondeado;
    }

    // Cálculo del error
    public static double errorRelativo(double xrActual, double xrAnterior) {
        BigDecimal Er = new BigDecimal(((xrActual - xrAnterior) / xrActual) * 100);
        // Permite 4 decimales y redondea
        double ErRedondeado = Er.setScale(4, RoundingMode.HALF_UP).doubleValue();
        return ErRedondeado;
    }

    public static void main(String[] arguments) {
        // Icializa el error en null
        Double ultimoErrorCalculado = null;

        // Hardcodeamos el primer intervalo; lo agregamos al array list intervalos.
        intervalos.add(new double[] { 0.0, 1.0 });

        // Recorremos el array list de intervalos que ya tiene el primer intervalo
        // cargado
        for (int i = 0; i < intervalos.size(); i++) {
            // Identificamos los extremos de los intervalos a y b
            double a = intervalos.get(i)[0];
            double b = intervalos.get(i)[1];
            iteraciones.add(i);
            // Calculamos la raíz mediante la función xr y enviamos los extremos de los
            // intervalos
            double nuevoXr = xr(a, b);
            double fxr = fx(nuevoXr);
            fxrArray.add(fxr);
            // Agregamos el nuevo xr (raíz) al array list de raíces xr
            xr.add(nuevoXr);

            // Si estamos en la segunda iteración
            if (i > 0) {
                // Obtenemos la raíz xr anterior i - 1
                double xrAnterior = xr.get(i - 1);
                // Calculamos el error relativo usando la función error relativo y enviamos el
                // nuevo xr calculado y el xr anterior
                double errorRelativo = Math.abs(errorRelativo(nuevoXr, xrAnterior));
                // Asignamos el último error calculado
                ultimoErrorCalculado = errorRelativo;
                // Agregamos el error al array de errores: errores
                errores.add(errorRelativo);
            } else {
                // Si estamos en la primera iteración, agregamos null al error
                errores.add(null);
            }
            // Si el último error calculado no es null y el último error que se calculó en la
            // iteración i es menor o igual que el error permisible
            // Se detiene la ejecución y no se itera más.
            if (ultimoErrorCalculado != null &&
                    ultimoErrorCalculado <= errorPermisible) {
                break;
            }

            // Si el bolzano es true, es decir, f(a) * f(b) < 0 para el intervalo a (extremo izquierdo) y la nueva raíz (xr) extremo derecho
            if (bolsano(a, nuevoXr)) {
                // Se agrega ese intervalo [extremo a, nueva raíz (nuevo extremo derecho)] al array intervalos
                intervalos.add(new double[] { a, nuevoXr });
                // Si el bolsano es true para el intervalo nuevo Xr (nueva raíz) y extremo b
            } else if (bolsano(nuevoXr, b)) {
                // Se agrega ese intervalo [nueva raíz (nuevo extremo izquierdo) extremo b] al array intervalos
                intervalos.add(new double[] { nuevoXr, b });
            }
        }

        // Tabulación de datos
        System.out.printf("%-20s %-15s %-12s %-12s %-12s%n", "Intervalo", "Raices Xr", "Error", "# iteración", "f(xr)");
        System.out.println("----------------------------------------------------------------------------------");

        // Se recorre el array de raíces Xr y se imprimen los datos
        for (int i = 0; i < xr.size(); i++) {

            String intervaloTexto = Arrays.toString(intervalos.get(i));
            double valorXr = xr.get(i);
            Double valorError = errores.get(i);
            Integer iteracion = iteraciones.get(i);
            Double fxr = fxrArray.get(i);

            if (valorError == null) {
                System.out.printf(
                        "%-20s %-15.4f %-12s %-12d %-12.4f%n",
                        intervaloTexto,
                        valorXr,
                        "—",
                        iteracion,
                        fxr);
            } else {
                System.out.printf(
                        "%-20s %-15.4f %-12.4f %-12d %-12.4f%n",
                        intervaloTexto,
                        valorXr,
                        valorError,
                        iteracion,
                        fxr);
            }
        }
    }
}
