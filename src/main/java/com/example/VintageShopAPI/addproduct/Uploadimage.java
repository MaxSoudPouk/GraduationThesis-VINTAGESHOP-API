package com.example.VintageShopAPI.addproduct;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Uploadimage {
//	DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("WWYYYY");
//	  String formattedWeek = Date.format(weekFormatter);
	
	
	 public boolean image_image1(String UPLOAD_DIRECTORY, String fileName, MultipartFile image1) throws IOException, java.io.IOException {
	        
//		  System.out.println(" UPLOAD_IMAGE1========: " + UPLOAD_DIRECTORY);
		  
		 Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
	         	        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
	        }
	         
	        try (InputStream inputStream = image1.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
            
            return true;
            
        } catch (IOException | java.io.IOException ioe) {  
	        	return false;
           // throw new IOException("Could not save image file: " + fileName, ioe);
        }
		    
	    }
	 
	 //================================
	 public boolean image_image2(String UPLOAD_DIRECTORY, String fileName, MultipartFile image2) throws IOException, java.io.IOException {
	        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
//		 System.out.println(" UPLOAD_IMAGE2========: " + UPLOAD_DIRECTORY);
	         
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	         
	        try (InputStream inputStream = image2.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	            
	            
	            return true;
	            
	        } catch (IOException | java.io.IOException ioe) {  
	        	return false;
	           // throw new IOException("Could not save image file: " + fileName, ioe);
	        }
		    
	    }
	 
	 //==============================
	 public boolean image_image3(String UPLOAD_DIRECTORY, String fileName,MultipartFile image3) throws IOException, java.io.IOException {
	        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
//		 System.out.println(" UPLOAD_IMAGE3========: " + UPLOAD_DIRECTORY);

	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	         
	        try (InputStream inputStream = image3.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	             
	            return true;
	            
	        } catch (IOException | java.io.IOException ioe) {  
	        	return false;
	           // throw new IOException("Could not save image file: " + fileName, ioe);
	        }
		    
	    }

	//==============================


	public boolean image_image4(String UPLOAD_DIRECTORY, String fileName,MultipartFile image4) throws IOException, java.io.IOException {

//		System.out.println(" UPLOAD_IMAGE4========: " + UPLOAD_DIRECTORY);


		Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = image4.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


			return true;

		} catch (IOException | java.io.IOException ioe) {
			return false;
			// throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	//==============================


	public boolean image_image5(String UPLOAD_DIRECTORY, String fileName,MultipartFile image5) throws IOException, java.io.IOException {

//		System.out.println(" UPLOAD_IMAGE5========: " + UPLOAD_DIRECTORY);


		Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = image5.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


			return true;

		} catch (IOException | java.io.IOException ioe) {
			return false;
			// throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}
	
	 
//	 public void saveImage(MultipartFile imageFile, String fileName) throws IOException {
//	        Path currentPath = Paths.get(".");
//	        Path absolutePath = currentPath.toAbsolutePath();
//	        file.setPath(absolutePath + "/src/main/resources/static/photos/");
//	        byte[] bytes = imageFile.getBytes();
//	        Path path = Paths.get(photo.getPath() + imageFile.getOriginalFilename());
//	        Files.write(path, bytes);
//	       // kafkaTemplate.send("photoIn", path.normalize().toString());
//	    }
	 
	 
	 //=====================================================================================
	 
//    public boolean image_person(String UPLOAD_DIRECTORY, String fileName, MultipartFile img_person) {
//	try {
//	 
//		 Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//		 StringBuilder fileNames = new StringBuilder();
//	        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, img_person.getOriginalFilename());
//	        fileNames.append(img_person.getOriginalFilename());
//	        Files.write(fileNameAndPath, img_person.getBytes());
//	       
//         return true;
//
//	} catch (Exception e) {
//		 // System.out.println(" Exception========:: " + e.getMessage());	
//	    return false;
//	}
//   }
    
//    
//    //========================image doc==================
//    
//    public boolean input_doc( MultipartFile img_doc) {
//    	try {
//    		 StringBuilder fileNames = new StringBuilder();
//    	        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, img_doc.getOriginalFilename());
//    	        fileNames.append(img_doc.getOriginalFilename());
//    	        Files.write(fileNameAndPath, img_doc.getBytes());
//    	       
//             return true;
//
//    	} catch (Exception e) {
//    		 // System.out.println(" Exception========:: " + e.getMessage());	
//    	    return false;
//    	}
//
//        }
//    
//    //=================================image sim==================
//    
//    
//    public boolean input_sim( MultipartFile img_sim) {
//    	try {
//    		 StringBuilder fileNames = new StringBuilder();
//    	        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, img_sim.getOriginalFilename());
//    	        fileNames.append(img_sim.getOriginalFilename());
//    	        Files.write(fileNameAndPath, img_sim.getBytes());
//    	       
//             return true;
//
//    	} catch (Exception e) {
//    		 // System.out.println(" Exception========:: " + e.getMessage());	
//    	    return false;
//    	}
//
//        }
//	
	
}


