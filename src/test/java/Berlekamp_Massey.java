import java.lang.*;
import java.util.*;

public class Berlekamp_Massey {
    /* Reproduces the Berlekamp-Massey algortihm.
     *
     * Please note that the C,B are representing the coefficients of the connection polynomial,
     * as opposed to the LFSR class where the array int c[] represents the physical connections.
     * To pass from one equivalent for to another, simply drop the first element of the connection
     * polynomial as :
     *		C(D) = 1 + c_0*D + c_1*D^2 + ... + c_n*D^(n+1)
     * or more generally :
     *		C(D) = 1 + c_1*D + c_2*D^2 + ... + c_n*D^n
     */
    private int modulo = 2;
    private int [] sequence;
    private int [] C = {1};
    private int [] B = {1};
    private int L = 0;
    private int x = 1;
    private int b = 1;
    private int N = 0;

    public Berlekamp_Massey(int[] seq){
        this.sequence = seq.clone();
    }

    public void print(){
        System.out.println("the coefficients are");
        System.out.println(Arrays.toString(this.C));
        System.out.println("iteration :" + this.N);

    }

    public void iteration(){
        if(N == this.sequence.length){
//            System.out.println("Finished");
        }
        else {
//            System.out.println("N:" + this.N  );
//            System.out.println("L:" + this.L);
//            System.out.println("C:" + Arrays.toString(this.C));
//            System.out.println("X:" + this.x);
//            System.out.println("S:" + Arrays.toString(this.sequence));
//            System.out.println("Sn:" + this.sequence[this.N]);
            int d = this.sequence[this.N];
            for (int i = 1; i <= this.L; i++){
                d += this.C[i] * this.sequence[this.N -i];
            }
            d = d % this.modulo;
//            System.out.println("D::" + d);
            if (d == 0){
                this.x += 1;
            }
            else if (2*this.L <= this.N){
                int [] temp = new int[Math.max(this.C.length, this.B.length+this.x)];
                for(int i = 0; i < temp.length; i++){
                    temp[i] = 0;
                    if (i < this.C.length){
                        temp[i] = C[i];
                    }
                    if (i-this.x < this.B.length && i-this.x >= 0) {
                        temp[i] += this.B[i-this.x];
                    }
                    temp[i] = temp[i] % this.modulo;
                }
                this.L = this.N + 1 - this.L;
                this.B = this.C;
                this.C = temp;
                this.b = d;                //We work in binary fields only, therefore it should alwas be 1
                this.x = 1;
            }
            else {
                for(int i = 0; i < this.C.length; i++){
                    if (i - this.x < this.B.length && i >= this.x){

                        // Some simplifications due to binary fields are done
                        this.C[i] = (this.C[i] + this.B[i- this.x]) % 2;
                    }
                }
                this.x += 1;
            }
            this.N += 1;
        }
    }

    public void correctL() {
        for (int i = this.getC().length - 1; i >= 0; --i) {
            if (this.getC()[i] == 1) {
                this.setL(i);
                return;
            }
        }
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int[] getC() {
        return C;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public int[] getSequence() {
        return sequence;
    }

    public void setSequence(int[] sequence) {
        this.sequence = sequence;
    }

    public void setC(int[] c) {
        C = c;
    }

    public int[] getB() {
        return B;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setB(int[] b) {
        B = b;
    }

    public void setL(int l) {
        L = l;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getL() {
        return L;
    }

    @Override
    public String toString()
    {
        return "I'm the Berlekamp-Massay Algorithm";
    }
}  