package SharedClasses;

public class Pair <T,R> 
{
	private T key;
	private R value;
	
	public Pair(T key,R value)
	{
		this.key = key;
		this.value = value;
	}
	
	public T getKey()
	{
		return this.key;
	}
	public R getValue()
	{
		return this.value;
	}
	
	public void getKey(T key)
	{
		this.key = key;
	}
	public void getValue(R value)
	{
		this.value = value;
	}
	public void setKey(T key) {
		this.key = key;
	}

	public void setValue(R value) {
		this.value = value;
	}
}
