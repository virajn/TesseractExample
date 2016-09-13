import org.bytedeco.javacpp.*;
import org.junit.Test;

import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;
import static org.junit.Assert.assertTrue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class BasicTesseractExampleTest {
    
    @Test
    public void givenTessBaseApi_whenImageOcrd_thenTextDisplayed() throws Exception {
        try
		{
			BytePointer outText;

			TessBaseAPI api = new TessBaseAPI();
			//System.out.println("मुख्यमंत्री ");
			// Initialize tesseract-ocr with English, without specifying tessdata path
			if (api.Init(".", "ENG") != 0) {
				System.err.println("Could not initialize tesseract.");
				throw new Exception("Could not initialize tesseract.");
			}

			// Open input image with leptonica library
			PIX image = pixRead("skill.png");
			api.SetImage(image);
			// Get OCR result
			outText = api.GetUTF8Text();
			String string = outText.getString();
			assertTrue(true);
	        System.out.println("OCR output:\n" + string);
			assertTrue(writeFile("skill.txt",string));
			// Destroy used object and release memory
			api.End();
			outText.deallocate();
			pixDestroy(image);
		}
		catch(Throwable exp)
		{
			System.out.println(exp);
		}
	}
	
	public static boolean writeFile(String filePath, String content)
	{
		try
		{
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("**************Done*******************");
			return true;
		}
		catch(Exception exp)
		{
			System.out.println(exp);
		}
		return false;
	}
}