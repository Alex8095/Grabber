package com.frogorf.grabber.helper.impl;

import com.frogorf.grabber.helper.RealtyImageHelper;
import com.frogorf.realty.domain.Realty;
import com.frogorf.realty.domain.RealtyImage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Alex on 23.12.14.
 */
@Service("realtyImageHelperIService")
public class RealtyImageHelperImpl implements RealtyImageHelper {

    private Realty realty;
    private List<RealtyImage> realtyImages;

    @Override
    public List<RealtyImage> getImages() {
        return realtyImages;
    }

    @Override
    public List<RealtyImage> getRealtyImages() {
        return realtyImages;
    }

    @Override
    public void init(Realty realty) {
        this.realty = realty;
    }

    @Override
    public void clean() {
        this.realty = null;
        this.realtyImages = null;
    }
}
