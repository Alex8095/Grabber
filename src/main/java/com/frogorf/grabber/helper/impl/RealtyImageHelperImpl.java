package com.frogorf.grabber.helper.impl;

import com.frogorf.grabber.helper.RealtyImageHelper;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyImage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 23.12.14.
 */
@Service("realtyImageHelperIService")
public class RealtyImageHelperImpl implements RealtyImageHelper {

    private Realty realty;
    private List<RealtyImage> realtyImages;
    private List<String> imagesSource;

    @Override
    public List<RealtyImage> getImages() {
        if (imagesSource != null) {
            realtyImages = new ArrayList<>();
            for (String imageSource : imagesSource) {
                if (canAddImageToList(imageSource)) {
                    realtyImages.add(createImage(imageSource));
                }
            }
        }
        return realtyImages;
    }

    @Override
    public RealtyImage createImage(String imageSource) {
        RealtyImage realtyImage = new RealtyImage();
        realtyImage.setRealty(realty);
        realtyImage.setSiteUrl(imageSource);
        return realtyImage;
    }

    @Override
    public boolean canAddImageToList(String imageSource) {
        if (realty.getImages() != null) {
            for (RealtyImage realtyImage : realty.getImages()) {
                if (realtyImage.getSiteUrl().equals(imageSource)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void init(List<String> imagesSource, Realty realty) {
        clean();
        this.imagesSource = imagesSource;
        this.realty = realty;
    }

    @Override
    public void clean() {
        imagesSource = null;
        realty = null;
        realtyImages = null;
    }
}
