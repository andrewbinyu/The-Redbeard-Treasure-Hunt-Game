package heap;

public class Heap<T extends HeapItem> {
	
	// Note the T is a parameter representing a type that extends the HeapItem interface
	// This a new way to use inheritance!

	protected T[] items; // Array that is used to store heap items. items[0] is the highest priority element.
	protected int maxHeapSize; // The capacity of the heap
	protected int currentItemCount; // How many elements we have currently on the heap

	public Heap(int maxHeapSize) {
		this.maxHeapSize = maxHeapSize;
		items = (T[]) new HeapItem[maxHeapSize];
		currentItemCount = 0; // heap is empty!
	}

	public boolean isEmpty() {
		return currentItemCount == 0;
	}

	public boolean isFull() {
		return currentItemCount == maxHeapSize;
	}

	public void add(T item) throws HeapFullException
	// Adds item T to its correct position on the heap
	{
		if (isFull())
			throw new HeapFullException();
		else {
			item.setHeapIndex(currentItemCount);
			items[currentItemCount] = item;  // the element is added to the bottom
			sortUp(item); // Move the element up to its legitimate place. Check the diagram on the handout!
			currentItemCount++;
		}
	}

	public boolean contains(T item)
	// Returns true if item is on the heap
	// Otherwise returns false
	{
		return items[item.getHeapIndex()].equals(item);
	    /*boolean result = false;
	    for (T i: items){
	        if (i != null && i.equals(item))
	            result = true;
	    }
	    return result;*/
	}

	public int count() {
		return currentItemCount;
	}

	public void updateItem(T item) {
		sortUp(item);
	}

	public T removeFirst() throws HeapEmptyException
	// Removes and returns the element sitting on top of the heap
	{
		if (isEmpty())
			throw new HeapEmptyException();
		else {
			T firstItem = items[0]; // element of top of the heap is stored in firstItem variable
			currentItemCount--;
			items[0] = items[currentItemCount]; //last element moves on top
			items[0].setHeapIndex(0);
			sortDown(items[0]); // move the element to its legitimate position. Please check the diagram on the handout.
			return firstItem;
		}
	}
	
	/*private void sortUp(T item) {
		// Implement this method according to the diagram on the handout.
		// Also: the indices of children and parent elements satisfy some relationships.
		// The formulas are on the handout.
	    int itemIndex = item.getHeapIndex();
	    if (itemIndex > 0){
	        T parent = items[(itemIndex-1)/2];
	        if (item.compareTo(parent)==-1){
	            items[itemIndex] = parent;
	            items[(itemIndex-1)/2] = item;
	            item.setHeapIndex((itemIndex-1)/2);
	            parent.setHeapIndex(itemIndex);
	            sortUp(item);
	        }
	    }
	}*/
	private void sortUp(T item) {
        // Implement this method according to the diagram on the handout.
        // Also: the indices of children and parent elements satisfy some relationships.
        // The formulas are on the handout.
	    
           T  parent = items[(item.getHeapIndex()-1)/2];
        
            while ( item.getHeapIndex() >0 && item.compareTo(parent)==-1 ){
                items[item.getHeapIndex()] = parent;
                items[(item.getHeapIndex()-1)/2] = item;
                item.setHeapIndex((item.getHeapIndex()-1)/2);
                parent.setHeapIndex(item.getHeapIndex());            
            }
        }
    
	
	private void sortDown(T item) {
		// Implement this method according to the diagram on the handout.
				// Also: the indices of children and parent elements satisfy some relationships.
				// The formulas are on the handout.
	    int itemIndex = item.getHeapIndex();
	    int childLeftIndex = itemIndex*2 + 1;
	    int childRightIndex = itemIndex*2 + 2;
	    if (childRightIndex < currentItemCount){
	        T childLeft = items[childLeftIndex];
	        T childRight = items[childRightIndex];
	        if (childLeft.compareTo(childRight) == -1){
	            if (childLeft.compareTo(item) == -1){
	                items[childLeftIndex] = item;
	                items[itemIndex] = childLeft;
	                item.setHeapIndex(childLeftIndex);
	                childLeft.setHeapIndex(itemIndex);               
	                sortDown(item);
	            }
	        }
	        else {
	            if (childRight.compareTo(item)==-1){
	                items[childRightIndex] = item;
	                items[itemIndex] = childRight;
	                item.setHeapIndex(childRightIndex);
	                childRight.setHeapIndex(itemIndex);
	                sortDown(item);
	            }
	        }
	    }
	    else {
	        if (childLeftIndex < currentItemCount){
	            T childLeft = items[childLeftIndex];
	            if (childLeft.compareTo(item) == -1){
	                items[childLeftIndex] = item;
                    items[itemIndex] = childLeft;
	                item.setHeapIndex(childLeftIndex);
	                childLeft.setHeapIndex(itemIndex);
	                sortDown(item);
	            }
	            
	        }
	    }
	
	}
	
	// You may implement additional helper methods if desired. Make sure to make them private!
}
