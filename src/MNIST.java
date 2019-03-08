import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class MNIST {
	String train_label_filename = "C:\\Users\\Akintunde\\eclipse-workspace\\AdvancedProg\\MNIST Database\\train-labels-idx1-ubyte.gz";
	String train_image_filename = "C:\\Users\\Akintunde\\eclipse-workspace\\AdvancedProg\\MNIST Database\\train-images-idx3-ubyte.gz";
	
	FileInputStream in_stream_labels = null;
	FileInputStream in_stream_images = null;{
	
	try {
		in_stream_labels = new FileInputStream(new File(train_label_filename));
		in_stream_images = new FileInputStream(new File(train_image_filename));
		
		int labels_start_code = (in_stream_labels.read() << 24) |
				(in_stream_labels.read() << 16) |
				(in_stream_labels.read() << 8) |
				(in_stream_labels.read());
		
		System.out.println("label start code: " + labels_start_code);
		
		int images_start_code = (in_stream_images.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		System.out.println("Images start code: " + images_start_code);
		
		int number_of_labels = (in_stream_labels.read() << 24) |
				(in_stream_labels.read() << 16) |
				(in_stream_labels.read() << 8) |
				(in_stream_labels.read());
		
		int number_of_images = (in_stream_images.read() << 24) |
				(in_stream_labels.read() << 16) |
				(in_stream_labels.read() << 8) |
				(in_stream_labels.read());
		System.out.println("Number of labels and images: " + number_of_labels + " and " + number_of_images);
		
		int image_height = (in_stream_images.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		int image_width = (in_stream_images.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		System.out.println("Image size: " + image_width + " x " + image_height);
		int image_size = image_width * image_height;
		
		int[] label_list = new int [number_of_labels];
		int[] image_data;
		
		for(int record = 0; record < number_of_images; record++) {
			int label = in_stream_labels.read();
			label_list[record] = label;
			System.out.println(label);
			
			image_data = new int[image_width * image_height];
			
			for(int pixel = 0; pixel < image_size; pixel++) {
				
				int gray_value = in_stream_images.read();
				int rgb_value = 0xFF000000 | (gray_value << 16) |
						(gray_value << 8) |
						(gray_value);
				
				image_data[pixel] = rgb_value;
						
			}
		}
		
		int[] label_list1 = new int[number_of_labels];
		int[] image_data1;
		
		BufferedImage currentImg;
		
		for(int record = 0; record < number_of_images; record++) {
			currentImg = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);
			
			int label = in_stream_labels.read();
			label_list1[record] = label;
			System.out.println(label);
			image_data1 = new int[image_width * image_height];
			
			for(int pixel = 0; pixel < image_size; pixel++) {
				int gray_value = in_stream_images.read();
				int rgb_value = 0xFF000000 | (gray_value << 16) |
						(gray_value << 8) |
						(gray_value);
				
				image_data1[pixel] = rgb_value;
			}
			
			currentImg.setRGB(0, 0, image_width, image_height, image_data1, 0, image_width);
		}
		
	}
	
	catch (Exception e) {
		System.out.println("An error occured");
	}

}}
