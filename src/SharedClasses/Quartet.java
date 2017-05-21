package SharedClasses;


public class Quartet<K,V,T,S> {

    /**
     * Key of this <code>quartet</code>.
     */
    private K key;

    /**
     * Gets the key for this quartet.
     * @return key for this quartet
     */
    public K getKey() { return key; }

    /**
     * Value of this this <code>quartet</code>.
     */
    private V value1;

    /**
     * Gets the value for this quartet.
     * @return value for this quartet
     */
    public V getValue1() { return value1; }
    /**
     * Value of this this <code>quartet</code>.
     */
    private T value2;

    /**
     * Gets the value for this quartet.
     * @return value for this quartet
     */
    public T getValue2() { return value2; }
    /**
     * Creates a new quartet
     * @param key The key for this quartet
     * @param value The value to use for this quartet
     */
    private S value3;

    /**
     * Gets the value for this quartet.
     * @return value for this quartet
     */
    public S getValue3() { return value3; }
    /**
     * Creates a new quartet
     * @param key The key for this quartet
     * @param value The value to use for this quartet
     */
    public Quartet(K key, V value1,T value2,S value3) {
        this.key = key;
        this.value1 = value1;
        this.value2=value2;
        this.value3=value3;
    }

    /**
     * <p><code>String</code> representation of this
     * <code>quartet</code>.</p>
     *
     * <p>The default name/value delimiter '=' is always used.</p>
     *
     *  @return <code>String</code> representation of this <code>quartet</code>
     */
    @Override
    public String toString() {
        return key + "=" + value1;
    }

    /**
     * <p>Generate a hash code for this <code>quartet</code>.</p>
     *
     * <p>The hash code is calculated using both the name and
     * the value of the <code>quartet</code>.</p>
     *
     * @return hash code for this <code>quartet</code>
     */
    @Override
    public int hashCode() {
        // name's hashCode is multiplied by an arbitrary prime number (13)
        // in order to make sure there is a difference in the hashCode between
        // these two parameters:
        //  name: a  value: aa
        //  name: aa value: a
        return key.hashCode() * 13 + (value1 == null ? 0 : value1.hashCode());
    }

}