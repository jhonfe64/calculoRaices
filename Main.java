import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
* Obtener la raaiz de la funcion f(x)= Cos(πx)-0.3 en el intervalo [0,1] por el
* Método  de bisección
* usar criterio de error de 0.05%
*/
public class Main {

    static double errorPermisible = 0.05;
    // Array de intervalos
    static ArrayList<double[]> intervalos = new ArrayList<>();
    // Array de raices
    static ArrayList<Double> xr = new ArrayList<Double>();
    // Array de erroes
    static ArrayList<Double> errores = new ArrayList<Double>();
    // Array de iteraciones
    static ArrayList<Integer> iteraciones = new ArrayList<Integer>();
    // Array fxr funcion evaluda en la raiz xr
    static ArrayList<Double> fxrArray = new ArrayList<Double>();

    // Ecuación Fx
    public static double fx(double x) {
        // primer intervalo harcodeado
        return Math.cos(Math.PI * x) - 0.3;
    }

    // calculo del bolsano
    // Evalua la función con los valores de los intervalos
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

    // Calculo de la raiz xr
    public static double xr(double valueA, double valueB) {
        BigDecimal xr = new BigDecimal((valueA + valueB) / 2);
        // permite 4 decimales y redondea
        double xrRedondeado = xr.setScale(4, RoundingMode.HALF_UP).doubleValue();
        return xrRedondeado;
    }

    // calculod el error
    public static double errorRelativo(double xrActual, double xrAnterior) {
        BigDecimal Er = new BigDecimal(((xrActual - xrAnterior) / xrActual) * 100);
        // permite 4 decimales y redondea
        double ErRedondeado = Er.setScale(4, RoundingMode.HALF_UP).doubleValue();
        return ErRedondeado;
    }

    public static void main(String[] arguments) {
        //
        Double ultimoErrorCalculado = null;

        // hardcodeamos el primer intervalo lo agregamos al array list intrevalos
        intervalos.add(new double[] { 0.0, 1.0 });

        // recorremos el array list de intervalos que ya tien el primer intervalo
        // cargado
        for (int i = 0; i < intervalos.size(); i++) {
            // identificamos los intevalos a y b
            double a = intervalos.get(i)[0];
            double b = intervalos.get(i)[1];
            iteraciones.add(i);
            // calculamos la raiz mediante la función xr y enviamos los extremos de los
            // intervalos
            double nuevoXr = xr(a, b);
            double fxr = fx(nuevoXr);
            fxrArray.add(fxr);
            // agregamos el nuevo xr (raiz) a el arrai list de raices xr
            xr.add(nuevoXr);

            // si estamos en la segunda iteración
            if (i > 0) {
                // obtenemos la raiz xr anterior i - 1
                double xrAnterior = xr.get(i - 1);
                // calculamso el error relativo usando la funcion error relativo y enviamos el
                // nuevo xr calculado y el xr anterior
                double errorRelativo = Math.abs(errorRelativo(nuevoXr, xrAnterior));
                // asiganamos el ultimo error calculado
                ultimoErrorCalculado = errorRelativo;
                // agregamso el error al array de errores: errores
                errores.add(errorRelativo);
            } else {
                // si estamos en la primera iteracion agregamso null al error
                errores.add(null);
            }
            // si el ultimo error calculado no es null y el ultimo error que se caculo en la
            // iteracion i es menor o igual que el error permisible
            // se detien la ejecución y no se itera mas
            if (ultimoErrorCalculado != null &&
                    ultimoErrorCalculado <= errorPermisible) {
                break;
            }

            // Si el bolsano es true es decir f(a) * f(b) < 0 para el intervalo a (extremo
            // izquierdo) y la nueva raiz (xr) estremo derecho
            if (bolsano(a, nuevoXr)) {
                // se agrega ese intervalo [extremo a, nueva raiz(nuevo extremo derecho)] al
                // array intervalos
                intervalos.add(new double[] { a, nuevoXr });
                // si el bolsano es true para el intervalo nuevoXr (nueva raiz) y extremo b
            } else if (bolsano(nuevoXr, b)) {
                // se agrega ese intervalo [nueva raiz(nuevo extremo izquierdo) extremo b] al
                // array intervalos
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
