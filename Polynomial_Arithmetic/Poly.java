class Poly
{
    private Term first;//The variable first points to the head Term in a linear, singly linked list of Term’s.
    private Term last; // The variable last points to the last Term in the list
    private class Term
    {
        //Each instance of Term represents a term of the polynomial. Term must have three slots: a private int slot called coef, a private int slot called expo, and a private Term slot called next. As the names suggest, coef is the coefficient, expo is the exponent, and next points to the next Term (or to null).
        private int coef;
        private int expo;
        private Term next;
        private Term(int coef, int expo, Term next)
        {
            this.coef = coef;
            this.expo = expo;
            this.next = next;
        }
    }
    public Poly()
    {
        //Constructor. Make a new Term to be used as a head node. Its coef slot may be any int. Its expo slot must be Integer.MAX_VALUE (see below). Let first and last point to that Term. The result is a polynomial with no terms, which is assumed to be 0.
        first = new Term(0, Integer.MAX_VALUE, null);
        last = first;
    }
    public boolean isZero()
    {
        //Test if this is the zero polynomial, with no Term’s, in O(1) time. Hint: remember how head nodes work
        return this.first.next == null;
    }
    public Poly minus()
    {
        //Return a new instance of Poly that is the negation of this, as described in the previous section. You must use the following algorithm. You must not use special cases. You must not change this.
        // Let result point to a new instance of Poly. Let temp point to the first Term in this.
        // While temp is not null, do step 3 repeatedly (a loop).
        // Get temp’s coefficient. Reverse its sign. Make a new Term with the negated coefficient, and Term’s original exponent. Append the new Term to result. Advance temp to the next Term.
        // Finally, return result.
        Poly result = new Poly();
        Term temp = this.first.next;
        while (temp != null)
        {
            int tempCoef = temp.coef;
            tempCoef = -tempCoef;
            int expo = temp.expo;
            Term newTerm = new Term(tempCoef, expo, null);
            result.last.next = newTerm;
            result.last = newTerm;
            temp = temp.next;
        }
        return result;
    }
    public Poly plus(Poly that)
    {
        //Return a new instance of Poly which is the sum of this and that, as described in the previous section. You must use the following algorithm. You must not use special cases. You must not change this or that.
        //Let result point to a new instance of Poly. Let left point to the first Term in this, and let right point to the first Term in that. One or more of left and right may be null.
        //While left is not null, and right is not null, do steps 3 through 5 repeatedly (a loop).
        //If left’s exponent is greater than right’s exponent, then append a copy of left to result, and advance left to the next Term.
        //If right’s exponent is greater than left’s exponent, then append a copy of right to result, and advance right to the next Term.
        //If left and right have equal exponents, then compute the sum of their coefficients. If the sum is zero, then do nothing. Otherwise, append a new Term to result, whose coefficient is the sum, and whose exponent is that of left or right. Advance left to the next Term, and advance right to the next Term.
        //Now we’re done with the loop from steps 2 through 5, so at least one of left and right is null. If left is not null, then append it to result. If right is not null, then append it to result. You need not copy the Term’s of left and right (why?) so this must be done with a single assignment, in O(1) time.
        //Finally, return result. Note that the algorithm works because Term’s appear in descending order of their exponents.
        Poly result = new Poly();
        Term left = this.first.next;
        Term right = that.first.next;
        while (left != null && right != null)
        {
            if (left.expo > right.expo)
            {
                result.last.next = new Term(left.coef, left.expo, null);
                result.last = result.last.next;
                left = left.next;
            }
            else if (right.expo > left.expo)
            {
                result.last.next = new Term(right.coef, right.expo, null);
                result.last = result.last.next;
                right = right.next;
            }
            else
            {
                int sum = left.coef + right.coef;
                if (sum != 0)
                {
                    result.last.next = new Term(sum, left.expo, null);
                    result.last = result.last.next;
                }
                left = left.next;
                right = right.next;
            }
        }
        if (left != null)
        {
            result.last.next = left;
            result.last = this.last;
        }
        else if (right != null)
        {
            result.last.next = right;
            result.last = that.last;
        }
        return result;
    }
    public Poly plus(int coef, int expo)
    {
        //Throw an IllegalArgumentException if coef is zero, if expo is negative, or if expo is greater than or equal to the exponent of the last Term in this. Otherwise, make a new Term whose slots are coef and expo. Append the new Term to this, and reset last so it points to the new Term, all in O(1) time. You must not use special cases. Return this.
        if (coef == 0)
        {
            throw new IllegalArgumentException("The coefficient is zero.");
        }
        if (expo < 0)
        {
            throw new IllegalArgumentException("The exponent is negative.");
        }
        if (this.last != null && expo >= this.last.expo)
        {
            throw new IllegalArgumentException("The exponent can not be greater than the exponent of the last term.");
        }
        Term newTerm = new Term(coef, expo, null);
        if (last == null)
        {
            first = newTerm;
            last = newTerm;
        }
        else
        {
            last.next = newTerm;
            last = newTerm;
        }
        return this;
    }
    public String toString()
    {
        //Return a string that represents this, for printing. You must use a StringBuffer or a StringBuilder. See the example section below to find out what the returned string must look like.
        StringBuilder str = new StringBuilder();
        Term temp = first.next;
        boolean firstTerm = true;
        while (temp != null)
        {
            if (temp.coef != 0)
            {
                if (!firstTerm)
                {
                    if (temp.coef > 0)
                    {
                        str.append(" + ");
                    }
                    else
                    {
                        str.append(" - ");
                    }
                    str.append(Math.abs(temp.coef));
                    if (temp.expo >= 0)
                    {
                        str.append("x^").append(temp.expo);
                    }
                }
                else
                {
                    str.append(temp.coef);
                    if (temp.expo >= 0)
                    {
                        str.append("x^").append(temp.expo);
                    }
                    firstTerm = false;
                }
            }
            temp = temp.next;
        }
        if (str.length() == 0)
        {
            return "0";
        }
        return str.toString();
    }
    public static void main(String[] args)
    {
        Poly p = new Poly().plus(3,5).plus(2,4).plus(2,3).plus(-1,2).plus(5,0);
        Poly q = new Poly().plus(7,4).plus(1,2).plus(-4,1).plus(-3,0);
        Poly z = new Poly();
        Poly r = new Poly().plus(1, 1);
        Poly s = new Poly().plus(1, 1).plus(1, 0);

        System.out.println(p);                 // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
        System.out.println(q);                 // 7x⁴ + 1x² - 4x¹ - 3x⁰
        System.out.println(z);                 // 0
        System.out.println(r);                 // 1x¹
        System.out.println(s);                 // 1x¹ + 1x⁰

        System.out.println(p.minus());         // -3x⁵ - 2x⁴ - 2x³ + 1x² - 5x⁰
        System.out.println(q.minus());         // -7x⁴ - 1x² + 4x¹ + 3x⁰
        System.out.println(z.minus());         // 0
        System.out.println(r.minus());         // -1x¹

        System.out.println(p.plus(q));         // 3x⁵ + 9x⁴ + 2x³ - 4x¹ + 2x⁰
        System.out.println(p.plus(z));         // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
        System.out.println(p.plus(q.minus())); // 3x⁵ - 5x⁴ + 2x³ - 2x² + 4x¹ + 8x⁰
        System.out.println(p.plus(r.minus())); // 3x⁵ + 2x⁴ + 2x³ - 1x² - 1x¹ + 5x⁰
        System.out.println(p.plus(z));         // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
    }
}