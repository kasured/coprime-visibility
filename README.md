
```
#!java

  y
  ^
  |
  12  *   o   *   *   *   o   *   o   *   *   o   o  *                  Using the display form of the problem we can see that
                                                                        the point p0 = (0;0) and any other point pn=(xn;yn)
  11  *   o   o   o   o   o   o   o   o   o   o  *                      can be connected with the line which is described with the linear equation y = A * x
                                                                        Having y and x integers we can represent A as the rational number A = n / d where n os integer nominator
  10  *   o   *   o   *   *   *   o   *   o  *                          and d is integer denominator

  9   *   o   o   *   o   o   *   o   o  *                              Now any pair of points (x1; y1) (x2; y2) is considered to be on the same line iff y1 / x1 == y2 / x2

  8   *   o   *   o   *   o   *   o  *                                  Now let n be the input which defines (n+1)x(n+1) matrix
                                                                        then 3 points (1;1) (0;1) (1;0) are visible to point (0; 0)
  7   *   o   o   o   o   o   o  *
                                                                        This 3 points define A to be 1, infinity and 0
  6   *   o   *   *   *   o  *                                          A = 1 is the line of symmetry and divides the square into 2 triangles

  5   *   o   o   o   o  *                                              Therefor the answer N can be expressed as N = 2 * N' + 2 * n + 1
                                                                        where N' is the number of visible points in the triangle defined by {(2;3),(2;n),(n-1;n)}
  4   *   o   *   o  *
                                                                        points {(1, n1)} where n1 = { 2, 3, 4, ... n } determine the axis A to be normalized rational numbers {2/1, 3/1, 4/1, 5/1, ...}
  3   *   o   o  *                                                      points {(2, n2)} where n2 = { 3, 5, 7, ... N2 } where N2 <= n determine the axis A to be normalized rational numbers {3/2, 5/2, 7/2, 9/2, ...}
                                                                        points {(3, n3)} where n3 = { 4, 5, 7, ... N3 } where N3 <= n determine the axis A to be normalized rational numbers {4/3, 5/3, 7/3, 8/3, ...}
  2   *   o   *                                                         ........
                                                                        in general points {(z, nz)} where 1 < z < n, z < nz <= n and where gcd(z,nz) = 1, i.e. the set of all co-primes to z which does not exceeds n
  1   o   o
                                                                        Indeed lets assume that there is zN which such that zN < nzN <= n but gcd(zN, nzN) > 1. It means that there is a number Z0
  0   *   o                                                             that divides both zN and nzN and also Z0 < zN BUT there is already such point that was already counted on the previous steps which has the same
                                                                        axis as nzN / zN That means that this point should be excluded from the computation
      0   1   2   3   4   5   6   7   8   9   10  11  12  -> x

                                                                        Thus for each step between 2 and n we need to calculate the number of co-primes between (step + 1, n)

                                                                        The efficiency of this algorithm is O(C*n^2*logn) where log n is present by gcd

                                                                        constant C can be influenced by multiple factors
                                                                        1) we can skip the gcd calculations for even numbers on each even step - implemented
                                                                        2) we can make each step executed in the parallel thread (factor of number of cores) - implemented

                                                                        Additional note is that the algorithm does not require additional space so the space complexity is O(1)

                                                                        On my machine with 4 cores it took 10^2 seconds for input matrix (10^5 + 1) X (10^5 + 1)
                                                                        for matrix (10^6 + 1) X (10^6 + 1) it took 3 * 10^4 seconds

                                                                        n = 2 -> 5 points
                                                                        n = 4 -> 13 points
                                                                        n = 231 -> 32_549 points
                                                                        n = 1_000 -> 608_385 points
                                                                        n = 10_000 -> 6_079_301_509 points
                                                                        n = 1_000_000 -> 607_927_104_785 points







```