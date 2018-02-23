package com.example.akshay.noblind;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by bipin on 12/11/17.
 */

class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private GraphicOverlayb<BarcodeGraphic> mGraphicOverlay;

    BarcodeTrackerFactory(GraphicOverlayb<BarcodeGraphic> barcodeGraphicOverlay) {
        mGraphicOverlay = barcodeGraphicOverlay;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphic graphic = new BarcodeGraphic(mGraphicOverlay);
        return new BarcodeGraphicTracker(mGraphicOverlay, graphic);
    }

}

