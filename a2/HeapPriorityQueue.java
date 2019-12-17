/**
 *
 * Array Heap implimentation of a priority queue
 * 
 */
public class HeapPriorityQueue <K extends Comparable, V> implements PriorityQueue <K, V> {

	private Entry<K, V> buffer;
	private Entry[] minHeap;
	private Entry[] maxHeap;
	private int minTail;
	private int maxTail;

	/**
	 * Default constructor
	 */
	public HeapPriorityQueue() {
		this(100);
	}


	/**
	 * HeapPriorityQueue constructor with max storage of size elements
	 */
	public HeapPriorityQueue( int size ) {
		minHeap = new Entry[size];
		maxHeap = new Entry[size];
		minTail = -1;
		maxTail = -1;
		buffer = null;
	}


	/****************************************************
	*
	*             Priority Queue Methods
	*
	****************************************************/

	/**
	 * Returns the number of items in the priority queue.
	 * O(1)
	 * @return number of items
	 */
	public int size() {
		if(buffer==null){
			return minTail + maxTail + 2;
		}
		return minTail + maxTail + 2 +1;
	} /* size */


	/**
	 * Tests whether the priority queue is empty.
	 * O(1)
	 * @return true if the priority queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return minTail < 0 && maxTail < 0 && buffer==null;
	} /* isEmpty */


	/**
	 * Inserts a key-value pair and returns the entry created.
	 * O(log(n))
	 * @param key     the key of the new entry
	 * @param value   the associated value of the new entry
	 * @return the entry storing the new key-value pair
	 * @throws IllegalArgumentException if the heap is full
	 */
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		if(buffer==null){
			buffer = new Entry(key,value);
			return buffer;
		}

		if(minTail==minHeap.length-1 || maxTail==maxHeap.length-1)
			throw new IllegalArgumentException("Heap Overflow");

		Entry newEntry = new Entry(key,value);
		buffer.setAssociate(newEntry);
		newEntry.setAssociate(buffer);
		insert(newEntry,buffer);
		buffer=null;
		return newEntry;
	}


	/**
	 * Returns (but does not remove) an entry with minimal key.
	 * O(1)
	 * @return entry having a minimal key (or null if empty)
	 */
	public Entry<K, V> min() {
		if(isEmpty())
			return null;

		Entry<K, V> ret;

		if(minHeap[0]==null){
			ret = buffer;
			buffer=null;
			return ret;
		}
		if(buffer.getKey().compareTo(minHeap[0].getKey()) >= 0 ){
			return buffer;
		}
		return minHeap[0];
	} /* min */

	/**
	 * Returns (but does not remove) an entry with minimal key.
	 * O(1)
	 * @return entry having a minimal key (or null if empty)
	 */
	public Entry<K, V> max() {
		if(isEmpty())
			return null;

		Entry<K, V> ret;

		if(minHeap[0]==null){
			ret = buffer;
			buffer=null;
			return ret;
		}
		if(buffer.getKey().compareTo(minHeap[0].getKey()) <= 0 ){
			return buffer;
		}
		return maxHeap[0];
	} /* min */

	/**
    * Removes and returns an entry with minimal key.
    * O(log(n))
    * @return the removed entry (or null if empty)
    */
	public Entry<K, V> removeMin(){
		if(isEmpty())
			return null;

		Entry<K, V> ret;
		Entry<K, V> assoc;

		if(minHeap[0]==null){
			ret = buffer;
			buffer=null;
			return ret;
		}

		if(buffer==null){
			ret=minHeap[0];
			assoc=ret.getAssociate();
			assoc.setAssociate(null);
			buffer=assoc;
			if(minTail==0){
				minTail=-1;
				minHeap[0]=null;
				return ret;
			}
			minHeap[0]=minHeap[minTail];
			minHeap[minTail--]=null;
			minHeap[0].setIndex(0);
			downHeap(0,false);
			return ret;
		}

		if(buffer.getKey().compareTo(minHeap[0].getKey()) <= 0 ){
			ret = buffer;
			buffer=null;
			return ret;
		}

		ret=minHeap[0];
		minHeap[0]=minHeap[minTail];
		minHeap[minTail--]=null;
		minHeap[0].setIndex(0);
		downHeap(0,false);

		assoc=ret.getAssociate();
		buffer.setAssociate(assoc);
		assoc.setAssociate(buffer);
		insert(assoc,buffer);
		buffer=null;

		return ret;
	}

	/**
    * Removes and returns an entry with maximum key.
    * O(log(n))
    * @return the removed entry (or null if empty)
    */
	public Entry<K, V> removeMax(){
		if(isEmpty())
			return null;

		Entry<K, V> ret;
		Entry<K, V> assoc;

		if(maxHeap[0]==null){
			ret = buffer;
			buffer=null;
			return ret;
		}

		if(buffer==null){
			ret=maxHeap[0];
			assoc=ret.getAssociate();
			assoc.setAssociate(null);
			buffer=assoc;
			if(maxTail==0){
				maxTail=-1;
				maxHeap[0]=null;
				return ret;
			}
			maxHeap[0]=maxHeap[maxTail];
			maxHeap[maxTail--]=null;
			maxHeap[0].setIndex(0);
			downHeap(0,true);
			return ret;
		}

		if(buffer.getKey().compareTo(maxHeap[0].getKey()) >= 0 ){
			ret = buffer;
			buffer=null;
			return ret;
		}

		ret=maxHeap[0];
		maxHeap[0]=maxHeap[maxTail];
		maxHeap[maxTail--]=null;
		maxHeap[0].setIndex(0);
		downHeap(0,true);

		assoc=ret.getAssociate();
		buffer.setAssociate(assoc);
		assoc.setAssociate(buffer);
		insert(assoc,buffer);
		buffer=null;

		return ret;
	}


	/****************************************************
	*
	*           Methods for Heap Operations
	*
	****************************************************/


	private void insert(Entry entry1, Entry entry2){
		minTail++;
		maxTail++;
		if(entry1.getKey().compareTo(entry2.getKey()) >= 0){
			maxHeap[maxTail] = entry1;
			entry1.setIndex(maxTail);
			upHeap(maxTail, true);
			minHeap[minTail] = entry2;
			entry2.setIndex(minTail);
			upHeap(minTail, false);
		}

		else{
			maxHeap[maxTail] = entry2;
			entry2.setIndex(maxTail);
			upHeap(maxTail, true);
			minHeap[minTail] = entry1;
			entry1.setIndex(minTail);
			upHeap(minTail, false);
		}
	}

	/**
	 * Algorithm to place element after insertion at the tail.
	 * O(log(n))
	 * heapSelect true if want upHeap on maxHeap
	 */
	private void upHeap(int location,boolean heapSelect){
		if( location == 0 ) return;

		int parent = parent(location);

		if(heapSelect){
			if(maxHeap[parent].getKey().compareTo(maxHeap[location].getKey()) < 0 ) {
				swap(location, parent, heapSelect);
				upHeap(parent,heapSelect);
			}
		}

		else{
			if( minHeap[parent].getKey().compareTo(minHeap[location].getKey()) > 0 ) {
				swap(location, parent, heapSelect);
				upHeap(parent, heapSelect);
			}
		}
	}

	/**
	 * Inplace swap of 2 elements, assumes locations are in array
	 * O(1)
	 * heapSelect true if want swap on maxHeap
	 */
	private void swap(int location1, int location2, boolean heapSelect) {
		if(heapSelect){
			Entry<K, V> temp = maxHeap[location1];
			maxHeap[location1] = maxHeap[location2];
			maxHeap[location2] = temp;

			maxHeap[location1].index= location1;
			maxHeap[location2].index= location2;
		}

		else{
			Entry<K, V> temp = minHeap[location1];
			minHeap[location1] = minHeap[location2];
			minHeap[location2] = temp;

			minHeap[location1].index= location1;
			minHeap[location2].index= location2;
		}

	}

	/**
	 * Algorithm to place element after removal of root and tail element placed at root.
	 * O(log(n))
	 * heapSelect true if want downHeap on maxHeap
	 */
	private void downHeap(int location,boolean heapSelect) {
		int    left  = (location * 2) + 1;
		int    right = (location * 2) + 2;

		if(heapSelect){
			//Both children null or out of bound
			if(left > maxTail) return;

			//left in right out;
			if(left == maxTail) {
				if(maxHeap[location].getKey().compareTo(maxHeap[left].key) < 0 )
					swap(location, left,heapSelect);
				return;
			}

			int toSwap = (maxHeap[left].getKey().compareTo(maxHeap[right].getKey()) > 0) ?
			                left : right;

			if(maxHeap[location].getKey().compareTo(maxHeap[toSwap].getKey()) < 0 ) {
				swap(location, toSwap,heapSelect);
				downHeap(toSwap, heapSelect);
			}
		}

		else{
			//Both children null or out of bound
			if(left > minTail) return;

			//left in right out;
			if(left == minTail) {
				if(minHeap[location].getKey().compareTo(minHeap[left].getKey()) > 0)
					swap(location, left, heapSelect);
				return;
			}

			int toSwap = (minHeap[left].getKey().compareTo(minHeap[right].getKey()) < 0) ?
			                left : right;

			if(minHeap[location].getKey().compareTo(minHeap[toSwap].getKey()) > 0) {
				swap(location, toSwap, heapSelect);
				downHeap(toSwap, heapSelect);
			}
		}
	}


	/**
	 * Find parent of a given location,
	 * Parent of the root is the root
	 * O(1)
	 */
	private int parent(int location) {
		return (location - 1) / 2;
	} /* parent */



    public void print() {
		System.out.println("Min Heap:");
		for (Entry<K,V> e : minHeap){

			System.out.println ( "(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), " );
		}
		System.out.println("Max Heap");
		for (Entry<K,V> e : maxHeap){
			System.out.println ( "(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), " );
		}
		System.out.println("buffer: " + "(" + buffer.key.toString() + "," + buffer.value.toString() + "), ");
	}
}
