public class GenericComplex{

    static class Complex<T extends Number> {

        private T real;   
        private T imag;   

        // Constructor
        public Complex(T real, T imag) {
            this.real = real;
            this.imag = imag;
        }

        public T getReal() {
            return real;
        }

        public T getImag() {
            return imag;
        }

        // Addition 
        public Complex<Double> add(Complex<? extends Number> other) {

            double r = this.real.doubleValue() + other.real.doubleValue();
            double i = this.imag.doubleValue() + other.imag.doubleValue();

            return new Complex<>(r, i);
        }

        // Subtraction 
        public Complex<Double> subtract(Complex<? extends Number> other) {

            double r = this.real.doubleValue() - other.real.doubleValue();
            double i = this.imag.doubleValue() - other.imag.doubleValue();

            return new Complex<>(r, i);
        }

        @Override
        public String toString() {
            return "(" + real + " + " + imag + "i)";
        }
    }
    
    public static void main(String[] args) {

        Complex<Integer> c1 = new Complex<>(3, 4);
        Complex<Double>  c2 = new Complex<>(1.5, -2.0);

        Complex<Double> sum  = c1.add(c2);
        Complex<Double> diff = c1.subtract(c2);

        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);
        System.out.println("sum = " + sum);
        System.out.println("diff = " + diff);
    }
}
