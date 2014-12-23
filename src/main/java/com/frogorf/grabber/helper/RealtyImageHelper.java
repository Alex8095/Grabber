package com.frogorf.grabber.helper;

import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyImage;

import java.util.List;

/**
 * Created by Alex on 23.12.14.
 */
public interface RealtyImageHelper {

    List<RealtyImage> getImages();

    List<RealtyImage> getRealtyImages();

    void init(Realty realty);

    void clean();
}
