package com.rns.tiffeat.web.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtil implements Constants {


	public static void storeVendorProfilePic(com.rns.tiffeat.web.dao.domain.Vendor registVendor, MultipartFile file)
			throws IOException {
		if (file.isEmpty()) {
			return;
		}
		String directory = ROOT_DIRECTORY + "/" + registVendor.getId() + "/";
		String filePath = storeImage(file, directory);
		registVendor.setImage(filePath);
	}

	private static String storeImage(MultipartFile file, String directory) throws FileNotFoundException, IOException {
		File dir = new File(directory);
		dir.mkdirs();
		String filePath = directory + file.getOriginalFilename();
		File image = new File(filePath);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image));
		stream.write(file.getBytes());
		stream.close();
		return filePath;
	}

	public static void storeMealImage(com.rns.tiffeat.web.dao.domain.Meal mealToBeAdded, MultipartFile imageFile)
			throws FileNotFoundException, IOException {
		if (imageFile.isEmpty()) {
			return;
		}
		String directory = ROOT_DIRECTORY + "/" + mealToBeAdded.getVendor().getId() + "/" + mealToBeAdded.getId();
		String filePath = storeImage(imageFile, directory);
		mealToBeAdded.setImage(filePath);
	}

	public static BufferedImage compressImage(InputStream inputStream) throws IOException {


		float imageQuality = 0.0f;
		// Create the buffered image
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		// Get image writers
		Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
		OutputStream os = new FileOutputStream(ROOT_DIRECTORY + "compressed.jpg");

		if (!imageWriters.hasNext())
			throw new IllegalStateException("Writers Not Found!!");

		ImageWriter imageWriter = (ImageWriter) imageWriters.next();
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(os);
		imageWriter.setOutput(imageOutputStream);

		ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

		// Set the compress quality metrics
		imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imageWriteParam.setCompressionQuality(imageQuality);

		// Created image
		imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
		System.out.println(bufferedImage.getHeight() + ":" + bufferedImage.getWidth());
		// close all streams
		inputStream.close();
		os.close();
		imageOutputStream.close();
		imageWriter.dispose();
		return bufferedImage;
	}

}
