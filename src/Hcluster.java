import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


public class Hcluster {
	
	static int n;
	BufferedReader reader = null;
	LineNumberReader lnr;
	int k;
	static Point [] data; // set of all data points
	int cluster_count;
	
	public Hcluster (String path, int number_of_clusters) throws IOException
	{
		n = 0;
		k = number_of_clusters;
		
		
		// count the number of data points
		lnr = new LineNumberReader(new FileReader(new File(path)));
		lnr.skip(Long.MAX_VALUE);
		//System.out.println(lnr.getLineNumber() + 1); //Add 1 because line index starts at 0
		n = lnr.getLineNumber() + 1;
		// Finally, the LineNumberReader object should be closed to prevent resource leak
		lnr.close();
		
		data = new Point [n];
		int i = 0;
		int count = 0;
		cluster_count = n;
		
		try {
		    File file = new File(path);
		    reader = new BufferedReader(new FileReader(file));

		    String line= reader.readLine();
		   
		    while (line != null) {
		    	//System.out.println(line);
		    	String[] parts = line.split("\\s+");
		    	//System.out.println(parts[0] + " " + parts[1]);
		    	double x_val = Double.parseDouble(parts[0]); // x
		    	//System.out.println(x_val);
		    	
		    	double y_val = Double.parseDouble(parts[1]); // y

		    	data[i] = new Point(x_val, y_val, i, i);
		    	//System.out.println("Loading point "+ data[i]);
		    	i++;
		    	line = reader.readLine();
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		//Frame draw = new Frame("Please God, let it work");
		
		while (cluster_count > 2)
		{
			System.out.println("Cluster count - "+cluster_count);
			System.out.println("Cluster codes:");
			for(int m=0; m<data.length; m++)
				System.out.print(data[m].cluster+" ");
			System.out.println();
			//System.out.println("I'm in a while loop! Cluster count - "+ cluster_count);
			//System.out.println(data[m].x + "     " + data[m].y + "  " + data[m].cluster);
			mergePoints ();
		}
		
		System.out.println("FUCKING FINAL Cluster count - "+cluster_count);
		System.out.println("FUCKING FINAL Cluster codes:");
		for(int m=0; m<data.length; m++)
			System.out.print(data[m].cluster+" ");
		System.out.println();
		System.out.println("N: " + n);
		
		Frame draw = new Frame("HCluster Graph");
		
	}
	
	// Removing one cluster
	public void mergePoints ()
	{
		double min_dist = Double.MAX_VALUE;
		Point min_c1 = new Point(), min_c2 = new Point();
		
		for (int i = 0; i < n; i++)
		{
			for (int j =0; j < n; j++)
			if ((data[i].cluster != data[j].cluster) & distance(data[i], data[j]) < min_dist)
			{
				//System.out.println("Found a minimum distance!");
				min_c1 = data[i];
				min_c2 = data[j];
				
				min_dist = distance(data[i], data[j]);
				//System.out.println("Minimum distance - "+ min_dist);
			}
			//System.out.println("Minimum distance - "+ min_dist);
		}
		System.out.println("1st num - "+ min_c1);
		System.out.println("2nd num - "+ min_c2);
		System.out.println();
		System.out.println("///////////////*******     We're looking for cluster "+ min_c2.cluster);
		double replaceFrom=min_c2.cluster;
		double replaceWith=min_c1.cluster;
		for(int i=0; i< data.length; i++)
		{
			System.out.println("I see cluster "+data[i].cluster);
			if(data[i].cluster==replaceFrom)
			{
				System.out.println("Data cluster prev: " + data[i].cluster);
				data[i].cluster=replaceWith;
				System.out.println("Data cluster new: " + data[i].cluster);
				//System.out.println("Change cluster - "+ data[i] + " from cluster #" + min_c2.cluster + " to cluster #" + min_c1.cluster);
				
			}
		}
			
				
		System.out.println("break");
		cluster_count --;
	}
	
	public double distance (Point p1, Point p2)
	{
		double distance_val = Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
		//System.out.println("Point 1 - "+p1 + ", Point 2 - " + p2);
		//System.out.println(distance_val);
		return distance_val;
	}
	
	public void printList() 		// expected runtime: O(N)
	{
		System.out.println("List: ");
		
		for (int m = 0; m < (n); m++)
		{
			System.out.println(data[m].x + "     " + data[m].y + "  " + data[m].cluster);
		}
		
	}

}
