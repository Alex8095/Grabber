package com.frogorf.grabber.helper;

import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyImage;

import java.util.List;

/**
 * Created by Alex on 23.12.14.
 */
public interface RealtyImageHelper {

    List<RealtyImage> getImages();

    void init(List<String> imagesSource, Realty realty);

    RealtyImage createImage(String imageSource);

    boolean canAddImageToList(String imageSource);

    void clean();
}
