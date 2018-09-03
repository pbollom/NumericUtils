public class PrimeManager {
	
	/**
	 * cache of all of the primes (zero-indexed)
	 * true in a value means that the number is prime
	 */
	private boolean[] _primes;

	/**
	 * default constructor. picks an arbitrary value to initialize the cached primes.
	 * if you know the approximate upper bound, use PrimeManager(int maxPrime) instead.
	 */
	public PrimeManager()
	{
		//pick some arbitrary starting size
		this(1000);
	}
	
	/**
	 * constructor to initialize the manager to include primes up to the number specified.
	 * @param maxPrime - the maximal value to check (passed number will be checked)
	 */
	public PrimeManager(int maxPrime)
	{
		this._primes = new boolean[maxPrime];
		
		this.sieve();
	}
	
	/**
	 * Checks whether the passed number is prime.
	 * Note: with poor initialization, this might need to compute many additional primes before returning a value
	 * @param numToCheck - the number we want to check for whether it is prime
	 * @return true if numToCheck is prime. false if it is not prime or if it is less than 1.
	 */
	public boolean IsPrime(int numToCheck)
	{
		//anything less than 1 should be considered not a prime
		if (numToCheck <= 0)
		{
			return false;
		}
		
		//if we haven't computed the right number of primes yet, then compute what we need and a bit more
		if (numToCheck > this._primes.length)
		{
			sieve(2 * numToCheck + 1);
		}
		
		return this._primes[numToCheck - 1];
	}
	
	/**
	 * Returns the maximum number we have checked for whether it is prime
	 * @return the maximum number we have checked for whether it is prime
	 */
	public int MaxNumberChecked()
	{
		return this._primes.length;
	}
	
	/**
	 * Expands the range of prime numbers we have computed.
	 * Updates the internal structure for storing primes as well
	 * @param expandTo - the maximum number we want to compute primes up to
	 */
	private void sieve(int expandTo)
	{
		if (expandTo < this._primes.length) { return; }
		
		this._primes = new boolean[expandTo];
		
		//Rather than recomputing everything, we could copy over values, but because we'd
		//need to take a lot of multiples of primes we already have, at the scale I'm currently
		//working with it doesn't save us a ton.
		//This could definitely be optimized if the usage warrants it, though
		this.sieve();
	}
	
	/**
	 * Base entry point for computing prime numbers.
	 * Doesn't do anything fancy with previously cached primes, but it will
	 * recompute all primes up to the currently configured maximum prime value.
	 */
	private void sieve()
	{	
		int primesToCompute = this._primes.length;
		
		boolean[] sieve = new boolean[primesToCompute]; //true in the sieve means it is not a prime
		this._primes[0] = true;
		
		for (int i = 1; i < primesToCompute; i++)
		{
			if (!sieve[i])
			{
				this._primes[i] = true;
			}
			for (int j = 2; j <= primesToCompute/(i+1); j++)
			{
				sieve[(i+1)*j-1] = true;
			}
		}
	}
}
