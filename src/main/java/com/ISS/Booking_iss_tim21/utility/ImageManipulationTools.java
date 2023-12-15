package com.ISS.Booking_iss_tim21.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class ImageManipulationTools {


    public static String ImagePathToBase64(String path){

        byte[] bytes=null;

        try {
            bytes= Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(bytes);

    }

    public static Set<String> ImagePathSetToBase64(Set<String> images){

        Iterator<String> imagesIterator = images.iterator();

        Set<String> base64Images = new java.util.HashSet<>(Collections.emptySet());

        while(imagesIterator.hasNext()) {
            base64Images.add(ImagePathToBase64(imagesIterator.next()));
        }

        return base64Images;

    }

}
