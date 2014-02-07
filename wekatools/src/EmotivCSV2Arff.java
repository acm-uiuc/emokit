import weka.core.Instances;
import weka.core.Instance;
import weka.core.Attribute;
import weka.core.FastVector;

import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
 
import java.io.File;
 
public class EmotivCSV2Arff {
  /**
   * takes 2 arguments:
   * - CSV input file
   * - ARFF output file
   */
  public static void main(String[] args) throws Exception {
    if (args.length % 2 != 1) {
      System.out.println("\nUsage: EmotivCSV2Arff <input1.csv> <label1> <input2.csv> <label2> ...  <output.arff>\n");
      System.exit(1);
    }
 
    // load CSVs
    CSVLoader[] loader = new CSVLoader[(args.length - 1) / 2];
		Instances[] data = new Instances[(args.length - 1) / 2];
		for (int i = 0; i < (args.length - 1) / 2; i++) {
		  loader[i] = new CSVLoader();
	    loader[i].setSource(new File(args[2 * i]));
	    data[i] = loader[i].getDataSet();
	  }

		// add class label data
    FastVector labels = new FastVector((args.length - 1) / 2);
    for (int i = 0; i < (args.length - 1) / 2; i++) {
		  if (!labels.contains(args[2 * i + 1])) {
	      labels.addElement(args[2 * i + 1]);
			}
    }
		
		/*labels.addElement("rest");
		labels.addElement("barmup");
		labels.addElement("rarmup");
		labels.addElement("larmup");
		labels.addElement("up");
		labels.addElement("down");*/
	
		for (int i = 0; i < (args.length - 1) / 2; i++) {
			Attribute classLabel = new Attribute("Class", labels);
			data[i].insertAttributeAt(classLabel, data[i].numAttributes());
			data[i].setClassIndex(data[i].numAttributes() - 1);
	  }

    // set class labels
		for (int j = 0; j < (args.length - 1) / 2; j++) {
			for (int i = 0; i < data[j].numInstances(); i++) {
				data[j].instance(i).setClassValue(args[(2 * j) + 1]);
			}
		}

		// merge data
		Instances newData = data[0];
		for (int i = 1; i < (args.length - 1) / 2; i++) {
		  System.out.println(i);
	    System.out.println(data[i].numInstances());
			for (int j = 0; j < data[i].numInstances(); j++) {
		    newData.add(data[i].instance(j));
				System.out.println(data[i].instance(j));
			}
		}

    // save ARFF
    ArffSaver saver = new ArffSaver();
    saver.setInstances(newData);
    saver.setFile(new File(args[args.length - 1]));
    saver.setDestination(new File(args[args.length - 1]));
    saver.writeBatch();
  }
}
