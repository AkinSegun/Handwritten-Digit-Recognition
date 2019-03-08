import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;

public class kNN {
	Float getDistanceBetween(float[] dataElement1, float[] dataElement2) {
	    int x1 =  Math.round(dataElement1[X_COORDINATE_INDEX]   ) ;
	    int y1 =  Math.round(dataElement1[Y_COORDINATE_INDEX]   ) ;
	    int x2 =  Math.round(dataElement2[X_COORDINATE_INDEX]   ) ;
	    int y2 =  Math.round(dataElement2[Y_COORDINATE_INDEX]   ) ;
	    int term1 = (x2 - x1) * (x2 - x1);
	    int term2 = (y2 - y1) * (y2 - y1);
	    int sum = term1 + term2;
	    String convertedSum = Integer.toString(sum);
	    double convertedToDoubleSum = Double.parseDouble(convertedSum);
	    double distance = Math.abs (Math.sqrt (convertedToDoubleSum ) );
	    String convertedDistance = Double.toString(distance);
	    return Float.parseFloat(convertedDistance);
	}
	
	private static int determineK () {
	    int size = data.size();
	    String sizeString = Integer.toString( size ) ;
	    double sizeDouble = Double.parseDouble( sizeString );
	    double root = Math.sqrt( sizeDouble );
	    double rawK = root / 2 ;
	    int num = Math.round( ( float )rawK ) ;
	    if ( num%2 != 0 ) {
	        return num ;
	    }
	    else {
	        return num - 1 ;
	    }
	}
	
	for (int index = 0; index < trainingData.size(); index++) {
	    float distance = getDistanceBetween(dataPoint, trainingData.get(index));
	    distances.add(distance);
	    distancesClone.add(distance);
	}
	public static LabeledImage predict(BufferedImage labeledImage) {
		// TODO Auto-generated method stub
		return null;
	}

	Collections.sort(distances, new Comparator<Float>() {
	    @Override
	    public int compare(Float o1, Float o2) {
	        return o1.compareTo(o2);
	    }
	});

	int K = determineK() ;
	List<Float >shortestDistances= distances.subList( 0 , K  ) ;
	for ( float element : shortestDistances ) {
	    int indexOnClone = distancesClone.indexOf(element);
	    float[] nearestNeighbour = trainingData.get(indexOnClone);
	    if (nearestNeighbour [ 3] == 1) {
	        CLASS_1.add( nearestNeighbour ) ;
	    }
	    else if (nearestNeighbour [ 3] == 2) {
	        CLASS_2.add( nearestNeighbour ) ;
	    }
	}
	if ( CLASS_1.size() > CLASS_2 .size() ){
	    return CLASS_1
	}
	else {
	    return CLASS_2
	}
}
